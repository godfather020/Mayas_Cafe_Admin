package com.example.mayas_cafe_admin.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mayas_cafe_admin.R;
import com.example.mayas_cafe_admin.utils.Constants;
import com.example.mayas_cafe_admin.utils.Functions;
import com.hbb20.CountryCodePicker;

public class Login extends AppCompatActivity {

    private boolean isBackPressed = false;
    private boolean phoneCheck = false;
    private String phoneNumber;
    EditText phoneNum;
    TextView signUp;
    Button signIn;
    ImageButton back_img;
    CountryCodePicker cc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signIn = findViewById(R.id.sign_in);
        phoneNum = findViewById(R.id.phoneNum);
        back_img = findViewById(R.id.back_img);
        signUp = findViewById(R.id.sign_up);
        cc = findViewById(R.id.cc);

        cc.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                Constants.cc = cc.getSelectedCountryCode();
            }
        });

        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Login.this, GetStart.class));
                finish();


            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                phoneNumber = Constants.cc + phoneNum.getText().toString();
                Log.d("phone", phoneNumber);
                phoneCheck = Functions.checkData(phoneNumber, phoneNum);

                if (phoneCheck) {

                    startActivity(new Intent(Login.this, OTP.class));
                    finish();
                }
                else{
                    Toast.makeText(Login.this, "Check Information", Toast.LENGTH_SHORT).show();
                }

            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Login.this, Register.class));
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

        Toast.makeText(Login.this, "Press again to exit", Toast.LENGTH_SHORT).show();
        isBackPressed = true;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isBackPressed = false;
            }
        },2000);
    }
}