package com.example.mayas_cafe_admin.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mayas_cafe_admin.MainActivity;
import com.example.mayas_cafe_admin.R;
import com.example.mayas_cafe_admin.activities.ViewModel.Login_ViewModel;
import com.example.mayas_cafe_admin.utils.Constants;
import com.example.mayas_cafe_admin.utils.Functions;
import com.example.mayasfood.Retrofite.response.Response_Common;
import com.hbb20.CountryCodePicker;

public class Login extends AppCompatActivity {

    private boolean isBackPressed = false;
    private boolean phoneCheck = false;
    private String phoneNumber;
    EditText phoneNum;
    Button signIn;
    ImageButton back_img;
    ViewModelProvider viewModelProvider;
    Login_ViewModel login_viewModel;
    CountryCodePicker cc;
    TextView skip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signIn = findViewById(R.id.sign_in);
        phoneNum = findViewById(R.id.phoneNum);
        back_img = findViewById(R.id.back_img);
        cc = findViewById(R.id.cc);
        skip = findViewById(R.id.skip);

        viewModelProvider = new ViewModelProvider(this);
        login_viewModel = viewModelProvider.get(Login_ViewModel.class);

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

                phoneNumber = phoneNum.getText().toString();
                Log.d("phone", phoneNumber);
                phoneCheck = Functions.checkData(phoneNumber, phoneNum);

                if (phoneCheck) {

                    login_viewModel.get_otp(Login.this, phoneNumber).observe(Login.this, new Observer<Response_Common>() {
                        @Override
                        public void onChanged(Response_Common response_common) {

                            if (response_common != null){

                                if (response_common.getData().getOtp() != null){

                                    getSharedPreferences(Constants.sharedPrefrencesConstant.USER_P, MODE_PRIVATE).edit().putString(Constants.sharedPrefrencesConstant.USER_P, phoneNumber).apply();
                                    getSharedPreferences(Constants.sharedPrefrencesConstant.OTP, MODE_PRIVATE).edit().putString(Constants.sharedPrefrencesConstant.OTP, response_common.getData().getOtp()).apply();

                                    Log.d("OTP", response_common.getData().getOtp());
                                    startActivity(new Intent(Login.this, OTP.class));
                                    finish();
                                }
                            }
                        }
                    });

                }
                else{
                    Toast.makeText(Login.this, "Check Information", Toast.LENGTH_SHORT).show();
                }

            }
        });

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });

        phoneNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (phoneNum.getText().toString().length() == 10) {
                    InputMethodManager imm =
                            (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(phoneNum.getWindowToken(), 0);
                }
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