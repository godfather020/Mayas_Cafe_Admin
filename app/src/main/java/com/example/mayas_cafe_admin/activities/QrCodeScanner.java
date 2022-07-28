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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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
        String[] rs = rawResult.toString().replace("\n","").split(" ");

        for(int i = 0; i < rs.length; i++) {

            Log.d("result", rs[i]);
        }
        Log.d("result", rawResult.getBarcodeFormat().toString());
        //If you would like to resume scanning, call this method below:
        //mScannerView.resumeCameraPreview(this);
        Intent intent = new Intent();
        intent.putExtra("100", rawResult.getText());
        setResult(RESULT_OK, intent);

        MainActivity.getInstance().navigationView.setCheckedItem(R.id.AllOrders);

        Constants.QR_SCAN_ID = rs[0];
        String orderPickUp = rs[1]+" "+rs[2]+" "+rs[3];

        Log.d("result", orderPickUp);

        SimpleDateFormat sm = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());

        Date date = null;
        try {
            date = sm.parse(orderPickUp);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String newSm = "";
        if (date != null) {
            newSm = new SimpleDateFormat("dd-MM-yy", Locale.getDefault()).format(date);
        }

        Log.d("result", newSm);

        Constants.orderPickUp = newSm+" "+rs[4]+" "+rs[5].toLowerCase();
        MainActivity.getInstance().onResume();

        finish();
    }
}