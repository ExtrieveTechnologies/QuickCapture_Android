Mobile-Document-Scanning-SDK-ANDROID
=====
QuickCapture Mobile Scanning SDK Specially designed for native ANDROID from Extrieve
![](static/glide_logo.png)

Download
--------
<!--For detailed instructions and requirements, see Glide's [download and setup docs page][28].-->

You can download a .aar library file from GitHub's [releases page](https://github.com/ExtrieveTechnologies/QuickCapture_Android/releases/tag/v2.1.2).

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

<!--For info on using the bleeding edge, see the [Snapshots][17] docs page.-->

<!--R8 / Proguard
--------
The specific rules are [already bundled](library/proguard-rules.txt) into the aar which can be interpreted by R8 automatically

How do I use Glide?
-------------------
Check out the [documentation][20] for pages on a variety of topics, and see the [javadocs][3].

For Glide v3, see the [wiki][2].

Simple use cases will look something like this:

```java
// For a simple view:
@Override public void onCreate(Bundle savedInstanceState) {
  ...
  ImageView imageView = (ImageView) findViewById(R.id.my_image_view);

  Glide.with(this).load("https://goo.gl/gEgYUd").into(imageView);
}

// For a simple image list:
@Override public View getView(int position, View recycled, ViewGroup container) {
  final ImageView myImageView;
  if (recycled == null) {
    myImageView = (ImageView) inflater.inflate(R.layout.my_image_view, container, false);
  } else {
    myImageView = (ImageView) recycled;
  }

  String url = myUrls.get(position);

  Glide
    .with(myFragment)
    .load(url)
    .centerCrop()
    .placeholder(R.drawable.loading_spinner)
    .into(myImageView);

  return myImageView;
}
```

Status
------
Version 4 is now released and stable. Updates are released periodically with new features and bug fixes.

Comments/bugs/questions/pull requests are always welcome! Please read [CONTRIBUTING.md][5] on how to report issues.
-->
Compatibility
-------------
 * **JAVA 8 Support**: QuickCapture v2 requires JAVA version 8 support for the application.
 * **Minimum Android SDK**: QuickCapture v2 requires a minimum API level of 21.
 * **Compile Android SDK**: QuickCapture v2 requires you to compile against API 33 or later.

 <!--If you need to support older versions of Android, consider staying on [Glide v3][14], which works on API 10, but is not actively maintained.

 * **OkHttp 3.x**: There is an optional dependency available called `okhttp3-integration`, see the [docs page][23].
 * **Volley**: There is an optional dependency available called `volley-integration`, see the [docs page][24].
 * **Round Pictures**: `CircleImageView`/`CircularImageView`/`RoundedImageView` are known to have [issues][18] with `TransitionDrawable` (`.crossFade()` with `.thumbnail()` or `.placeholder()`) and animated GIFs, use a [`BitmapTransformation`][19] (`.circleCrop()` will be available in v4) or `.dontAnimate()` to fix the issue.
 * **Huge Images** (maps, comic strips): Glide can load huge images by downsampling them, but does not support zooming and panning `ImageView`s as they require special resource optimizations (such as tiling) to work without `OutOfMemoryError`s.

Build
-----
Building Glide with gradle is fairly straight forward:

```shell
git clone https://github.com/bumptech/glide.git
cd glide
./gradlew jar
```

**Note**: Make sure your *Android SDK* has the *Android Support Repository* installed, and that your `$ANDROID_HOME` environment
variable is pointing at the SDK or add a `local.properties` file in the root project with a `sdk.dir=...` line.


Development
-----------
Follow the steps in the [Build](#build) section to setup the project and then edit the files however you wish.
[Android Studio][26] cleanly imports both Glide's source and tests and is the recommended way to work with Glide.

To open the project in Android Studio:

1. Go to *File* menu or the *Welcome Screen*
2. Click on *Open...*
3. Navigate to Glide's root directory.
4. Select `setting.gradle`

For more details, see the [Contributing docs page][27].

Getting Help
------------
To report a specific problem or feature request, [open a new issue on Github][5]. For questions, suggestions, or
anything else, email [Glide's discussion group][6], or join our IRC channel: [irc.freenode.net#glide-library][13].

Contributing
------------
Before submitting pull requests, contributors must sign Google's [individual contributor license agreement][7].

Thanks
------
* The **Android team** and **Jake Wharton** for the [disk cache implementation][8] Glide's disk cache is based on.
* **Dave Smith** for the [GIF decoder gist][9] Glide's GIF decoder is based on.
* **Chris Banes** for his [gradle-mvn-push][10] script.
* **Corey Hall** for Glide's [amazing logo][11].
* Everyone who has contributed code and reported issues!
-->
<!--Author
------
Sam Judd - @sjudd on GitHub, @samajudd on Twitter

License
-------
BSD, part MIT and Apache 2.0. See the [LICENSE][16] file for details.
-->
<!--Disclaimer
---------
This is not an official Google product.

[1]: https://github.com/bumptech/glide/releases

 -->
 
 **API  and  integration  Details**
Mainly the SDK will expose two  classes  and  two  supporting  classes :


 1. **CameraHelper**  -  Handle the  camera  related  operations. Basically,  an  activity. 
 2. **ImgHelper**  - Purpose  of  this  class  is  to  handle  all  imaging  related operations
 3. **CameraSupport**  -  Holds  various configurations  for  camera. 
 4. **ImgException**  -  Handle  all exceptions  on  Image  related  operations  on ImgHelper.

Based on the requirement any one or all classes can be used. And need to import those from the  SDK.

    import  com.extrieve.quickcapture.sdk.ImgHelper;  
    import  com.extrieve.quickcapture.sdk.CameraHelper;
    import  com.extrieve.quickcapture.sdk.CameraSupport;  
    import  com.extrieve.quickcapture.sdk.ImgException;
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
	CameraSupport.CamConfigClass.OutputPath = “pass output path as 	string”;
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


