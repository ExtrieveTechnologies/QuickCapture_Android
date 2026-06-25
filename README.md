
<img class="img-fluid" align="center" src="https://raw.githubusercontent.com/ExtrieveTechnologies/QuickCapture/main/img/QuickCapture.png" width="30%" alt="img-verification"><img align="right" class="img-fluid" padding="10px" src="https://raw.githubusercontent.com/ExtrieveTechnologies/QuickCapture/main/img/android.png" alt="img-verification">
<!-- <a align="center" href='https://play.google.com/store/apps/details?id=com.extrieve.exScan&pcampaignid=pcampaignidMKT-Other-global-all-co-prtnr-py-PartBadge-Mar2515-1' title="Click to download android app" target="_blank" rel="noopener noreferrer"><img align="center" width="150px" alt='Get it on Google Play' src='https://play.google.com/intl/en_us/badges/static/images/badges/en_badge_web_generic.png'/></a> -->

<p align="left">
  <a href="https://github.com/ExtrieveTechnologies"><img src="https://img.shields.io/badge/GitHub-Extrieve%20Org-181717?style=flat&logo=github" alt="GitHub Org" /></a>
  <a href="https://github.com/ExtrieveTechnologies/QuickCapture"><img src="https://img.shields.io/badge/Repo-QuickCapture-blue?style=flat&logo=github" alt="QuickCapture Repo" /></a>
  <a href="https://github.com/orgs/ExtrieveTechnologies/discussions/categories/technical-support?discussions_q="><img src="https://img.shields.io/badge/Community-Discussions & Technical support-purple?style=flat&logo=github" alt="Community" /></a>
  <a href="https://contact.extrieve.com?ptype=QC-Home"><img src="https://img.shields.io/badge/Contact-Talk%20to%20Us-success?style=flat&logo=maildotru&logoColor=white" alt="Contact" /></a>
</p>


# Extrieve QuickCapture v4.2
<img src="https://img.shields.io/badge/AI-KIMORA%20AI%20v3-0A66C2?style=for-the-badge&logo=openai&logoColor=white" />

QuickCapture Mobile Document Scanning, Imaging & Seccurity SDK Specially designed for native ANDROID from [Extrieve](https://www.extrieve.com/).

Now powered by **KIMORA Ai V2**, our advanced AI model for intelligent document detection.
Trained on a vast dataset of real-world documents, KIMORA delivers precise and reliable edge detection & auto document angle correction using a custom-designed AI architecture.

> It's not "**just**" a scanning SDK. It's a "**document**" 
scanning/capture SDK evolved with **Best Quality**, **Highest Possible Compression**, **Image Optimisation**, keeping output quality of the document in mind.

> Control **DPI**,**Layout** & **Size** of output images and can convert them into **PDF & TIFF**

> Automatically detect and correct the orientation/rotation of captured documents using the KIMORA AI v2 engine.

> **QR code** & **BAR Code** Scanning & Generation

> **Developer-friendly** & **Easy to integrate** SDK.

> **DeviceGuard** for Anti-Spoofing (Root, Emulator, Mock Location detection) and **DeviceInfo** for offline **DigiPin** generation and hardware intelligence.
 
> **Works entirely offline**, locally on the device, with **no data transferred to any server or third party**.  

*For reduced build size if needed, an initial internet connection may optionally be required to fetch ML data or resource files, depending on the specific integration and features used by the consumer application*

> **End of support Notice** :
> QuickCapture SDK Android **V1** deprecated by Dec. 2022.For any further updates and support, can use **V2**
> which having no major modifications.But with improved funcionalities,feature additions and fixes.
> 
> QuickCapture SDK Android **V2** deprecated by May. 2024.For any further updates and support, can use **V4** & bugfixes on **V3** 

Access / Download
--------
You can use this SDK in any Android project simply by using Gradle :

```java
//Add expack central repo in settings.gradle (Project Settings)
repositories {
  google()
  mavenCentral()
  maven {url 'https://expack.extrieve.in/maven/'}
}

//Then add implementation for SDK in dependencies in build.gradle (module:<yourmodulename>)
dependencies {
  implementation 'com.extrieve.quickcapture:QCv4_PLUS:<SDK-VERSION>'
}
//SDK-VERSION - Need to replace with the correct v4 series.
```

Or Maven:

```xml
<dependency>
  <groupId>com.extrieve.quickcapture</groupId>
  <artifactId>QCv4_PLUS</artifactId>
  <version>SDK-VERSION</version>
</dependency>
//SDK-VERSION - Need to replace with the correct v4 series
```

Or can even integrate with the **.aar** library file and manually add the file dependency to the project/app.



Compatibility

-------------

*  **JAVA 17 Support**: QuickCapture v4 requires JAVA version 17 support for the application.

*  **Minimum Android SDK**: QuickCapture v4 requires a minimum API level of 21.

*  **Target Android SDK**: QuickCapture v4 features supports **API 35**.

*  **Compiled SDK Version**: QuickCapture v4 compiled against **API 34**.Host application using this SDK should compiled against 34 or later

## Run-time requirement

  

- [x] This SDK is designed to run on officially supported Android & iOS devices only.

- [x] On Android, Google Play Services is mandatory.

- [x] Supported CPU architectures: arm64-v8a and armeabi-v7a.

- [x] Simulator and emulator environments are not supported.For testing on simulators, please contact the development support team to request a dedicated test version compatible with those environments.

  

# API & integration Details

Available properties and method

  

SDK exposes the following core classes plus supporting classes :

  

1.  **CameraHelper** - *Handles the camera related operations. Basically, an activity.*

2.  **ImgHelper** - *Purpose of this class is to handle all imaging related operations.*

3.  **OpticalCodeHelper** - *Handles the Optical code (QR CODE & BAR CODE) related activities*

4.  **HumanFaceHelper** - *Advanced Ai based utility class handles all functionalities such as face detection, extraction,matching & related functions.*

5.  **Config** - *Holds various configurations for SDK including licensing*

  

In addition, two **dedicated capture activities** are launched by class name (just like `CameraHelper`): **SignatureCapture** (signature capture — see §2) and **HumanFaceCapture** (face-photo capture — see §5).

  

Based on the requirement, any one or all classes can be used.And need to import those from the SDK.

```java

import com.extrieve.quickcapture.sdk.*;

//OR : can import only required classes as per use cases.

import com.extrieve.quickcapture.sdk.ImgHelper;

import com.extrieve.quickcapture.sdk.CameraHelper;

import com.extrieve.quickcapture.sdk.OpticalCodeHelper;

import com.extrieve.quickcapture.sdk.Config;

import com.extrieve.quickcapture.sdk.HumanFaceHelper;

import com.extrieve.quickcapture.sdk.ImgException;

```

---

## 1. CameraHelper

This core class will be implemented as an activity.This class can be initialized as intent.

```java

//JAVA

CameraHelper  CameraHelper = new  CameraHelper();

```

```kotlin

//Kotlin

var cameraHelper: CameraHelper? = CameraHelper()

```

  

With an activity call, triggering the SDK for capture activity can be done.Most operations in **CameraHelper** is **activity based**.

  

SDK is having multiple flows as follows :

*  **CAMERA_CAPTURE_REVIEW** - *Default flow. Capture with SDK Camera **->** review.*

*  **SYSTEM_CAMERA_CAPTURE_REVIEW** - *Capture with system default camera **->** review.*

*  **IMAGE_ATTACH_REVIEW** - *Attach/pass image **->** review.*

  

**1. CAMERA_CAPTURE_REVIEW** - *Default flow of the CameraHelper. Includes Capture with SDK Camera -> Review Image.*

  

```java

//JAVA

  

//Set CaptureMode as CAMERA_CAPTURE_REVIEW

Config.CaptureSupport.CaptureMode = Config.CaptureSupport.CaptureModes.CAMERA_CAPTURE_REVIEW;

//set permission for output path that set in config.

UriphotoURI = Uri.parse(Config.CaptureSupport.OutputPath);

this.grantUriPermission(this.getPackageName(),photoURI,Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);

  

//Create CameraIntent for CameraHelper activity call.

Intent  CameraIntent = new  Intent(this,Class.forName("com.extrieve.quickcapture.sdk.CameraHelper"));

if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {

CameraIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

}

//Call the Activity.

startActivityForResult(CameraIntent,REQUEST_CODE_FILE_RETURN);

  

//On activity result,recieve the captured, reviewed, cropped, optimised & compressed image collection as array.

@Override

protected  void  onActivityResult(int requestCode, int resultCode, @Nullable  Intent data)

{

super.onActivityResult(requestCode, resultCode, data);

if (requestCode == REQUEST_CODE_FILE_RETURN && resultCode == Activity.RESULT_OK)

{

Boolean  Status = (Boolean)data.getExtras().get("STATUS");

String  Description = (String)data.getExtras().get("DESCRIPTION");

if(Status == false){

//Failed to capture

}

finishActivity(REQUEST_CODE_FILE_RETURN); return;

}

FileCollection = (ArrayList<String>)data.getExtras().get("fileCollection");

//FileCollection //: will contain all capture images path as string

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

Toast.makeText(this, "Failed to open camera -" + ex.message, Toast.LENGTH_LONG).show()

}

```

  

**2. SYSTEM_CAMERA_CAPTURE_REVIEW** - *If user needs to capture an image with system default camera, this can be used. It includes Capture with system default camera -> Review*.

  

```java

//JAVA

  

//Set CaptureMode as SYSTEM_CAMERA_CAPTURE_REVIEW

Config.CaptureSupport.CaptureMode = Config.CaptureSupport.CaptureModes.SYSTEM_CAMERA_CAPTURE_REVIEW;

//set permission for output path that is set in config.

UriphotoURI = Uri.parse(Config.CaptureSupport.OutputPath);

this.grantUriPermission(this.getPackageName(),photoURI,Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);

  

//Create CameraIntent for CameraHelper activity call.

Intent  CameraIntent = new  Intent(this,Class.forName("com.extrieve.quickcapture.sdk.CameraHelper"));

if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {

CameraIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

}

//Call the Activity.

startActivityForResult(CameraIntent,REQUEST_CODE_FILE_RETURN);

  

//On activity result,recieve the captured, reviewed, cropped, optimised & compressed image colletion as array.

@Override

protected  void  onActivityResult(int requestCode, int resultCode, @Nullable  Intent data)

{

super.onActivityResult(requestCode, resultCode, data);

if (requestCode == REQUEST_CODE_FILE_RETURN && resultCode == Activity.RESULT_OK)

{

Boolean  Status = (Boolean)data.getExtras().get("STATUS");

String  Description = (String)data.getExtras().get("DESCRIPTION");

if(Status == false){

//Failed to capture

}

finishActivity(REQUEST_CODE_FILE_RETURN); return;

}

FileCollection = (ArrayList<String>)data.getExtras().get("fileCollection");

//FileCollection //: will contain all capture images path as string

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

Toast.makeText(this, "Failed to open camera -" + ex.message, Toast.LENGTH_LONG).show()

}

```

  

**3. IMAGE_ATTACH_REVIEW** - *This option can be used if the user needs to review an image from their device's gallery. After attaching each image, the review and all dependent functionalities become available*.

  

```java

//JAVA

  

//Set CaptureMode as IMAGE_ATTACH_REVIEW

Config.CaptureSupport.CaptureMode = Config.CaptureSupport.CaptureModes.IMAGE_ATTACH_REVIEW;

//Create/Convert/ get Image URI from image source.

Uri  ImgUri = data.getData();

//Create ReviewIntent for CameraHelper activity call.

Intent  ReviewIntent = new  Intent(this,Class.forName("com.extrieve.quickcapture.sdk.CameraHelper"));

//Add the image URI to intent request with a key : ATTACHED_IMAGE.

ReviewIntent.putExtra("ATTACHED_IMAGE", ImUri);

//Call the Activity.

startActivityForResult(ReviewIntent,REQUEST_CODE_FILE_RETURN);

  

//On activity result,recieve the captured, reviewed, cropped, optimised & compressed image colletion as array.

@Override

protected  void  onActivityResult(int requestCode, int resultCode, @Nullable  Intent data)

{

super.onActivityResult(requestCode, resultCode, data);

if (requestCode == REQUEST_CODE_FILE_RETURN && resultCode == Activity.RESULT_OK)

{

Boolean  Status = (Boolean)data.getExtras().get("STATUS");

String  Description = (String)data.getExtras().get("DESCRIPTION");

if(Status == false){

//Failed to capture

}

finishActivity(REQUEST_CODE_FILE_RETURN); return;

}

FileCollection = (ArrayList<String>)data.getExtras().get("fileCollection");

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

Toast.makeText(this, "Failed to open camera -" + ex.message, Toast.LENGTH_LONG).show()

}

```

## 2. SignatureCapture

A dedicated activity for capturing **signatures**. It is built on the same camera → review → save pipeline as `CameraHelper`, but with a signature-specific UX and **no AI / no model loading**.

  

-  On the **live preview** it shows a centered, signature-aspect **guide box** with a centre alignment dot and an animated sample signature, and the hint *"Keep your signature inside the box, then tap Capture"*. No live document detection runs; the user taps the shutter manually.

-  On **review** the crop rectangle is **pre-seeded to the guide box**; the user adjusts if needed and taps Crop.

-  The **result is returned exactly like document capture** — same `STATUS` / `DESCRIPTION` / `fileCollection` / `DATA` extras, read the same way as for `CameraHelper`.

Launch it like `CameraHelper` :

```java

//JAVA

Config.CaptureSupport.OutputPath = "<output directory path>";

Uri photoURI = Uri.parse(Config.CaptureSupport.OutputPath);

this.grantUriPermission(this.getPackageName(), photoURI, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);

Intent intent = new Intent(this, Class.forName("com.extrieve.quickcapture.sdk.SignatureCapture"));

startActivityForResult(intent, REQUEST_CODE);

```

```kotlin

//Kotlin

Config!!.CaptureSupport!!.OutputPath = "<output directory path>"

val photoURI = Uri.parse(Config!!.CaptureSupport!!.OutputPath)

this.grantUriPermission(this.packageName, photoURI, Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION)

val intent = Intent(this, Class.forName("com.extrieve.quickcapture.sdk.SignatureCapture"))

startActivityForResult(intent, REQUEST_CODE)

```

  

**Defaults** — the activity applies signature-tuned defaults at start; each is **overridable from the host via `Config.CaptureSupport`** before launch, and is restored to its prior value when the activity returns (so it never affects a later document capture):

> **Cropping** — `ManualCrop` (no AI; manual crop on review). Fixed by the activity.

> **Crop filter** — `NONE` (raw capture; no filter forced). A host that wants enhancement sets `Config.CaptureSupport.CropFilter` before launch — e.g. `MAGIC_COLOR` (document whitening) or `INK` (crisp black-on-white binarisation).

> **Output DPI** — `150`. Override via `Config.CaptureSupport.OutputDPI`.

> **Page layout** — CUSTOM `560 × 200 px` (= 2.8:1, matched to the guide box so the crop maps 1:1). Override via `Config.CaptureSupport.CustomLayoutWidthPx` / `CustomLayoutHeightPx`.

> **Format** — JPEG (Document quality), same encoder as document capture. Override via `Config.CaptureSupport` image quality.

  

All the usual capture settings still apply via `Config.CaptureSupport` — **page layout, DPI, image quality, bottom stamping, multi-capture / `MaxPage`, crop filter, resize mode, output path**.

> **Roadmap:** optional AI auto-detection of the signature region for hands-free crop — it will slot in behind the same activity without changing the result contract.

  

## 3. Config

The SDK includes a supporting class called for static configuration. This class holds all configurations related to the SDK. It also contains sub-configuration collection for further organization. This includes: :

  

**CaptureSupport** - Contains all the Capture & review related configurations. **Config.CaptureSupport** contains various configurations as follows:

  

-  **BottomStampData** - This configuration will automatically print the specified text at the bottom of the captured image with correct alignment, font size and DPI**. This also supports placeholders, such as `{DATETIME}`, which will be replaced with the current date and time from the device at the time of stamping. **$** - for new line print.

```java

//JAVA

Config.CaptureSupport.BottomStampData ="{DATETIME} , Other info $ next line info.";

```

```kotlin

//Kotlin

Config!!.CaptureSupport!!.BottomStampData = ="{DATETIME} , Other info $ next line info.";

```

  

-  **OutputPath** - To set the output directory in which the captured images will be saved, base app should have rights to write to the provided path.

```java

//JAVA

Config.CaptureSupport.OutputPath = "pass output path sd string";

```

```kotlin

//Kotlin

Config!!.CaptureSupport!!.OutputPath = "pass output path sd string";

```

-  **MaxPage** - To set the number of captures to do on each camera session, can also control whether the capture mode is single or multi i.e :

> if MaxPage <= 0 / not set: means unlimited.If MaxPage >= 1:

> means limited.

```java

//JAVA

// MaxPage <= 0 : Unlimited Capture Mode

// MaxPage = 1 : Limited Single Capture

// MaxPage > 1 : Limited Multi Capture Mode

Config.CaptureSupport.MaxPage = 0;

```

```java

//Kotlin

// MaxPage <= 0 : Unlimited Capture Mode

// MaxPage = 1 : Limited Single Capture

// MaxPage > 1 : Limited Multi Capture Mode

Config!!.CaptureSupport!!.MaxPage = 0;

```

-  **ColorMode** - To Set the capture color mode - supporting color and grayscale.

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

-  **EnableFlash** - Enable Document capture specific flash control for SDK camera.

```java

//JAVA

Config.CaptureSupport.EnableFlash = true;

```

```kotlin

//Kotlin

Config!!.CaptureSupport!!.EnableFlash = true;

```

-  **CaptureSound** - To Enable camera capture sound.

```java

//JAVA

Config.CaptureSupport.CaptureSound = true;

```

```kotlin

//Kotlin

Config!!.CaptureSupport!!.CaptureSound = true;

```

  

-  **CameraToggle** - Toggle camera between front and back.

```java

//JAVA

Config.CaptureSupport.CameraToggle = CameraToggleType.ENABLE_BACK_DEFAULT;

//DISABLED (0) -Disable camera toggle option.

//ENABLE_BACK_DEFAULT (1) - Enable camera toggle option with Front camera by default.

//ENABLE_FRONT_DEFAULT (2) - Enable camera toggle option with Back camera by default.

```

```kotlin

//Kotlin

Config!!.CaptureSupport!!.CameraToggle = CameraToggleType!!.ENABLE_BACK_DEFAULT;

//DISABLED (0) -Disable camera toggle option.

//ENABLE_BACK_DEFAULT (1) - Enable camera toggle option with Front camera by default.

//ENABLE_FRONT_DEFAULT (2) - Enable camera toggle option with Back camera by default.

```

  

-  **DocumentCropping** - Controls the document detection & cropping behaviour after capture. Default is **AutoCapture**. Supported modes (`Config.CaptureSupport.CroppingType`):

> **AutoCapture** - Live document edge detection; the SDK auto-captures when a stable document is found, then crops + perspective-corrects on review. (Auto/Assisted toggle shown.)

> **AssistedCapture** - Live document edge detection assists the user, who taps the shutter manually. (Auto/Assisted toggle shown.)

> **AutoCrop** - Detects and crops the document automatically from the captured image (no auto-capture toggle).

> **Disabled** - Plain photo / asset capture: no edge detection, no ML model loading, no crop. The image is saved as captured.

> **ManualCrop** *(new)* - No live detection and **no ML model loading** during capture (manual shutter only, auto/assisted toggle hidden); on review the user is shown a freely-adjustable rectangle to crop any region of the captured image. AI auto-rotation is skipped (the user can rotate manually). Ideal for **signature** and **ID / card** capture, where the user shoots directly and then crops the needed area.

```java

//JAVA

Config.CaptureSupport.DocumentCropping = Config.CaptureSupport.CroppingType.ManualCrop;

// AutoCapture | AssistedCapture | AutoCrop | Disabled | ManualCrop

```

```kotlin

//Kotlin

Config!!.CaptureSupport!!.DocumentCropping = Config!!.CaptureSupport!!.CroppingType!!.ManualCrop

```

  

-  **CropFilter** - Image-enhancement filter applied to the cropped result. It is also the default selection in the review **filter chooser**, which the user can change on the review screen (the user's pick is what gets saved). Default **NONE** (raw). Options (`Config.CaptureSupport.CropImageFilterType`): `NONE`, `ENHANCE`, `MAGIC_COLOR`, `COLOR`, `LIGHTEN`, `GRAY`, `XEROX`, `INK`.

```java

//JAVA

Config.CaptureSupport.CropFilter = Config.CaptureSupport.CropImageFilterType.MAGIC_COLOR;

```

```kotlin

//Kotlin

Config!!.CaptureSupport!!.CropFilter = Config!!.CaptureSupport!!.CropImageFilterType!!.MAGIC_COLOR

```

  

**Review** - Review-screen behaviour, configured under `Config.CaptureSupport.Review`:

-  **EnableCropReview** - When `true` (default), the crop-suggestion overlay is shown on review so the user can adjust the corners before cropping. When `false`, the SDK auto-crops immediately using the detected/seeded polygon without showing the suggestion. *(`ManualCrop` always shows the interactive crop regardless of this flag.)*

```java

//JAVA

Config.CaptureSupport.Review.EnableCropReview = true;

```

```kotlin

//Kotlin

Config!!.CaptureSupport!!.Review!!.EnableCropReview = true

```

-  **EnableAutoCorrectDocAngle** - When `true` (default), AI-based automatic document-rotation correction is applied to the cropped image at save time (using the bundled rotation classifier). Set `false` to keep the captured orientation. *(`ManualCrop` and `Disabled` never apply AI auto-rotation, regardless of this flag.)*

```java

//JAVA

Config.CaptureSupport.Review.EnableAutoCorrectDocAngle = true;

```

```kotlin

//Kotlin

Config!!.CaptureSupport!!.Review!!.EnableAutoCorrectDocAngle = true

```

  

**Common** - Contains various configurations as follows:

  

-  **SDKInfo** - Contains all version related information on SDK.

```java

//JAVA

Config.Common.SDKInfo;

```

```kotlin

//Kotlin

Config!!.Common!!.SDKInfo;

```

-  **DeviceInfo** - Will share all general information about the device.

```java

//JAVA

Config.Common.DeviceInfo;

```

```kotlin

//Kotlin

Config!!.Common!!.DeviceInfo;

```

-  **getAnalyticsForLastCapture()** *(new)* - Returns per-image capture analytics for the **most recent capture session** as a JSON string. The collection is cleared at the start of every capture session and populated as each image is compressed, so it returns one entry per image captured in that session. Each entry reports the **original source** file size & dimensions, the **uncompressed in-memory** bitmap size, and the **final compressed JPEG** size & dimensions on disk — useful for showcasing the SDK's compression. Compare `originalCapture.sizeBytes` to `afterCompression.sizeBytes` for a true file-to-file compression ratio. Unknown numeric values are emitted as `-1`. Prefer calling this off the main thread.

```java

//JAVA

String analyticsJson = Config.Common.getAnalyticsForLastCapture();

```

```kotlin

//Kotlin

val analyticsJson = Config!!.Common!!.getAnalyticsForLastCapture()

```

> Sample response:

```json

{
  "schemaVersion": 2,
  "imageCount": 1,
  "images": [
    {
      "id": "QC_20260619_120000",
      "path": "/storage/emulated/0/Android/data/<app>/files/QC/QC_....jpg",
      "capturedAtMs": 1718524800123,
      "originalCapture":   { "widthPx": 4032, "heightPx": 3024, "sizeBytes": 4193280 },
      "beforeCompression": { "widthPx": 3024, "heightPx": 4032, "sizeBytes": 48771072 },
      "afterCompression":  { "widthPx": 2480, "heightPx": 1860, "sizeBytes": 524288 }
    }
  ]
}

```

> `originalCapture` = the camera/source JPEG (or attached file). `beforeCompression` = the uncompressed in-memory bitmap (RAM footprint, not a file). `afterCompression` = the JPEG the SDK wrote to disk.

  

**License** - Cotrolls all activities relates to licensing.

-  ***Activate*** - *Method to activate the SDK license.*

```java

//JAVA

Config.License.Activate(hostApplicationContext,licenseString);

```

```kotlin

//Kotlin

Config!!.License!!.Activate(hostApplicationContext,licenseString)

```

> **hostApplicationContext** : Application context of host/client application which is using the SDK.

> **licenseString** : Licence data in string format.

  

## 4. ImgHelper

Following are the options/methods available from class **ImgHelper** :

```java

//JAVA

ImgHelper  ImageHelper = new  ImgHelper(this);

```

```kotlin

//Kotlin

var ImageHelper: ImgHelper? = ImgHelper(this)

```

-  ***SetImageQuality*** - *Set the Quality of the image, Document_Quality is used. If documents are used further for any automations and OCR, use Document_Quality.*

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

-  ***SetPageLayout*** - *Set the Layout for the images generated/processed by the system.*

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

-  ***SetDPI*** - *Set DPI(depth per inch) for the image.*

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

-  ***GetThumbnail*** - *This method Will build thumbnail for the given image in custom width,height & AspectRatio.*

```java

//JAVA

Bitmap  thumb = ImageHelper.GetThumbnail(ImageBitmap, 600, 600, true);

/*

Bitmap GetThumbnail(

@NonNull Bitmap bm,

int reqHeight,

int reqWidth,

Boolean AspectRatio )throws ImgException.

*/

```

```kotlin

//KOTLIN

var thumb = ImageHelper!!.GetThumbnail(ImageBitmap, 600, 600, true);

```

-  ***CompressToJPEG*** - *This method will Compress the provided bitmap image and will save to given path.*

```java

//JAVA

Boolean  Iscompressed = ImageHelper.CompressToJPEG(bitmap,outputFilePath);

/*

Boolean CompressToJPEG(Bitmap bm,String outputFilePath)

throws ImgException

*/

```

```kotlin

//KOTLIN

var Iscompressed = ImageHelper!!.CompressToJPEG(bitmap, outputFilePath);

```

-  **GetTiffForLastCapture** - Build Tiff output file from last captured set of images.

```java

//JAVA

ImageHelper.GetTiffForLastCapture(outPutFileWithpath);

//on success, will respond with string : "SUCCESS:::TiffFilePath";

//use ":::" char. key to split the response.

//on failure,will respond with string : "FAILED:::Reason for failure";

//use ":::" char. key to split the response.

//on failure, error details can collect from CameraSupport.CamConfigClass.LastLogInfo

```

```kotlin

//KOTLIN

var thumb = ImageHelper!!.GetTiffForLastCapture(outPutFileWithpath);

```

-  **GetPDFForLastCapture** - Build PDF output file from last captured set of images.

```java

//JAVA

ImageHelper.GetPDFForLastCapture(outPutFileWithpath);

//on success, will respond with string : "SUCCESS:::PdfFilePath";

//use ":::" char. key to split the response.

//on failure,will respond with string : "FAILED:::Reason for failure";

//use ":::" char. key to split the response.

//on failure, error details can collect from CameraSupport.CamConfigClass.LastLogInfo

```

```kotlin

//KOTLIN

var thumb = ImageHelper!!.GetPDFForLastCapture(outPutFileWithpath);

```

-  **BuildTiff** - Build tiff output file from the list of images shared.

```java

//JAVA

ImageHelper.BuildTiff(ImageCol,OutputTiffFilePath);

*@param  "Image File path collection as ArrayList<String>".

*@param  "Output Tiff FilePath as String".

*@return on failure = "FAILED:::REASON" || on success = "SUCCESS:::TIFF file path".

```

```kotlin

//KOTLIN

var thumb = ImageHelper!!.BuildTiff(ImageCol,OutputTiffFilePath);

```

-  **BuildPDF** - Build PDF output file from last captured set of images.

```java

//JAVA

ImageHelper.BuildPDF(ImageCol,outPutPDFFileWithpath);

*@param  "Image File path collection as ArrayList<String>"

*@param  "Output Tiff FilePath as String".

*@return on failure = "FAILED:::REASON" || on success = "SUCCESS:::PDF file path".

```

```kotlin

//KOTLIN

var thumb = ImageHelper!!.BuildPDF(ImageCol,OutputTiffFilePath);

```

>  **Recommended Settings:**

>  - ImageQuality: `documentQuality`

>  - DPI: `150` or `200`

>  - LayoutType: `A4`

>  - ResizeMode: `preserveAspectOnly`


- **ReadImageInfo** 
   `ReadImageInfo` retrieves metadata from an image file such as **height**, **width**, **color mode**, and **page count**.
   Supported image formats:
	-   **TIFF**
	-   **JPEG / JPG**
	-   **PNG**
	-   **BMP**

```

//JAVA
String resultJson = ImageHelper.ReadImageInfo(filePath)
*@param  "Full path of the image file in String"

*@return String
// on failure = "FAILED:::REASON" || on success = "SUCCESS:::JSON_RESPONSE"

```

```

//KOTLIN
val resultJson = ImageHelper!!.ReadImageInfo(filePath)
 
```
## ❌ Failure Response (Returned as String containing JSON)

`{  "STATUS":  false,  "ERROR_MESSAGE":  "Reason for failure"  }`

## ✅ Success Response (Returned as String containing JSON)

```
{  
	"FILESIZE":  146 KB,  
	"TOTAL_PAGES":  1,  
	"INFORMATION":  [  
		{  
			"PAGENO":  1,  
			"WIDTH":  475,  
			"HEIGHT":  307,  
			"COLOR_TYPE":  "GREY"  
		}  
	]  
} 
```
----------

## Field Description

| Field           | Description                                              |
| --------------- | -------------------------------------------------------- |
| **FILESIZE**    | Total size of the image file in KB                       |
| **TOTAL_PAGES** | Number of pages (TIFF may contain multiple; JPG/PNG = 1) |
| **PAGE_NO**     | Current page number in the image                         |
| **WIDTH**       | Width of the image in pixels                             |
| **HEIGHT**      | Height of the image in pixels                            |
| **COLOR_TYPE**  | Image color type: `COLOR`, `GREY`, or `BW`               |


## 5. HumanFaceHelper

  

QuickCapture SDK equipped with advanced face identification intelligence can accurately detect human faces within documents and match them precisely.**SDK needs to be activated using a proper license** with Config.License.Acivate(); for the plus features to initialise.

  

```java

//JAVA

HumanFaceHelper  humanFaceObj = new  HumanFaceHelper(this);

```

  

```kotlin

//Kotlin

var humanFaceObj:HumanFaceHelper? = HumanFaceHelper(this)

```

  

Following are the options/methods available from the class **HumanFaceHelper** :

  

-  **detectHumanFaces**  \- detectHumanFaces Method from \*\*humanFaceObj \*\* will Identify human faces from provided image and return the detected details. .

```java

//detectHumanFaces will use callback function to return the result.

humanFaceObj.detectHumanFaces(inputImage,detectHumanFacesCallBack);

*@param  : inputImage "input image in Bitmap".

*@param  :  "A callback method to capture the detected human faces response".

function detectHumanFacesCallBack(resultJson) {

//Process the resultJson

}

  

//Or use lambda function

humanFaceObj.detectHumanFaces(inputImage,resultJson -> {

//Detected response JSON

});

```

Following is a sample of response structure :

```java

{

STATUS:  true/false,

//Detection status

DESCRIPTION :  "SUCCESS",

//Success or failure description

DOC_ID :  0,

//Identifier/index of the used document.

FACE_DATA :[

//Collection of identified face data

{

ID :  0,

//Identifier/index of face.

LEFT :  0,

TOP :  0,

RIGHT :  0,

BOTTOM :  0

//Each location of face in document

}

]

}

```

-  **matchHumanFaces**  \- detectHumanFaces Method from **humanFaceObj**  \- With AI intelligence, analyses the provided face data and returns a response on whether the provided faces are of same human or not.Document Id and Face Id will be provided by detectHumanFaces, and same can be used.

```java

//matchHumanFaces will use callback function to return the result.

humanFaceObj.matchHumanFaces(DocId1,FaceId1,DocId2,FaceId2,matchHumanFacesCallBack);

*@param  "ID of first document"

*@param  "Face ID of first document"

*@param  "ID of second document"

*@param  "Face ID of second document".

*@param  :  "A callback method to capture the match human faces response".

function matchHumanFacesCallBack(resultJson) {

//Process the resultJson

}

  

//Or use lambda function

humanFaceObj.matchHumanFaces(DocId1,FaceId1,DocId2,FaceId2,,resultJson -> {

//Detected response JSON

});

```

Following is a sample response structure :

```java

{

STATUS:  true/false,

//Match status

DESCRIPTION :  "SUCCESS",

//Success or failure description

ACCURACY:  0,

//Accuracy of match

}

```

### Face Match Accuracy Interpretation

The match level is determined based on the accuracy percentage, which reflects the similarity between two facial images. The table below provides detailed descriptions for each match level.

| **Match Percentage** | **Match Level** | **Description** |

|--|--|--|

| **90% - 100%** | ✅ **Highly Reliable Match** | Faces match with extremely high confidence. They are almost certainly the same person. Suitable for critical identification applications. |

| **75% - 89%** | ✅ **Strong Match** | Faces matched successfully with a high probability of being the same person. Reliable for most identity verification use cases. |

| **65% - 74%** | ⚠️ **Moderate Match** | Faces show good similarity, but further validation may be required. Manual verification is recommended before confirmation. |

| **50% - 64%** | ⚠️ **Low Confidence Match** | Faces have some resemblance, but the similarity is not strong enough to confirm identity. Additional verification is needed. |

| **0% - 49%** | ❌ **No Match** | Faces do not match. There is minimal similarity, and they are highly unlikely to be the same person. |

  

### Usage of Results

  

#### ✅ **Highly Reliable Match (90% - 100%)**

-  **Best for**: Secure identity verification, biometric authentication, and critical decision-making.

-  **Action**: **Automatic acceptance. No further review required.**

  

#### ✅ **Strong Match (75% - 89%)**

-  **Best for**: General identification scenarios where strong confidence is required.

-  **Action**: **Safe for automatic approval** in most applications.

  

#### ⚠️ **Moderate Match (65% - 74%)**

-  **Best for**: Cases where additional review is acceptable before finalizing the decision.

-  **Action**: **Manual verification recommended before confirming a match.**

  

#### ⚠️ **Low Confidence Match (50% - 64%)**

-  **Best for**: Situations requiring strict validation before acceptance.

-  **Action**: **Use alternative verification methods. Do not rely on this score alone.**

  

#### ❌ **No Match (0% - 49%)**

-  **Best for**: Definitive rejection of mismatches.

-  **Action**: **Automatically reject matches in this range.**

  

### Capturing a face photo — `HumanFaceCapture` activity

A dedicated activity to capture a **face photo** (selfie / passport-style). It runs live **face-quality gating** (centring, distance, eyes-open, pose, lighting, blur), **auto-captures** when the face is steady and in-spec (a manual shutter is always available too), crops a **4:5 portrait** around the face, optionally enhances it, and saves it via `ImgHelper`. The result is returned with the **same contract as document capture**, so a single `onActivityResult` handler can serve both flows.

```java

//JAVA

Intent i = new Intent(this, Class.forName("com.extrieve.quickcapture.sdk.HumanFaceCapture"));

// Optional input extras:

// i.putExtra("force_front_camera", true);  // lock to the front lens

// i.putExtra("force_back_camera",  true);  // lock to the back lens

// i.putExtra("enhance_photo",      false); // default true; false = unmodified pixels

startActivityForResult(i, REQUEST_CODE);

```

**Optional input extras**

> **`force_front_camera`** / **`force_back_camera`** (boolean) — lock the lens; otherwise the user can toggle front/back.

> **`enhance_photo`** (boolean, default `true`) — apply adaptive, lighting- and blur-aware enhancement (adaptive exposure + mild local contrast + white-balance / saturation; authentic, no beauty smoothing). Set `false` for forensic / BFSI flows that require unmodified pixels.

**Result** — same contract as document capture: `STATUS` (boolean), `DESCRIPTION` (String), `fileCollection` (`ArrayList<String>`, one path on success), `DATA` (String JSON — `[{"ID":filename,"PATH":absolutePath}]`). The legacy `face_photo_path` extra is also set on the same Intent for backwards compatibility.

  

## 6. OpticalCodeHelper

Following are the options/methods available from class **OpticalCodeHelper** :

```java

//JAVA

OpticalCodeHelper  opticalCodeObj = new  OpticalCodeHelper(this);

```

```kotlin

//Kotlin

var opticalCodeObj : OpticalCodeHelper? = OpticalCodeHelper(this)

```

-  ***GenerateQRCode*** - *Method to generate QR Code*.Data need to pass in string format.Will return a bitmap of generated QR Code.

```java

//JAVA

Bitmap  qrcode = opticalCodeObj.GenerateQRCode(QRdata);

```

```kotlin

//KOTLIN

var qrcode = opticalCodeObj!!.GenerateQRCode(QRdata);

```

-  ***GenerateBarcode*** - *Method to generate BAR Code*.Data need to pass in string format.Will return a bitmap of generated BAR Code.

```java

//JAVA

Bitmap  qrcode = opticalCodeObj.GenerateBarcode(QRdata);

```

```kotlin

//KOTLIN

var qrcode = opticalCodeObj!!.GenerateBarcode(QRdata);

```

-  ***GenerateBarcodeWithText*** - *Method to generate GenerateBarcodeWithText*.Data need to pass in string format.Will return a bitmap of generated BAR Code with visible text.

```java

//JAVA

Bitmap  qrcode = opticalCodeObj.GenerateBarcode(QRdata);

```

```kotlin

//KOTLIN

var qrcode = opticalCodeObj!!.GenerateBarcode(QRdata);

```

-  **Scan / Capture OpticalCodes (QR Code / BAR Code)** - *Option to Scan or capture the **Optical Codes**.This is an activity based call.On activity result, will get the extracted data from the optical code*

  

```java

//JAVA

//Register the activity results

private  OpticalCodeActivityResultLauncher<Intent> registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result ->  handleOpticalCaptureActivityResult(result));

//trigger the activity

try {

Intent  CameraIntent = new  Intent(this, Class.forName("com.extrieve.quickcapture.sdk.OpticalCodeHelper"));

Uri  photoURI = Uri.parse(Config.CaptureSupport.OutputPath);

this.grantUriPermission(this.getPackageName(), photoURI, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);

if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {

CameraIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

}

captureActivityResultLauncher.launch(CameraIntent);

} catch (ClassNotFoundException  e) {

Toast.makeText(this, "Failed to open camera + ", Toast.LENGTH_LONG).show();

e.printStackTrace();

}

//Registered method to handle the result on callback

private  void  handleOpticalCaptureActivityResult(ActivityResult result) {

{

int  resultCode = result.getResultCode();

if (resultCode == Activity.RESULT_OK) {

String  qrData = (String) data.getExtras().get("DATA");

String  qrType = (String) data.getExtras().get("TYPE");

//showToast("QR_BAR_Code Data: " + qrData, Gravity.CENTER);

//Here captured optical codes data can be extracted

finishActivity(REQUEST_CODE_FILE_RETURN);

return;

}

}

```

```kotlin

//Kotlin

// Register the activity results

private val opticalCaptureActivityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

handleOpticalCaptureActivityResult(result)

}

  

// Trigger the activity

try {

val cameraIntent = Intent(this, Class.forName("com.extrieve.quickcapture.sdk.OpticalCodeHelper"))

val photoURI: Uri = Uri.parse(Config.CaptureSupport.OutputPath)

this.grantUriPermission(this.packageName, photoURI, Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION)

if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {

cameraIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)

}

opticalCaptureActivityResultLauncher.launch(cameraIntent)

} catch (e: ClassNotFoundException) {

Toast.makeText(this, "Failed to open camera + ", Toast.LENGTH_LONG).show()

e.printStackTrace()

}

  

// Registered method to handle the result on callback

private fun handleOpticalCaptureActivityResult(result: ActivityResult) {

val resultCode = result.resultCode

val data = result.data

if (resultCode == Activity.RESULT_OK && data != null) {

val qrData = data.extras?.getString("DATA")

val qrType = data.extras?.getString("TYPE")

// showToast("QR_BAR_Code Data: $qrData", Gravity.CENTER)

// Here captured optical codes data can be extracted

finishActivity(REQUEST_CODE_FILE_RETURN)

}

}

```

# Error Handling & Exceptions

- As a part of exceptional error handling **ImgException** class is available.

-  *Following are the possible errors and corresponding codes*:

- CREATE_FILE_ERROR= **-100**;

- IMAGE_ROTATION_ERROR= **-101**;

- LOAD_TO_BUFFER_ERROR= **-102**;

- DELETE_FILE_ERROR= **-103**;

- GET_ROTATION_ERROR= **-104**;

- ROTATE_BITMAP_ERROR= **-105**;

- BITMAP_RESIZE_ERROR= **-106**;

- CAMERA_HELPER_ERROR= **-107**;

- LOG_CREATION_ERROR= **-108**;

- Also with **Config.CaptureSupport.LastLogInfo** last logged exception or error details can be identified.

  

# Notes

### Regarding accuracy :

  

The accuracy of face detection and matching technologies depends on input image quality, including factors such as image distortion, rotation angles, lighting conditions, and color consistency. While offline solutions effectively reduce manual effort and operational costs, they do not guarantee 100% reliability in all scenarios.

  

This system enables on-device verification, efficiently identifying doubtful matches and flagging them for backend verification within the offline environment. By integrating backend validation, the system enhances reliability without relying on external APIs. Additionally, when a match achieves high accuracy as defined in the accuracy thresholds, the system can be considered reliable even without backend verification, making it a valuable solution for offline scenarios where external validation is limited.

  

For use cases demanding exceptionally high accuracy and reliability, an API-based advanced system is recommended.

  

*Document version 4.2.0*

  

**Extrieve** - *Your Expert in Document Management & AI Solutions.*

  

[© 1996 - 2026 Extrieve Technologies](https://www.extrieve.com/)