package com.extrieve.quickcapture.docappjava;

import android.app.Activity;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.extrieve.quickcapture.sdk.CameraHelper;
import com.extrieve.quickcapture.sdk.Config;
import com.extrieve.quickcapture.sdk.ImgHelper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_PERMISSIONS = 1001;
    /*DEV_HELP : Declare variables for the classes from SDK*/
    CameraHelper CameraHelper = null;
    ImgHelper ImageHelper = null;

    /*DEV_HELP : Declare variables for ActivityResultLauncher to accept result from camera activity
    * As CameraHelper is an activity based class*/
    private ActivityResultLauncher<Intent> captureActivityResultLauncher;


    private final String[] REQUIRED_PERMISSIONS =
            new String[]{"android.permission.CAMERA"};
    private static final int REQUEST_CODE_FILE_RETURN = 1004;

    ArrayList<String> FileCollection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CheckAndPromptPermissions();

        /*DEV_HELP : Initialise object of ImgHelper class.Pass the current activity context*/
        ImageHelper = new ImgHelper(this);
        /*DEV_HELP : Initialise object CameraHelper*/
        CameraHelper = new CameraHelper();

        /*DEV_HELP : assign registerForActivityResult for getting result from CameraHelper*/
        captureActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> handleCaptureActivityResult(result));

        /*DEV_HELP : Capture Document with SDK Button click handler*/
        findViewById(R.id.getPictureButton).setOnClickListener(v -> {
            SetConfig();
            OpenCameraActivity();
        });
    }

    /*DEV_HELP : Basic permission for App/SDK to work*/
    private void CheckAndPromptPermissions() {
        for (String permission : REQUIRED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            for (String permission : REQUIRED_PERMISSIONS) {
                if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permissions not granted by the user.", Toast.LENGTH_SHORT).show();
                    this.finish();
                }
                //Got permission
            }
        }
    }

    /*DEV_HELP : SetUp SDKConfig - Refer tech. Doc.  for further info.*/
    private void SetConfig() {

        ImageHelper.SetPageLayout(4);//A1-A7(1-7),PHOTO,CUSTOM,ID(8,9,10)

        ImageHelper.SetImageQuality(1);//0,1,2 - Photo_Quality, Document_Quality, Compressed_Document

        ImageHelper.SetDPI(200);//int dpi_val = 100, 150, 200, 300, 500, 600;

        //can set output file path
        Config.CaptureSupport.OutputPath = BuildStoragePath();

        Config.CaptureSupport.DocumentCropping = Config.CaptureSupport.CroppingType.LiveDetection;

        Config.CaptureSupport.EnableFlash = true;

        Config.CaptureSupport.CropFilter = Config.CaptureSupport.CropImageFilterType.ENHANCE;

    }

    /*DEV_HELP : BuildStoragePath*/
    private String BuildStoragePath() {
        ContextWrapper c = new ContextWrapper(this);
        String path = c.getExternalFilesDir(".GoNoGoImages").getAbsolutePath();
        return path;
    }

    /*DEV_HELP : handleCaptureActivityResult definition*/
    private void handleCaptureActivityResult(ActivityResult result){
        {
            int resultCode = result.getResultCode();
            if (resultCode != Activity.RESULT_OK) {
                return;
            }
            Intent data = result.getData();
            Boolean Status = null;
            if (data != null) {
                Status = (Boolean) data.getExtras().get("STATUS");
            }
            String Description = (String) data.getExtras().get("DESCRIPTION");
            if (!Status) {
                String imageCaptureLog = "Description : " + Description +
                        ".Exception: " + Config.CaptureSupport.LastLogInfo;
                Log.d("INFO", imageCaptureLog);
                Toast.makeText(this, imageCaptureLog, Toast.LENGTH_LONG).show();
                finishActivity(REQUEST_CODE_FILE_RETURN);
                return;
            }
            FileCollection = (ArrayList<String>) data.getExtras().get("fileCollection");
            if (FileCollection == null || FileCollection.isEmpty()) return;
            try {
              showImages(FileCollection);
            } catch (IOException e) {
              e.printStackTrace();
            }
            finishActivity(REQUEST_CODE_FILE_RETURN);
        }
    }

    /*DEV_HELP : showImages*/
    private void showImages(ArrayList<String> FilesPath) throws IOException {
        int FileCollectionLength = FilesPath.size();
        for (int i = 0; i < FileCollectionLength; i++) {
            String dir = FilesPath.get(i);
            File imgFile = new File(dir);
            //notifyMediaStoreScanner(imgFile);
            if (imgFile.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                ImageView myImage = findViewById(R.id.displayImageView);
                myImage.setImageBitmap(myBitmap);
            }
            Toast.makeText(this, "SDK captured " + FileCollectionLength + " images.", Toast.LENGTH_SHORT).show();
        }
    }

    /*DEV_HELP : OpenCameraActivity*/
    private void OpenCameraActivity() {

        /*DEV_HELP : Check basic permissions for camera if needed*/
        //if (!MainActivity.this.allPermissionsGranted()) {
            //Toast.makeText(MainActivity.this, "Permissions not granted by the user.", Toast.LENGTH_SHORT).show();
            /*DEV_HELP : TODO : handle invalid permission*/
           // return;
       // }
        try {
            /*DEV_HELP :redirecting to camera*/
            Intent CameraIntent = new Intent(this, Class.forName("com.extrieve.quickcapture.sdk.CameraHelper"));
            Uri photoURI = Uri.parse(Config.CaptureSupport.OutputPath);
            this.grantUriPermission(this.getPackageName(), photoURI,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
                CameraIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            }
            captureActivityResultLauncher.launch(CameraIntent);
        } catch (Exception ex) {
            /*DEV_HELP : TODO : handle invalid Exception*/
            Toast.makeText(this, "Failed to open camera  -" + ex.getMessage(), Toast.LENGTH_LONG).show();
        }

    }

}