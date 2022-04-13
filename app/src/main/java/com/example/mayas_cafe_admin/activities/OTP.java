package com.example.mayas_cafe_admin.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mayas_cafe_admin.R;
import com.example.mayas_cafe_admin.utils.Constants;
import com.example.mayas_cafe_admin.utils.Functions;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class OTP extends AppCompatActivity {

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

        Functions.otpTextChange(otp1, otp2);
        Functions.otpTextChange(otp2, otp3);
        Functions.otpTextChange(otp3, otp4);
        Functions.otpTextChange(otp4, otp4);

        countdownTimer();

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