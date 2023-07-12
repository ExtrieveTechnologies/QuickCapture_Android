
<img class="img-fluid" align="center" src="https://github.com/ExtrieveTechnologies/QuickCapture/blob/main/QuickCapture.png?raw=true" width="30%" alt="img-verification"><img align="right" class="img-fluid" padding="10px" src="https://github.com/ExtrieveTechnologies/QuickCapture/blob/main/android.png?raw=true?raw=true" alt="img-verification">

> **End of support Notice** :  QuickCapture SDK Android **V1** deprecated by Dec. 2022.For any further updates and support, can use **V2**
> which having no major modifications.But with improved funcionalities,feature additions and fixes.

[Refer here for **V2 documentation** and samples](https://github.com/ExtrieveTechnologies/QuickCapture_Android/tree/QuickCapture-V2#mobile-document-scanning-sdk-android-v2)

### Other available platform options
- [iOS](https://github.com/ExtrieveTechnologies/QuickCapture_IOS)
- [Fultter Plugin](https://pub.dev/packages/quickcapture)
- [Web SDK](https://github.com/ExtrieveTechnologies/QuickCapture_WEB)

## Mobile-Document-Scanning-SDK-ANDROID v3
QuickCapture Mobile Scanning SDK Specially designed for native ANDROID from Extrieve.

Download
--------
You can use this SDK in project simply by using Gradle :

```gradle
//Add expack central repo in settings.gradle
repositories {
  google()
  mavenCentral()
  maven {url 'https://expack.extrieve.in/maven/'}
}

//Then add implementation for SDK in dependencies in build.gradle (module:<yourmodulename>)
dependencies {
  implementation 'com.extrieve.quickcapture:QCv3:3.0.5'
}
```

Or Maven:

```xml
<dependency>
  <groupId>com.extrieve.quickcapture</groupId>
  <artifactId>QCv3</artifactId>
  <version>3.0.5</version>
</dependency>
```

Or even you can download the **.aar** library file from GitHub's [releases page](https://github.com/ExtrieveTechnologies/QuickCapture_Android/releases/) and add the file dependency manually in to the project/app.


Compatibility
-------------
 * **JAVA 8 Support**: QuickCapture v3 requires JAVA version 8 support for the application.
 * **Minimum Android SDK**: QuickCapture v3 requires a minimum API level of 21.
 * **Compile Android SDK**: QuickCapture v3 requires you to compile against API 33 or later.
 

## API and  integration  Details - Available properties and method

SDK have two core classes and supporting classes :

 1. **CameraHelper**	-	*Handle the  camera  related  operations. Basically, an activity.* 
 2. **ImgHelper**	-	*Purpose of this class is to handle all imaging related operations.*
 3. **Config**		-	*Holds various configurations SDK.* 
 4. **ImgException**	-	*Handle all exceptions on image related operations on ImgHelper.*
 

Based on the requirement, any one or all classes can be used. And need to import those from the SDK.
```java
    import com.extrieve.quickcapture.sdk.*;
    //OR : can import only required classes as per use cases.
    import  com.extrieve.quickcapture.sdk.ImgHelper;  
    import  com.extrieve.quickcapture.sdk.CameraHelper;
    import  com.extrieve.quickcapture.sdk.Config;  
    import  com.extrieve.quickcapture.sdk.ImgException;
   ```
---
## CameraHelper
This core class will be implemented as an activity.This class can be initialized as intent.
```java
//JAVA
CameraHelper CameraHelper = new CameraHelper();
```
```kotlin
//Kotlin
var cameraHelper: CameraHelper? = CameraHelper()
```

With an activity call, triggering SDK for capture activity can be done.Most operations in **CameraHelper** is **activity based**.
SDK having multiple openration flow as follows :
	
* **CAMERA_CAPTURE_REVIEW** - *Default flow of the CameraHelper.Includes Capture with SDK Camera -> Review Image.*
* **SYSTEM_CAMERA_CAPTURE_REVIEW** - *If user need to capture image with system default camera, this can be used.Includes Capture with system default camera -> Review*.
* **IMAGE_ATTACH_REVIEW** - *If user need to review an image from device / gallery - this option can be used.After attach each image,review and all functionalities depends on review can be avail*.

**CAMERA_CAPTURE_REVIEW**

```java
//JAVA
Intent CameraIntent = new Intent(this,Class.forName("com.extrieve.quickcapture.sdk.CameraHelper"));
UriphotoURI = Uri.parse(Config.CaptureSupport.OutputPath);
this.grantUriPermission(this.getPackageName(),photoURI,Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);  
if  (Build.VERSION.SDK_INT  <=  Build.VERSION_CODES.LOLLIPOP)  {
	CameraIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
}
startActivityForResult(CameraIntent,REQUEST_CODE_FILE_RETURN);

@Override
protected  void  onActivityResult(int  requestCode,  int  resultCode,  @Nullable  Intent  data)  
{
	super.onActivityResult(requestCode,  resultCode,  data);
	if  (requestCode  ==  REQUEST_CODE_FILE_RETURN  &&  resultCode  ==  Activity.RESULT_OK)
	{  
		Boolean  Status  =  (Boolean)  data.getExtras().get("STATUS");
		String Description  = (String) data.getExtras().get("DESCRIPTION");  
		if(Status  == false){  //Failed  to  capture
		finishActivity(REQUEST_CODE_FILE_RETURN);  return;
	}
	FileCollection  =  (ArrayList<String>)  data.getExtras().get("fileCollection");
	//FileCollection //: will contains all capture images path as string
	finishActivity(REQUEST_CODE_FILE_RETURN);
}
```
```kotlin
//Kotlin
try {
    /*DEV_HELP :redirecting to camera*/
    val captureIntent = Intent(this, Class.forName("com.extrieve.quickcapture.sdk.CameraHelper"))
    val photoURI = Uri.parse(Config.CaptureSupport.OutputPath)
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
```

**CAMERA_CAPTURE_REVIEW**

```java
//JAVA
//Set CaptureMode as CAMERA_CAPTURE_REVIEW
Config.CaptureSupport.CaptureMode = Config.CaptureSupport.CaptureModes.CAMERA_CAPTURE_REVIEW;
//set ppermission for output path that set in config.
UriphotoURI = Uri.parse(Config.CaptureSupport.OutputPath);
this.grantUriPermission(this.getPackageName(),photoURI,Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);  

//Create CameraIntent for CameraHelper activity call.
Intent CameraIntent = new Intent(this,Class.forName("com.extrieve.quickcapture.sdk.CameraHelper"));
if  (Build.VERSION.SDK_INT  <=  Build.VERSION_CODES.LOLLIPOP)  {
	CameraIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
}
//Call the Activity.
startActivityForResult(CameraIntent,REQUEST_CODE_FILE_RETURN);

//On activity result, recieve the captured, reviewed, cropped, optimised & compressed image colletion as array.
@Override
protected  void  onActivityResult(int  requestCode,  int  resultCode,  @Nullable  Intent  data)  
{
	super.onActivityResult(requestCode,  resultCode,  data);
	if  (requestCode  ==  REQUEST_CODE_FILE_RETURN  &&  resultCode  ==  Activity.RESULT_OK)
	{  
		Boolean  Status  =  (Boolean)  data.getExtras().get("STATUS");
		String Description  = (String) data.getExtras().get("DESCRIPTION");  
		if(Status  == false){  //Failed  to  capture
		finishActivity(REQUEST_CODE_FILE_RETURN);  return;
	}
	FileCollection  =  (ArrayList<String>)  data.getExtras().get("fileCollection");
	//FileCollection //: will contains all capture images path as string
	finishActivity(REQUEST_CODE_FILE_RETURN);
}
```
```kotlin
//Kotlin
try {
    /*DEV_HELP :redirecting to camera*/
    val captureIntent = Intent(this, Class.forName("com.extrieve.quickcapture.sdk.CameraHelper"))
    val photoURI = Uri.parse(Config.CaptureSupport.OutputPath)
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
```


SDK included a supporting class with static configuration - which includes all configurations related to SDK.Confg contains a sub configuration collection **CaptureSupport** - contains all the Capture & review related configurations.
Config.CaptureSupport  :  contains  various  configurations  as  follows:

- **OutputPath** - To set the output directory in which the captured images will be saved.  Base  app should  have  rights  to write  to the  provided  path.
	```java
 	//JAVA
	Config.CaptureSupport.OutputPath = "pass output path sd string";
	```
	```kotlin
 	//Kotlin
	Config!!.CaptureSupport!!.OutputPath = "pass output path sd string";
	```
- **MaxPage** - To set the number of captures to do on each camera session. And this can also  control  whether  the  capture  mode  is  single  or  multi  i.e
	> if  MaxPage  <= 0 /  not  set:  means  unlimited.If  MaxPage  >= 1:
	> means  limited.
	```java
	//JAVA
	// MaxPage <= 0  : Unlimited Capture Mode  
	// MaxPage = 1   : Limited Single Capture  
	// MaxPage > 1   : Limited Multi Capture Mode  
	Config.CaptureSupport.MaxPage = 0;
	```
	```java
	//Kotlin
	// MaxPage <= 0  : Unlimited Capture Mode  
	// MaxPage = 1   : Limited Single Capture  
	// MaxPage > 1   : Limited Multi Capture Mode  
	Config!!.CaptureSupport!!.MaxPage = 0;
	```
- **ColorMode**  -  To Set the capture color mode - supporting color and grayscale.
	```java
	//JAVA
	Config.CaptureSupport.ColorMode = Config.CaptureSupport.ColorModes.RBG;
	//RBG (1) - Use capture flow in color mode.
	//GREY (2) - Use capture flow in grey scale mode.
	```
	```kotlin
	//Kotlin
	Config!!.CaptureSupport!!.ColorMode = Config!!.CaptureSupport!!.ColorModes!!.RBG;
	//RBG (1) - Use capture flow in color mode.
	//GREY (2) - Use capture flow in grey scale mode.
	```
- **EnableFlash**  -  Enable Document capture specific flash control for SDK camera.
	```java
	//JAVA
	Config.CaptureSupport.EnableFlash  =  true;
	```
	```kotlin
	//Kotlin
	Config!!.CaptureSupport!!.EnableFlash  =  true;
	```
- **CaptureSound**  -  To  Enable  camera  capture  sound.
	```java
	//JAVA
	Config.CaptureSupport.CaptureSound  =  true;
	```
	```kotlin
	//Kotlin
	Config!!.CaptureSupport!!.CaptureSound  =  true;
	```
- **DeviceInfo**  -  Will  share  all  general  information  about  the  device.
	```java
	//JAVA
	Config.CaptureSupport.DeviceInfo;
	```
	```kotlin
	//Kotlin
	Config!!.CaptureSupport!!.DeviceInfo;
	```
- **SDKInfo**  - Contains  all  version  related  information  on  SDK.
	```java
	//JAVA
	Config.CaptureSupport.SDKInfo;
	```
	```kotlin
	//Kotlin
	Config!!.CaptureSupport!!.SDKInfo;
	```

- **CameraToggle**  -  Toggle  camera  between  front  and  back.
	```java
	//JAVA
	 Config.CaptureSupport.CameraToggle = CameraToggleType.ENABLE_BACK_DEFAULT;
	//DISABLED (0) -Disable camera toggle option.
	//ENABLE_BACK_DEFAULT (1) -Enable camera toggle option with Front camera by default.
	//ENABLE_FRONT_DEFAULT (2) -Enable camera toggle option with Back camera  by default.
	```
	```kotlin
	//Kotlin
	 Config!!.CaptureSupport!!.CameraToggle = CameraToggleType!!.ENABLE_BACK_DEFAULT;
	//DISABLED (0) -Disable camera toggle option.
	//ENABLE_BACK_DEFAULT (1) -Enable camera toggle option with Front camera by default.
	//ENABLE_FRONT_DEFAULT (2) -Enable camera toggle option with Back camera  by default.
	```
## ImgHelper
Following  are  the  options/methods  available  from  class  **ImgHelper** :
```java
//JAVA
ImgHelper ImageHelper = new ImgHelper(this);
```
```kotlin
//Kotlin
var ImageHelper: ImgHelper? = ImgHelper(this)
```
- ***SetImageQuality*** - *Set the Quality of the image, Document_Qualityisused.If documents are used further for any automations and OCR, use Document_Quality.*
	 >*Available Image Qualities* :
		1. Photo_Quality.
		2. Document_Quality.
		3. Compressed_Document.
		
	```java
	//JAVA
	ImageHelper.SetImageQuality(ImgHelper.ImageQuality.Photo_Quality.ordinal());
	//--------------------------
	ImageHelper.SetImageQuality(1);//0,1,2 - Photo_Quality, Document_Quality, Compressed_Document
	```
 	```kotlin
  	//Kotlin
	imageHelper!!.SetImageQuality(1)
	```
- ***SetPageLayout*** - *Set the Layout for the images generated/processed by the system.*
	```java
 	//JAVA
	ImageHelper.SetPageLayout(ImgHelper.LayoutType.A4.ordinal());
	//--------------------------
	ImageHelper.SetPageLayout(4);//A1-A7(1-7),PHOTO,CUSTOM,ID(8,9,10)
	```
 	```kotlin
  	//Kotlin
	imageHelper!!.SetPageLayout(4)
	```
	 >*Available layouts* : A1, A2, A3, **A4**, A5, A6, A7,PHOTO & CUSTOM
	 
	*A4 is the most recommended layout for document capture scenarios.*
	 
- ***SetDPI*** - *Set DPI (depth per inch) for the image.*
	```java
 	//JAVA
	ImageHelper.SetDPI(ImgHelper.DPI.DPI_200.ordinal());
	//--------------------------
	ImageHelper.SetDPI(200);//int dpi_val = 150, 200, 300, 500, 600;
	```
	```kotlin
	//Kotlin
	imageHelper!!.SetDPI(200)
	```
	 >*Available DPI* : DPI_150, DPI_200, DPI_300, DPI_500, DPI_600
	 
	 *150 & 200 DPI is most used.And 200 DPI recommended for OCR and other image extraction prior to capture.*
	 
- ***GetThumbnail*** - *This method Will build thumbnail for the given image in custom width,height & AspectRatio.*
	```java
   	//JAVA
	Bitmap thumb = ImageHelper.GetThumbnail(ImageBitmap, 600, 600, true);
	/*
	Bitmap GetThumbnail(
		@NonNull  Bitmap bm,
	    int reqHeight,
	    int reqWidth,
	    Boolean AspectRatio )throws ImgException.
	*/
	```
	```kotlin
	//KOTLIN
	var thumb = ImageHelper!!.GetThumbnail(ImageBitmap, 600, 600, true);

	```
- ***CompressToJPEG*** - *This method will Compress the provided bitmap image and will save to given path..*
	```java

	Boolean Iscompressed = ImageHelper.CompressToJPEG(bitmap,outputFilePath);
	/*
	Boolean CompressToJPEG(Bitmap bm,String outputFilePath)
		throws ImgException

	*/
	```
	
- ***rotateBitmap*** - *This method will rotate the image to preferred orientation.*
	 ```java

	Bitmap rotatedBm = ImageHelper.rotateBitmapDegree(nBm, RotationDegree);
	/*
	Bitmap rotateBitmapDegree(Bitmap bitmap,int Degree)
		throws ImgException
	*/
	```
- **GetTiffForLastCapture**  -  Build  Tiff  file  output  file  from  last  captured  set  of  images.
	```java
	ImageHelper.GetTiffForLastCapture(outPutFileWithpath);
	//on success, will respond with string : "SUCCESS:::TiffFilePath";
	//use  ":::"  char.  key  to  split  the  response.
	//on failure,will respond with string : "FAILED:::Reason for failure";
	//use ":::" char. key to split the response.
	//on failure, error details can collect from CameraSupport.CamConfigClass.LastLogInfo
	```
- **GetPDFForLastCapture**  -  Build  PDF  file  output  file  from  last  captured  set  of  images.
	```java
	ImageHelper.GetPDFForLastCapture(outPutFileWithpath);
	//on success, will respond with string : "SUCCESS:::PdfFilePath";
	//use  ":::"  char.  key  to  split  the  response.
	//on failure,will respond with string : "FAILED:::Reason for failure";
	//use ":::" char. key to split the response.
	//on failure, error details can collect from CameraSupport.CamConfigClass.LastLogInfo
	```
- **BuildTiff**  -  Build  .tiff  file  output  from  the list  of  images  shared.
	```java
	ImageHelper.BuildTiff(ArrayList<String>  ImageCol,  String  OutputTiffFilePath)
	 *@param  "Image  File  path  collection  as  ArrayList<String>"
	 *@return  on  failure  =  "FAILED:::REASON"  ||  on  success  =  "SUCCESS:::TIFF  file  path".
	```
- **BuildPDF**  -  Build  PDF  file  output  file  from  last  captured  set  of  images.
	```java
	ImageHelper.BuildPDF(outPutFileWithpath);
	*@param  "Image  File  path  collection  as  ArrayList<String>"
	*@return  on  failure  =  "FAILED:::REASON"  ||  on  success  =  "SUCCESS:::PDF  file  path".
	```

## ImgException 
As a part of exceptional error handling **ImgException** class is available.
- *Following are the possible errors and corresponding codes*:
	- CREATE_FILE_ERROR= **-100**;
	- IMAGE_ROTATION_ERROR= **-101**;
	- LOAD_TO_BUFFER_ERROR= **-102**;
	- DELETE_FILE_ERROR= **-103**;
	- GET_ROTATION_ERROR= **-104**;
	- ROTATE_BITMAP_ERROR= **-105**;
	- BITMAP_RESIZE_ERROR= **-106**;
	- CAMERA_HELPER_ERROR= **-107**;
	- LOG_CREATION_ERROR= **-108**;
	
## SDK Licensing

*License file provided that should keep inside assets folder of main application and call UnlockImagingLibrary from ImgHelper class to unlock the SDK.*
> **QuickCapture** SDK is absolutely **free** to use.But for image operations on enterprise use cases, license required.
> [Click for license details ](https://www.extrieve.com/mobile-document-scanning/)

```java
	
String licData = readAssetFile("com.extrieve.lic", this);  
Boolean IsUnlocked = ImageHelper.UnlockImagingLibrary(licData)

/*
boolean UnlockImagingLibrary(String licenseFileData)
	throws Exception
*/

```

	
[© 1996 - 2023 Extrieve Technologies](https://www.extrieve.com/)
