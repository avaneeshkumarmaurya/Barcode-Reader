package com.notbytes.barcodereader;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.vision.barcode.Barcode;
import com.notbytes.barcode_reader.BarcodeReaderActivity;
import com.notbytes.barcode_reader.BarcodeReaderFragment;

public class MainActivity extends AppCompatActivity implements BarcodeReaderFragment.BarcodeCaptureListener, View.OnClickListener {
    private boolean isUseFlash = false;
    private BarcodeReaderFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_flash).setOnClickListener(this);
        final FragmentManager supportFragmentManager = getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragment = BarcodeReaderFragment.newInstance(true, false);
        fragment.setBarcodeCaptureListener(this);
        fragmentTransaction.replace(R.id.fm_container, fragment);
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    public void onBarcodeCaptured(Barcode barcode) {
        System.out.println(barcode);
    }

    @Override
    public void onClick(View v) {
        isUseFlash = !isUseFlash;
        Intent intent = new Intent(this, BarcodeReaderActivity.class);
        startActivityForResult(intent, BarcodeReaderActivity.BARCODE_READER_ACTIVITY_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Barcode barcode = data.getParcelableExtra(BarcodeReaderActivity.KEY_CAPTURED_BARCODE);
        System.out.println(barcode.rawValue);
    }
}
