Mobile-Document-Scanning-SDK-ANDROID
=====
QuickCapture Mobile Scanning SDK Specially designed for native ANDROID from Extrieve
![](static/glide_logo.png)

Download
--------
<!--For detailed instructions and requirements, see Glide's [download and setup docs page][28].-->

You can download a **.aar** library file from GitHub's [releases page](https://github.com/ExtrieveTechnologies/QuickCapture_Android/releases/tag/v2.1.2).

Or use Gradle:

```gradle
repositories {
  google()
  mavenCentral()
  maven {url 'https://expack.extrieve.in/maven/'}
}

dependencies {
  implementation 'com.extrieve.quickcapture:QCv2:2.1.2'
}
```

Or Maven:

```xml
<dependency>
  <groupId>com.extrieve.quickcapture</groupId>
  <artifactId>QCv2</artifactId>
  <version>2.1.2</version>
</dependency>
```

Compatibility
-------------
 * **JAVA 8 Support**: QuickCapture v2 requires JAVA version 8 support for the application.
 * **Minimum Android SDK**: QuickCapture v2 requires a minimum API level of 21.
 * **Compile Android SDK**: QuickCapture v2 requires you to compile against API 33 or later.
 

##  API  and  integration  Details

Mainly the SDK will expose two  classes  and  two  supporting  classes :


 1. **CameraHelper**  -  *Handle the  camera  related  operations. Basically,  an  activity.* 
 2. **ImgHelper**  - *Purpose  of  this  class  is  to  handle  all  imaging  related operations.*
 3. **CameraSupport**  -  *Holds  various configurations  for  camera.* 
 4. **ImgException**  -  *Handle  all exceptions  on  Image  related  operations  on ImgHelper.*
 

Based on the requirement any one or all classes can be used. And need to import those from the  SDK.
```java
    import  com.extrieve.quickcapture.sdk.ImgHelper;  
    import  com.extrieve.quickcapture.sdk.CameraHelper;
    import  com.extrieve.quickcapture.sdk.CameraSupport;  
    import  com.extrieve.quickcapture.sdk.ImgException;
   ```
---
# CameraHelper
This  class  will  be  implemented  as  an  activity.  This  class  can  be  initialized  as  intent.

```java
Intent CameraIntent = new Intent(this,Class.forName("com.extrieve.quickcapture.sdk.CameraHelper"));  UriphotoURI = Uri.parse(CameraSupport.CamConfigClass.OutputPath);
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
Camera Helper having a supporting class with static configuration  
CameraSupport.CamConfigClass.CamConfigClass  :  contains  various  configurations  as  follows:

- **OutputPath** - To set the output directory in which the captured images will be saved.  Base  app should  have  rights  to write  to the  provided  path.
	```java
	CameraSupport.CamConfigClass.OutputPath = “pass output path as string”;
	```
- **MaxPage** - To set the number of captures to do on each camera session. And this can also  control  whether  the  capture  mode  is  single  or  multi  i.e
	> if  MaxPage  <= 0 /  not  set:  means  unlimited.If  MaxPage  >= 1:
	> means  limited.
	```java
	// MaxPage <= 0  : Unlimited Capture Mode  
	// MaxPage = 1   : Limited Single Capture  
	// MaxPage > 1   : Limited Multi Capture Mode  
	public static int MaxPage = 0;
	```
- **ColorMode**  -  To  Set  the  capture  color  mode-  supporting  color  and  grayscale.

[© 1996 - 2023 Extrieve Technologies](https://www.extrieve.com/)


