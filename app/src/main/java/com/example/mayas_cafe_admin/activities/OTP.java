package com.example.mayas_cafe_admin.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mayas_cafe_admin.FirebaseCloudMsg;
import com.example.mayas_cafe_admin.MainActivity;
import com.example.mayas_cafe_admin.R;
import com.example.mayas_cafe_admin.activities.ViewModel.Login_ViewModel;
import com.example.mayas_cafe_admin.activities.ViewModel.OTP_ViewModel;
import com.example.mayas_cafe_admin.utils.Constants;
import com.example.mayas_cafe_admin.utils.Functions;
import com.example.mayasfood.Retrofite.response.Response_Common;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class OTP extends AppCompatActivity {

    private boolean otpData = false;
    private String otp_1, otp_2, otp_3, otp_4;
    EditText otp1, otp2, otp3, otp4;
    TextView timer, resend_txt;
    ImageButton img_back_otp;
    Button submit, resend;
    ProgressBar loading_otp;
    ViewModelProvider viewModelProvider;
    OTP_ViewModel otp_viewModel;
    Login_ViewModel login_viewModel;
    String userPhone = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        viewModelProvider = new ViewModelProvider(this);
        otp_viewModel = viewModelProvider.get(OTP_ViewModel.class);
        login_viewModel = viewModelProvider.get(Login_ViewModel.class);

        userPhone = getSharedPreferences(Constants.sharedPrefrencesConstant.USER_P, MODE_PRIVATE).getString(Constants.sharedPrefrencesConstant.USER_P, "");

        otp1 = findViewById(R.id.otp1);
        otp2 = findViewById(R.id.otp2);
        otp3 = findViewById(R.id.otp3);
        otp4 = findViewById(R.id.otp4);
        resend_txt = findViewById(R.id.resend_txt);
        timer = findViewById(R.id.timer);
        img_back_otp = findViewById(R.id.img_back_otp);
        submit = findViewById(R.id.submit);
        resend = findViewById(R.id.resend);
        loading_otp = findViewById(R.id.loading_otp);

        Functions.otpTextChange(otp1, otp2, otp1);
        Functions.otpTextChange(otp2, otp3, otp1);
        Functions.otpTextChange(otp3, otp4, otp2);
        Functions.otpTextChange(otp4, otp4, otp3);

        countdownTimer();

        otp4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (otp4.getText().toString().length() > 0) {
                    InputMethodManager imm =
                            (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(otp4.getWindowToken(), 0);
                    OTP.this.submit.callOnClick();
                }
            }
        });

        String otp = getSharedPreferences(Constants.sharedPrefrencesConstant.OTP, MODE_PRIVATE).getString(Constants.sharedPrefrencesConstant.OTP, "");

        if (!otp.isEmpty()){

            setOtp(otp);

            OTP.this.submit.callOnClick();
        }


        img_back_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OTP.this, Login.class));
            }
        });

        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resend.setVisibility(View.GONE);
                submit.setVisibility(View.VISIBLE);
                resend_txt.setVisibility(View.VISIBLE);
                timer.setVisibility(View.VISIBLE);
                countdownTimer();

                loading_otp.setVisibility(View.VISIBLE);

                login_viewModel.get_otp(OTP.this, userPhone).observe(OTP.this, new Observer<Response_Common>() {
                    @Override
                    public void onChanged(Response_Common response_common) {

                        if (response_common != null){

                            if (Boolean.TRUE.equals(response_common.getSuccess())){

                                loading_otp.setVisibility(View.GONE);

                                setOtp(response_common.getData().getOtp());

                            }
                            else {

                                loading_otp.setVisibility(View.GONE);
                            }
                        }
                        else {

                            loading_otp.setVisibility(View.GONE);
                        }

                    }
                });
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loading_otp.setVisibility(View.VISIBLE);

                otp_1 = otp1.getText().toString();
                otp_2 = otp2.getText().toString();
                otp_3 = otp3.getText().toString();
                otp_4 = otp4.getText().toString();

                otpData = Functions.checkOtp(otp_1, otp_2, otp_3, otp_4, otp1, otp2, otp3, otp4);

                if (otpData) {

                    String deviceName = Build.BRAND + " " + Build.MODEL;
                    String otp = otp_1 + otp_2 + otp_3 + otp_4;

                    if (!userPhone.isEmpty()) {

                        otp_viewModel.verify_otp(OTP.this, userPhone, otp, Settings.Secure.ANDROID_ID, deviceName, Build.VERSION.RELEASE).observe(OTP.this, new Observer<Response_Common>() {
                            @Override
                            public void onChanged(Response_Common response_common) {

                                if (response_common != null){

                                    if (Boolean.TRUE.equals(response_common.getSuccess())){

                                        String userName = "";
                                        String userPic = "";
                                        String deviceToken = "";

                                        if (response_common.getData().getResult().getUserName() != null) {

                                            userName = response_common.getData().getResult().getUserName();
                                            userPic = response_common.getData().getResult().getProfilePic();
                                            deviceToken = response_common.getData().getToken();
                                        }

                                        if (!userName.isEmpty() && !userPic.isEmpty() && !deviceToken.isEmpty()) {

                                            loading_otp.setVisibility(View.GONE);

                                            getSharedPreferences(Constants.sharedPrefrencesConstant.USER_N, MODE_PRIVATE).edit().putString(Constants.sharedPrefrencesConstant.USER_N, userName).apply();
                                            getSharedPreferences(Constants.sharedPrefrencesConstant.USER_I, MODE_PRIVATE).edit().putString(Constants.sharedPrefrencesConstant.USER_I, userPic).apply();
                                            getSharedPreferences(Constants.sharedPrefrencesConstant.DEVICE_TOKEN, MODE_PRIVATE).edit().putString(Constants.sharedPrefrencesConstant.DEVICE_TOKEN, deviceToken).apply();
                                            getSharedPreferences(Constants.sharedPrefrencesConstant.LOGIN, MODE_PRIVATE).edit().putBoolean(Constants.sharedPrefrencesConstant.LOGIN, true).apply();

                                            Toast.makeText(getApplicationContext(), "Login Successfull", Toast.LENGTH_SHORT).show();

                                            startActivity(new Intent(OTP.this, MainActivity.class));
                                            finish();

                                        }
                                    }
                                }
                            }
                        });
                    }


                } else {
                    Toast.makeText(OTP.this, "Check information", Toast.LENGTH_SHORT).show();
                }

            }
        });

        new FirebaseCloudMsg().setEditTextOtp(otp1,otp2,otp3,otp4);
    }

    private void countdownTimer(){

        //60 Second CountDown
        new CountDownTimer(Constants.duration * 1000, 1000) {

            @Override
            public void onTick(long l) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String sDuration = String.format(Locale.getDefault(), "%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(l), TimeUnit.MILLISECONDS.toSeconds(l) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(l)));

                        timer.setText(sDuration);
                    }
                });

            }

            @Override
            public void onFinish() {
                resend.setVisibility(View.VISIBLE);
                submit.setVisibility(View.GONE);
                resend_txt.setVisibility(View.GONE);
                timer.setVisibility(View.GONE);
            }
        }.start();

    }

    private void setOtp(String otp){

        otp1.setText(otp.substring(0,1));
        otp2.setText(otp.substring(1,2));
        otp3.setText(otp.substring(2,3));
        otp4.setText(otp.substring(3,4));

    }
}