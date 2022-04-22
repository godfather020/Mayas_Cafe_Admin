package com.example.mayas_cafe_admin.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mayas_cafe_admin.FirebaseCloudMsg;
import com.example.mayas_cafe_admin.R;
import com.example.mayas_cafe_admin.utils.Constants;
import com.example.mayas_cafe_admin.utils.Functions;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class OTP extends Activity {

    private boolean otpData = false;
    private String otp_1, otp_2, otp_3, otp_4;
    EditText otp1, otp2, otp3, otp4;
    TextView timer, resend_txt;
    ImageButton img_back_otp;
    Button submit, resend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        otp1 = findViewById(R.id.otp1);
        otp2 = findViewById(R.id.otp2);
        otp3 = findViewById(R.id.otp3);
        otp4 = findViewById(R.id.otp4);
        resend_txt = findViewById(R.id.resend_txt);
        timer = findViewById(R.id.timer);
        img_back_otp = findViewById(R.id.img_back_otp);
        submit = findViewById(R.id.submit);
        resend = findViewById(R.id.resend);

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
                }
            }
        });

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
                resend_txt.setVisibility(View.VISIBLE);
                timer.setVisibility(View.VISIBLE);
                countdownTimer();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                otp_1 = otp1.getText().toString();
                otp_2 = otp2.getText().toString();
                otp_3 = otp3.getText().toString();
                otp_4 = otp4.getText().toString();

                otpData = Functions.checkOtp(otp_1, otp_2, otp_3, otp_4, otp1, otp2, otp3, otp4);

                if (otpData) {
                    startActivity(new Intent(OTP.this, Dashboard.class));
                    finish();
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
                resend_txt.setVisibility(View.GONE);
                timer.setVisibility(View.GONE);
            }
        }.start();

    }
}