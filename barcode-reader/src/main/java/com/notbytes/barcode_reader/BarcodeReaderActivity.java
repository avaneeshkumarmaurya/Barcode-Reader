package com.notbytes.barcode_reader;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.vision.barcode.Barcode;

public class BarcodeReaderActivity extends AppCompatActivity implements BarcodeReaderFragment.BarcodeCaptureListener {
    public static int BARCODE_READER_ACTIVITY_REQUEST = 1020;
    public static String KEY_CAPTURED_BARCODE = "key_captured_barcode";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_reader);
        final FragmentManager supportFragmentManager = getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        BarcodeReaderFragment fragment = BarcodeReaderFragment.newInstance(true, false);
        fragment.setBarcodeCaptureListener(this);
        fragmentTransaction.replace(R.id.fm_container, fragment);
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    public void onBarcodeCaptured(Barcode barcode) {
        if (barcode != null) {
            Intent intent = new Intent();
            intent.putExtra(KEY_CAPTURED_BARCODE, barcode);
            setResult(RESULT_OK,intent);
            finish();
        }
    }
}
