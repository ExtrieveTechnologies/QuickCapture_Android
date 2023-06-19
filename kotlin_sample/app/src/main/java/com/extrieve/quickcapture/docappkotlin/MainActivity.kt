package com.extrieve.quickcapture.docappkotlin

import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.File
import java.io.IOException

import com.extrieve.quickcapture.sdk.*;

class MainActivity : AppCompatActivity() {
    /*DEV_HELP : Declare variables for the classes from SDK*/
    private var cameraHelper: CameraHelper? = null
    private var imageHelper: ImgHelper? = null

    /*DEV_HELP : Declare variables for ActivityResultLauncher to accept result from camera activity
    * As CameraHelper is an activity based class*/
    private var captureActivityResultLauncher: ActivityResultLauncher<Intent>? = null
    private val REQUIREDPERMISSIONS = arrayOf("android.permission.CAMERA")
    var fileCollection: ArrayList<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkAndPromptPermissions()

        /*DEV_HELP : Initialise object of ImgHelper class.Pass the current activity context*/
        imageHelper = ImgHelper(this)
        /*DEV_HELP : Initialise object CameraHelper*/
        cameraHelper = CameraHelper()

        /*DEV_HELP : assign registerForActivityResult for getting result from CameraHelper*/
        captureActivityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                result: ActivityResult -> handleCaptureActivityResult(result)
        }

        /*DEV_HELP : Capture Document with SDK Button click handler*/
        findViewById<View>(R.id.getPictureButton).setOnClickListener {
            setConfig()
            openCameraActivity()
        }
    }

    /*DEV_HELP : Basic permission for App/SDK to work*/
    private fun checkAndPromptPermissions() {
        for (permission in REQUIREDPERMISSIONS) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    REQUIREDPERMISSIONS,
                    REQUEST_CODE_PERMISSIONS
                )
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            for (permission in REQUIREDPERMISSIONS) {
                if (ContextCompat.checkSelfPermission(
                        this,
                        permission
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    Toast.makeText(this, "Permissions not granted by the user.", Toast.LENGTH_SHORT)
                        .show()
                    finish()
                }
                //Got permission
            }
        }
    }

    /*DEV_HELP : SetUp SDKConfig - Refer tech. Doc.  for further info.*/
    private fun setConfig() {
        imageHelper!!.SetPageLayout(4) //A1-A7(1-7),PHOTO,CUSTOM,ID(8,9,10)
        imageHelper!!.SetImageQuality(1) //0,1,2 - Photo_Quality, Document_Quality, Compressed_Document
        imageHelper!!.SetDPI(200) //int dpi_val = 100, 150, 200, 300, 500, 600;

        //can set output file path
        CameraSupport.CamConfigClass.OutputPath = buildStoragePath()
    }

    /*DEV_HELP : BuildStoragePath*/
    private fun buildStoragePath(): String {
        val c = ContextWrapper(this)
        return c.getExternalFilesDir(".GoNoGoImages")!!.absolutePath
    }

    /*DEV_HELP : handleCaptureActivityResult definition*/
    private fun handleCaptureActivityResult(result: ActivityResult) {
        run {
            val resultCode = result.resultCode
            if (resultCode != RESULT_OK) {
                return
            }
            val data = result.data
            var status: Boolean? = null
            if (data != null) {
                status = data.extras!!["STATUS"] as Boolean?
            }
            val description = data!!.extras!!["DESCRIPTION"] as String?
            if (!status!!) {
                val imageCaptureLog = "Description : " + description +
                        ".Exception: " + CameraSupport.CamConfigClass.LastLogInfo
                Log.d("INFO", imageCaptureLog)
                Toast.makeText(this, imageCaptureLog, Toast.LENGTH_LONG).show()
                finishActivity(MainActivity.Companion.REQUEST_CODE_FILE_RETURN)
                return
            }
            fileCollection = data.extras!!["fileCollection"] as ArrayList<String>?
            if (fileCollection == null || fileCollection!!.isEmpty()) return
            try {
                showImages(fileCollection!!)
            } catch (e: IOException) {
                e.printStackTrace()
            }
            finishActivity(REQUEST_CODE_FILE_RETURN)
        }
    }

    /*DEV_HELP : showImages*/
    @Throws(IOException::class)
    private fun showImages(FilesPath: ArrayList<String>) {
        val fileCollectionLength = FilesPath.size
        for (i in 0 until fileCollectionLength) {
            val dir = FilesPath[i]
            val imgFile = File(dir)
            //notifyMediaStoreScanner(imgFile);
            if (imgFile.exists()) {
                val myBitmap = BitmapFactory.decodeFile(imgFile.absolutePath)
                val myImage = findViewById<ImageView>(R.id.displayImageView)
                myImage.setImageBitmap(myBitmap)
            }
            Toast.makeText(this, "SDK captured $fileCollectionLength images.", Toast.LENGTH_SHORT)
                .show()
        }
    }

    /*DEV_HELP : OpenCameraActivity*/
    private fun openCameraActivity() {

        /*DEV_HELP : Check basic permissions for camera if needed*/
        //if (!MainActivity.this.allPermissionsGranted()) {
        //Toast.makeText(MainActivity.this, "Permissions not granted by the user.", Toast.LENGTH_SHORT).show();
        /*DEV_HELP : TODO : handle invalid permission*/
        // return;
        // }
        try {
            /*DEV_HELP :redirecting to camera*/
            val captureIntent = Intent(this, Class.forName("com.extrieve.quickcapture.sdk.CameraHelper"))
            val photoURI = Uri.parse(CameraSupport.CamConfigClass.OutputPath)
            grantUriPermission(
                this.packageName, photoURI,
                Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
                captureIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            }
            captureActivityResultLauncher!!.launch(captureIntent)
        } catch (ex: Exception) {
            /*DEV_HELP : TODO : handle invalid Exception*/
            Toast.makeText(this, "Failed to open camera  -" + ex.message, Toast.LENGTH_LONG).show()
        }
    }

    companion object {
        private const val REQUEST_CODE_PERMISSIONS = 1001
        private const val REQUEST_CODE_FILE_RETURN = 1004
    }
}