package com.example.mayas_cafe_admin.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.mayas_cafe_admin.R;

import java.util.ArrayList;

public class GetStart extends AppCompatActivity {

    private boolean isBackPressed = false;
    ArrayList<String> permission = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_start);

        Button getStart = findViewById(R.id.get_start);

        if (check_Permission()) {
            Log.d("permission", "granted");
        } else {
            requestPermission();
        }

        getStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GetStart.this, Login.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {

        if(isBackPressed){
            super.onBackPressed();
            return;
        }

        Toast.makeText(GetStart.this, "Press again to exit", Toast.LENGTH_SHORT).show();
        isBackPressed = true;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isBackPressed = false;
            }
        },2000);
    }

    boolean check_Permission() {
        int camera = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        int internet = ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET);
        int gallery =
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int gallery1 =
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int contact = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);
        int phone = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
        int recieveSms = ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS);
        if (camera != PackageManager.PERMISSION_GRANTED) {
            permission.add(Manifest.permission.CAMERA);
        }
        if (internet != PackageManager.PERMISSION_GRANTED) {
            permission.add(Manifest.permission.INTERNET);
        }
        if (gallery != PackageManager.PERMISSION_GRANTED) {
            permission.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (gallery1 != PackageManager.PERMISSION_GRANTED) {
            permission.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (contact != PackageManager.PERMISSION_GRANTED) {
            permission.add(Manifest.permission.READ_CONTACTS);
        }
        if (phone != PackageManager.PERMISSION_GRANTED) {
            permission.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (recieveSms != PackageManager.PERMISSION_GRANTED){
            permission.add(Manifest.permission.RECEIVE_SMS);
        }
        return permission.isEmpty();



    }

    void requestPermission() {
        ActivityCompat.requestPermissions(
                this,
                permission.toArray(new String[0]),
                Companion.REQUEST_ID_MULTIPLE_PERMISSIONS

        );
    }

    private static class Companion {

        static int REQUEST_ID_MULTIPLE_PERMISSIONS = 3;
    }
}