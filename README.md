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
[© 1996 - 2023 Extrieve Technologies](https://www.extrieve.com/)


