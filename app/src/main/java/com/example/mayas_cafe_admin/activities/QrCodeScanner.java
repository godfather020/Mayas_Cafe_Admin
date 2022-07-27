package com.example.mayas_cafe_admin.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.mayas_cafe_admin.MainActivity;
import com.example.mayas_cafe_admin.R;
import com.example.mayas_cafe_admin.fragments.CurrentOrdersFrag;
import com.example.mayas_cafe_admin.utils.Constants;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QrCodeScanner extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    MainActivity mainActivity;

    private ZXingScannerView mScannerView;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        // Programmatically initialize the scanner view
        mScannerView = new ZXingScannerView(this);
        // Set the scanner view as the content view
        setContentView(mScannerView);
        mainActivity = new MainActivity();

        Constants.QR_SCAN_ID = "96";
        MainActivity.getInstance().onResume();

        finish();

        //AppCompatActivity activity = (AppCompatActivity) mScannerView.getContext();

        //activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CurrentOrdersFrag()).commit();

    }

    @Override
    public void onResume() {
        super.onResume();
        // Register ourselves as a handler for scan results.
        mScannerView.setResultHandler(this);
        // Start camera on resume
        mScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        // Stop camera on pause
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result rawResult) {
        // Do something with the result here
        // Prints scan results
        Log.d("result", rawResult.getText());
        // Prints the scan format (qrcode, pdf417 etc.)
        Log.d("result", rawResult.getBarcodeFormat().toString());
        //If you would like to resume scanning, call this method below:
        //mScannerView.resumeCameraPreview(this);
        Intent intent = new Intent();
        intent.putExtra("100", rawResult.getText());
        setResult(RESULT_OK, intent);

        MainActivity.getInstance().navigationView.setCheckedItem(R.id.AllOrders);
        /*mainActivity.loadFragment(
                getSupportFragmentManager(),
                new CurrentOrdersFrag(),
                R.id.fragment_container,
                false,
                "QrCodeScanner",
                null
        );*/

        Intent intent1 = new Intent(this, MainActivity.class);
        intent.putExtra("qrScan", rawResult.getText());
        startActivity(intent1);

        finish();
    }
}