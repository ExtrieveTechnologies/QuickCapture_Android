/*
Copyright (c) $originalComment.match("Copyright \(c\) (\d+)", 1, "-", "$today.year")$today.year. Extrieve Technologies Pvt. Ltd. All rights reserved.
 * Extrieve Technologies
 * Enterprise DMS, Workflow, OCR, PDF solutions & SDKs with AI
 * www.extrieve.com
 * info@extrieve.com | devsupport@extrieve.com
 * Author : Team Extrieve.
 * Created On : 01-01-2016.
 * Updated on : 10-08-2025
 */

package com.extrieve.quickcapture.docappjava;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.extrieve.quickcapture.sdk.CameraHelper;
import com.extrieve.quickcapture.sdk.CameraSupport;
import com.extrieve.quickcapture.sdk.ImgHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executors;

/**
 * MainActivity serves as a sample implementation for the QuickCapture SDK.
 * It demonstrates how to:
 * 1. Handle runtime permissions for Camera and Gallery access.
 * 2. Configure and launch the SDK's camera activity.
 * 3. Pick single or multiple images from the device's gallery.
 * 4. Process selected images to ensure compatibility.
 * 5. Use the SDK to build PDF and TIFF files from a collection of images.
 * 6. Save generated files to shared storage for user access.
 * 7. Open a generated PDF file in an external viewer app.
 */
public class MainActivity extends AppCompatActivity {

    // DEV_HELP: A tag for logging, useful for debugging.
    private static final String TAG = "MainActivity";

    // DEV_HELP: Define permission sets for different Android versions. This is a best practice.
    // For Camera access, only CAMERA permission is needed.
    private static final String[] PERMS_CAMERA = { Manifest.permission.CAMERA };
    // For Android 9 (API 28) and below, we need read/write access to shared storage.
    private static final String[] PERMS_GALLERY_LEGACY = { Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE };
    // For Android 13 (API 33) and above, Google introduced granular media permissions.
    private static final String[] PERMS_GALLERY_TIRAMISU = { Manifest.permission.READ_MEDIA_IMAGES };

    /**
     * Stores the absolute file paths of images selected from the camera or gallery.
     * This list is passed to the SDK's build methods.
     */
    private ArrayList<String> fileCollection = new ArrayList<>();

    // --- UI Elements ---
    private ImageView selectedImage;
    private Button getPictureBtn;
    private Button loadFromGalleryBtn;
    private Button buildOutputBtn;
    private ProgressBar progressBar;

    // --- SDK Helper Classes ---
    private ImgHelper imageHelper;
    private CameraHelper cameraHelper;

    // DEV_HELP: ActivityResultLaunchers are the modern, recommended way to handle activity results,
    // replacing the deprecated onActivityResult method.
    private ActivityResultLauncher<String[]> permissionLauncher;
    private ActivityResultLauncher<Intent> cameraLauncher;
    private ActivityResultLauncher<Intent> galleryLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // --- Initialize UI Components ---
        selectedImage = findViewById(R.id.displayImageView);
        getPictureBtn = findViewById(R.id.getPictureButton);
        loadFromGalleryBtn = findViewById(R.id.loadFromGalleryBtn);
        buildOutputBtn = findViewById(R.id.buildOutPutBtn);
        progressBar = findViewById(R.id.progressBar);

        // --- Initialize SDK Helpers ---
        imageHelper = new ImgHelper(this);
        cameraHelper = new CameraHelper();

        // --- Setup Core App Logic ---
        initializeLaunchers();
        // DEV_HELP: We ask for the camera permission on startup as it's a primary feature.
        // Gallery permissions are requested "just-in-time" when the user clicks the button.
        checkAndPromptCameraPermission();
        try {
            setCameraConfig();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // --- OnClick Listeners ---
        getPictureBtn.setOnClickListener(v -> {
            if (hasCameraPermission()) {
                openCameraActivity();
            } else {
                Toast.makeText(this, "Camera permission is required.", Toast.LENGTH_SHORT).show();
                checkAndPromptCameraPermission(); // Ask again if denied
            }
        });

        loadFromGalleryBtn.setOnClickListener(v -> {
            // DEV_HELP: This is "just-in-time" permission handling. We only ask for gallery
            // permission right before the user needs to access it.
            if (hasGalleryPermission()) {
                openGalleryForImages();
            } else {
                Toast.makeText(this, "Storage permission is required to access the gallery.", Toast.LENGTH_SHORT).show();
                checkAndPromptGalleryPermission(); // Ask for permission
            }
        });

        // DEV_HELP: This button demonstrates creating a TIFF from the current image selection.
        buildOutputBtn.setOnClickListener(v -> buildTiffFromSelection());
    }

    /**
     * Initializes the ActivityResultLaunchers used for handling permissions,
     * camera results, and gallery selections.
     */
    private void initializeLaunchers() {
        // This launcher handles the result of any runtime permission request.
        permissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), permissions -> {
            boolean allGranted = true;
            // DEV_HELP: The result is a map of [Permission String -> Boolean Granted].
            // We loop through the values to see if any were denied.
            for (Boolean isGranted : permissions.values()) {
                if (!isGranted) {
                    allGranted = false;
                    break;
                }
            }
            if (!allGranted) {
                Toast.makeText(this, "Permission was not granted.", Toast.LENGTH_SHORT).show();
            }
        });

        // This launcher handles the result from the SDK's Camera Activity.
        cameraLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == Activity.RESULT_OK) {
                Intent data = result.getData();
                if (data == null || data.getExtras() == null) return;

                boolean status = data.getExtras().getBoolean("STATUS");
                if (!status) {
                    String description = data.getExtras().getString("DESCRIPTION");
                    String imageCaptureLog = "Description: " + description + ". Exception: " + CameraSupport.CamConfigClass.LastLogInfo;
                    Log.d(TAG, imageCaptureLog);
                    return;
                }

                // The SDK returns a list of file paths for the captured images.
                fileCollection = (ArrayList<String>) data.getExtras().get("fileCollection");
                if (fileCollection != null && !fileCollection.isEmpty()) {
                    // Make the newly captured photos visible in the phone's gallery apps.
                    notifyGalleryOfNewImages(fileCollection);
                    // Automatically build a PDF from the captured images.
                    buildPdfFromCapture();
                }
            }
        });

        // This launcher handles the result from the system's image picker (the gallery).
        galleryLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                Intent data = result.getData();
                fileCollection.clear();

                // Handle multiple images selected.
                if (data.getClipData() != null) {
                    ClipData clip = data.getClipData();
                    for (int i = 0; i < clip.getItemCount(); i++) {
                        Uri uri = clip.getItemAt(i).getUri();
                        copyUriToCacheAndAdd(uri);
                    }
                }
                // Handle a single image selected.
                else if (data.getData() != null) {
                    Uri uri = data.getData();
                    copyUriToCacheAndAdd(uri);
                }

                // After selecting images, build a TIFF file.
                if (!fileCollection.isEmpty()) {
                    buildTiffFromSelection();
                } else {
                    Toast.makeText(this, "No valid images were selected.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    /**
     * Copies an image from a gallery URI to the app's private cache.
     * This is a crucial step for two reasons:
     * 1. It provides a direct, accessible file path that the SDK can use.
     * 2. It "sanitizes" the image by decoding and re-compressing it into a standard JPEG format,
     * which prevents errors with incompatible image types (e.g., progressive JPEGs, HEIC).
     * @param uri The content URI of the image selected from the gallery.
     */
    private void copyUriToCacheAndAdd(Uri uri) {
        takePersistableReadPermission(uri);
        // DEV_HELP: We always save as .jpg because the bitmap compression guarantees a standard JPEG format.
        File outFile = new File(getCacheDir(), "picked_" + System.currentTimeMillis() + ".jpg");

        try (InputStream inputStream = getContentResolver().openInputStream(uri)) {
            if (inputStream == null) return;

            // Step 1: Decode the image from the gallery into a Bitmap object.
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            if (bitmap == null) {
                Log.e(TAG, "Failed to decode image stream for URI: " + uri);
                return;
            }

            // Step 2: Re-save (compress) the Bitmap into a new, clean JPEG file.
            try (OutputStream outputStream = new FileOutputStream(outFile)) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream); // 90 is a good quality value.
                fileCollection.add(outFile.getAbsolutePath());
            }

            // Step 3: Clean up memory. This is important when handling multiple images.
            bitmap.recycle();

        } catch (IOException e) {
            Log.e(TAG, "Failed to copy or process URI: " + uri, e);
            Toast.makeText(this, "Failed to load an image.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Checks if the app has been granted camera permission.
     * @return true if permission is granted, false otherwise.
     */
    private boolean hasCameraPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * Checks for camera permission and launches the permission request if it's not granted.
     */
    private void checkAndPromptCameraPermission() {
        if (!hasCameraPermission()) {
            permissionLauncher.launch(PERMS_CAMERA);
        }
    }

    /**
     * Checks if the app has the necessary permissions to read from the gallery.
     * @return true if permissions are granted, false otherwise.
     */
    private boolean hasGalleryPermission() {
        // DEV_HELP: Select the correct permission array based on the device's Android version (SDK level).
        String[] requiredPermissions = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) ? PERMS_GALLERY_TIRAMISU : PERMS_GALLERY_LEGACY;
        for (String p : requiredPermissions) {
            if (ContextCompat.checkSelfPermission(this, p) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * Launches the permission request for gallery access.
     */
    private void checkAndPromptGalleryPermission() {
        // DEV_HELP: Launch the permission request with the correct permissions for the current SDK level.
        String[] requiredPermissions = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) ? PERMS_GALLERY_TIRAMISU : PERMS_GALLERY_LEGACY;
        permissionLauncher.launch(requiredPermissions);
    }

    /**
     * Configures the QuickCapture SDK's camera settings.
     * @throws IOException If there's an issue with configuration.
     */
    private void setCameraConfig() throws IOException {
        // Set image resolution.
        imageHelper.SetDPI(200);

        // DEV_HELP: Set the output path for captured images. Using the app's private external
        // files directory is recommended as it requires no special permissions.
        CameraSupport.CamConfigClass.OutputPath = buildStoragePath();

        // Show a review screen after capturing an image.
        CameraSupport.CamConfigClass.CaptureReview = true;
        // Set color mode to RGB.
        CameraSupport.CamConfigClass.ColorMode = 1;
        // Disable shutter sound.
        CameraSupport.CamConfigClass.CaptureSound = false;
        // Enable flash control.
        CameraSupport.CamConfigClass.EnableFlash = true;
        // Show the counter for captured images.
        CameraSupport.CamConfigClass.ShowCaptureCountAndLimit = true;
        // Set the rear camera as the default.
        CameraSupport.CamConfigClass.CameraToggle = 2;
    }

    /**
     * Creates and launches an intent to start the SDK's camera activity.
     */
    private void openCameraActivity() {
        try {
            // DEV_HELP: The SDK's camera activity is launched by its class name.
            Intent cameraIntent = new Intent(this, Class.forName("com.extrieve.quickcapture.sdk.CameraHelper"));
            cameraLauncher.launch(cameraIntent);
        } catch (ClassNotFoundException e) {
            Toast.makeText(this, "Failed to open camera", Toast.LENGTH_LONG).show();
            Log.e(TAG, "Camera SDK Class not found", e);
        }
    }

    /**
     * Creates and launches an intent to open the system's file picker for images.
     */
    private void openGalleryForImages() {
        // DEV_HELP: Using ACTION_OPEN_DOCUMENT is part of the Storage Access Framework (SAF),
        // which is the modern way to let users select files.
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        galleryLauncher.launch(intent);
    }

    /**
     * Takes a persistable read permission for a URI obtained from the gallery.
     * This is good practice to ensure long-term access if needed.
     * @param uri The URI of the selected content.
     */
    private void takePersistableReadPermission(Uri uri) {
        try {
            getContentResolver().takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } catch (SecurityException e) {
            Log.e(TAG, "Failed to take persistable URI permission", e);
        }
    }

    /**
     * Builds a multi-page TIFF file from the current image collection.
     * The file is first created in the app's private storage, then saved to the user's
     * public "Downloads" folder.
     */
    private void buildTiffFromSelection() {
        if (fileCollection == null || fileCollection.isEmpty()) {
            Toast.makeText(this, "Pick one or more images first.", Toast.LENGTH_SHORT).show();
            return;
        }

        // The temporary TIFF will be created in the app's private directory.
        File docsDir = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
        if (docsDir == null) docsDir = getFilesDir();
        if (!docsDir.exists()) docsDir.mkdirs();
        final String outTiffPath = new File(docsDir, "Output_" + UUID.randomUUID() + ".tiff").getAbsolutePath();

        showProgress(true);
        // DEV_HELP: Heavy operations like file creation must be done on a background thread
        // to avoid freezing the UI. Executors.newSingleThreadExecutor() is a simple way to do this.
        Executors.newSingleThreadExecutor().execute(() -> {
            String result;
            try {
                // Call the SDK method to build the TIFF.
                result = cameraHelper.BuildTiff(fileCollection, outTiffPath);
            } catch (Throwable t) {
                result = "FAILED:::Exception " + t.getMessage();
                Log.e(TAG, "TIFF build failed", t);
            }

            final String finalResult = result;
            // DEV_HELP: Update the UI on the main thread after the background task is complete.
            runOnUiThread(() -> {
                showProgress(false);
                if (finalResult != null && finalResult.startsWith("SUCCESS:::")) {
                    String tiffPath = finalResult.substring("SUCCESS:::".length());
                    Toast.makeText(this, "TIFF created successfully!", Toast.LENGTH_LONG).show();
                    // Save the final file to a user-accessible location.
                    saveFileToDownloads(tiffPath);
                } else {
                    Toast.makeText(this, "Failed to create TIFF: " + finalResult, Toast.LENGTH_LONG).show();
                }
            });
        });
    }

    /**
     * Builds a PDF file from the images captured by the camera.
     */
    private void buildPdfFromCapture() {
        if (fileCollection == null || fileCollection.isEmpty()) {
            Toast.makeText(this, "No images available to build PDF.", Toast.LENGTH_SHORT).show();
            return;
        }

        File docsDir = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
        if (docsDir == null) docsDir = getFilesDir();
        if (!docsDir.exists()) docsDir.mkdirs();
        final String outPdfPath = new File(docsDir, "Output_" + UUID.randomUUID() + ".pdf").getAbsolutePath();

        showProgress(true);
        Executors.newSingleThreadExecutor().execute(() -> {
            String result;
            try {
                result = cameraHelper.BuildPDF(fileCollection, outPdfPath);
            } catch (Throwable t) {
                result = "FAILED:::Exception " + t.getMessage();
                Log.e(TAG, "PDF build failed", t);
            }

            final String finalResult = result;
            runOnUiThread(() -> {
                showProgress(false);
                if (finalResult != null && finalResult.startsWith("SUCCESS:::")) {
                    String pdfPath = finalResult.substring("SUCCESS:::".length());
                    Toast.makeText(this, "PDF created successfully!", Toast.LENGTH_LONG).show();
                    openPdfFile(pdfPath);
                } else {
                    Toast.makeText(this, "Failed to create PDF: " + finalResult, Toast.LENGTH_LONG).show();
                }
            });
        });
    }

    /**
     * Opens a PDF file using an external viewer app.
     * @param pdfFilePath The path to the PDF file in the app's private storage.
     */
    private void openPdfFile(String pdfFilePath) {
        File file = new File(pdfFilePath);
        // DEV_HELP: FileProvider is the required, secure way to share files from your app's
        // private storage with other apps. You must configure it in your AndroidManifest.xml
        // and create a `file_paths.xml` resource file.
        String authority = getApplicationContext().getPackageName() + ".FileProvider";
        Uri fileUri = FileProvider.getUriForFile(this, authority, file);

        Intent viewIntent = new Intent(Intent.ACTION_VIEW);
        viewIntent.setDataAndType(fileUri, "application/pdf");
        viewIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        // Grant permission to all apps that can handle this intent.
        List<ResolveInfo> resolved = getPackageManager().queryIntentActivities(viewIntent, PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo r : resolved) {
            String pkg = r.activityInfo.packageName;
            grantUriPermission(pkg, fileUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }

        try {
            startActivity(viewIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "No PDF viewer found.", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Saves a file to the shared "Downloads" folder using the correct method for the Android version.
     * This is the modern, permission-safe way to save files for user access.
     * @param sourcePath The path to the temporary file in your app's private storage.
     */
    private void saveFileToDownloads(String sourcePath) {
        File sourceFile = new File(sourcePath);
        if (!sourceFile.exists()) {
            Toast.makeText(this, "Source file not found.", Toast.LENGTH_SHORT).show();
            return;
        }

        // DEV_HELP: Check the Android version. The method for saving to shared storage
        // changed significantly in Android 10 (API 29).
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // --- Modern Way (API 29 / Android 10 and newer) ---
            // This uses MediaStore, which does not require WRITE_EXTERNAL_STORAGE permission.
            ContentResolver resolver = getContentResolver();
            ContentValues contentValues = new ContentValues();
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, sourceFile.getName());
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/tiff");
            contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS);

            Uri destinationUri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues);
            if (destinationUri == null) {
                Toast.makeText(this, "Failed to create file in Downloads.", Toast.LENGTH_SHORT).show();
                return;
            }

            try (InputStream in = new FileInputStream(sourceFile);
                 OutputStream out = resolver.openOutputStream(destinationUri)) {
                if (out == null) return;
                byte[] buf = new byte[8192];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                Toast.makeText(this, "File saved to Downloads folder.", Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                Log.e(TAG, "Failed to save file to Downloads", e);
                Toast.makeText(this, "Error saving file.", Toast.LENGTH_SHORT).show();
            }
        } else {
            // --- Legacy Way (API 28 / Android 9 and older) ---
            // DEV_HELP: This method requires the WRITE_EXTERNAL_STORAGE permission, which must be
            // declared in the AndroidManifest.xml and requested at runtime.
            File downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            if (!downloadsDir.exists()) {
                downloadsDir.mkdirs();
            }
            File destFile = new File(downloadsDir, sourceFile.getName());

            try (InputStream in = new FileInputStream(sourceFile);
                 OutputStream out = new FileOutputStream(destFile)) {
                byte[] buf = new byte[8192];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                // Notify the system that a new file is available so it appears in file managers.
                sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(destFile)));
                Toast.makeText(this, "File saved to Downloads folder.", Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                Log.e(TAG, "Failed to save file to Downloads (Legacy)", e);
                Toast.makeText(this, "Error saving file.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Previews the first image from a list in the UI's ImageView.
     * @param filesPath List of image file paths.
     */
    private void showImages(ArrayList<String> filesPath) {
        if (filesPath == null || filesPath.isEmpty()) return;

        Bitmap bmp = BitmapFactory.decodeFile(filesPath.get(0));
        selectedImage.setImageBitmap(bmp);
        Toast.makeText(this, "Captured " + filesPath.size() + " image(s).", Toast.LENGTH_SHORT).show();
    }

    /**
     * Builds a path to the app's private external files directory.
     * This location is automatically deleted when the app is uninstalled and requires no special permissions.
     * @return The absolute path to the storage directory.
     */
    private String buildStoragePath() {
        File f = getExternalFilesDir("QuickCapture");
        if (f == null) {
            f = new File(getFilesDir(), "QuickCapture"); // Fallback to internal storage
        }
        if (!f.exists()) f.mkdirs();
        return f.getAbsolutePath();
    }

    /**
     * Shows or hides a progress bar.
     * @param show true to show, false to hide.
     */
    private void showProgress(boolean show) {
        if (progressBar != null) {
            progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }

    /**
     * Notifies the Android MediaStore that new images are available,
     * so they appear in gallery apps like Google Photos.
     * @param imagePaths A list of file paths for the new images.
     */
    private void notifyGalleryOfNewImages(ArrayList<String> imagePaths) {
        if (imagePaths == null || imagePaths.isEmpty()) {
            return;
        }
        String[] paths = imagePaths.toArray(new String[0]);
        MediaScannerConnection.scanFile(this, paths, new String[]{"image/jpeg"}, (path, uri) -> {
            Log.i(TAG, "Scanned " + path + ": -> uri=" + uri);
        });
    }
}