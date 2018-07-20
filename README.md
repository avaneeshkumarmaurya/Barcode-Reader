Barcode Reader Using Google Vision Api
===================
Android Barcode Reader library using **Google Mobile Vision.**

How to Use
-------------
1. Include the barcode reader dependency in app's **build.gradle**
```gradle
dependencies {
    // google vision gradle
    implementation 'com.google.android.gms:play-services-vision:15.0.2'
}
```
2. Implement your activity from <code>BarcodeReader.BarcodeReaderListener</code> and override the necessary methods.
```java
public class BarcodeReaderActivity extends AppCompatActivity implements BarcodeReaderFragment.BarcodeReaderListener {
    public static String KEY_CAPTURED_BARCODE = "key_captured_barcode";
    public static String KEY_CAPTURED_RAW_BARCODE = "key_captured_raw_barcode";
    private static final String KEY_AUTO_FOCUS = "key_auto_focus";
    private static final String KEY_USE_FLASH = "key_use_flash";
    private boolean autoFocus = false;
    private boolean useFlash = false;
    private BarcodeReaderFragment mBarcodeReaderFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_reader);

        final Intent intent = getIntent();
        if (intent != null) {
            autoFocus = intent.getBooleanExtra(KEY_AUTO_FOCUS, false);
            useFlash = intent.getBooleanExtra(KEY_USE_FLASH, false);
        }
        mBarcodeReaderFragment = attachBarcodeReaderFragment();
    }

    private BarcodeReaderFragment attachBarcodeReaderFragment() {
        final FragmentManager supportFragmentManager = getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        BarcodeReaderFragment fragment = BarcodeReaderFragment.newInstance(autoFocus, useFlash);
        fragment.setListener(this);
        fragmentTransaction.replace(R.id.fm_container, fragment);
        fragmentTransaction.commitAllowingStateLoss();
        return fragment;
    }

    public static Intent getLaunchIntent(Context context, boolean autoFocus, boolean useFlash) {
        Intent intent = new Intent(context, BarcodeReaderActivity.class);
        intent.putExtra(KEY_AUTO_FOCUS, autoFocus);
        intent.putExtra(KEY_USE_FLASH, useFlash);
        return intent;
    }

    @Override
    public void onScanned(Barcode barcode) {
        if (mBarcodeReaderFragment != null) {
            mBarcodeReaderFragment.pauseScanning();
        }
        if (barcode != null) {
            Intent intent = new Intent();
            intent.putExtra(KEY_CAPTURED_BARCODE, barcode);
            intent.putExtra(KEY_CAPTURED_RAW_BARCODE, barcode.rawValue);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    @Override
    public void onScannedMultiple(List<Barcode> barcodes) {

    }

    @Override
    public void onBitmapScanned(SparseArray<Barcode> sparseArray) {

    }

    @Override
    public void onScanError(String errorMessage) {

    }

    @Override
    public void onCameraPermissionDenied() {

    }
}

```

Adding Barcode Reader in Fragment
----
In fragment the barcode reader can be added easily but the scanner listener <code>barcodeReader.setListener()</code> has to 
be set manually.

Check the example fragment code in <code>BarcodeFragment.java</code> and <code>BarcodeFragmentTestActivity.java</code>

https://github.com/avaneeshkumarmaurya/Barcode-Reader/blob/master/barcode-reader/src/main/java/com/notbytes/barcode_reader/BarcodeReaderFragment.java

Adding Scanner Overlay Scanning Indicator
----
The overlay animation indicator displays a horizontal line animating from top to bottom. This will be useful to  to show some cool animation to indicate scanning progress.

To use it, add the <code>com.notbytes.barcode_reader.ScannerOverlay</code> on top of barcode reader fragment using Relative or Frame layout.
```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout ...>

    <fragment
        android:id="@+id/barcode_fragment"
        android:name="com.notbytes.barcode_reader.BarcodeReaderFragment"
        android:layout_width="match_parent"
        app:auto_focus="true"
        app:use_flash="false"
        android:layout_height="match_parent" />

    <com.notbytes.barcode_reader.ScannerOverlay
        android:id="@+id/scan_overlay"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:background="#44000000"
        app:line_color="#7323DC"
        app:line_speed="6"
        app:line_width="5"
        app:square_height="250"
        app:square_width="250" />

</RelativeLayout>

```


Additional Options
-------------
XML attribute for **Barcode Reader**

<code>auto_focus</code> - boolean, turn on/off auto focus. Default is <code>true</code>

<code>use_flash</code> - boolean, turn on/off flash. Default is <code>false</code>


XML attribute for **Scanner Overlay** Indicator

<code>square_width</code> - Width of transparent square

<code>square_height</code> - Height of transparent square

<code>line_color</code> - Horizontal line color

<code>line_speed</code> - Horizontal line animation speed

----

JAVA Methods

- **Play beep sound**

You can play the **beep** sound when the barcode is scanned. This code is usually called in <code>onScanned()</code> callback.
```java
@Override
    public void onScanned(final Barcode barcode) {
        Log.e(TAG, "onScanned: " + barcode.displayValue);
        barcodeReader.playBeep();
        });
    }
```

- **Change beep sound**

You can change the default **beep** sound by passing the file name. You beep file should be in project's **assets** folder.
```java
barcodeReader.setBeepSoundFile("shutter.mp3");
```

- **Pause scanning**

The scanning can be paused by calling <code>pauseScanning()</code> method.
```java
barcodeReader.pauseScanning();
```

- **Resume Scanning**

The scanning can be resumed by calling <code>resumeScanning()</code> method.
```java
barcodeReader.resumeScanning();
```
