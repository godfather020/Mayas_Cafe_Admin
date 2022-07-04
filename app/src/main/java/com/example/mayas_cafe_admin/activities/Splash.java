package com.example.mayas_cafe_admin.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.mayas_cafe_admin.MainActivity;
import com.example.mayas_cafe_admin.R;
import com.example.mayas_cafe_admin.utils.Constants;

public class Splash extends AppCompatActivity {

    private Handler mHandler = new Handler();
    private Launcher mLauncher = new Launcher();
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        launch();
    }

    public void launch(){
        mHandler.postDelayed(mLauncher, Constants.SPLASH_DELAY);
    }

    private class Launcher implements Runnable {
        @Override
        public void run() {
            
            Boolean isLogin = getSharedPreferences(Constants.sharedPrefrencesConstant.LOGIN, MODE_PRIVATE).getBoolean(Constants.sharedPrefrencesConstant.LOGIN, false);
            
            if (isLogin){

                intent = new Intent(Splash.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
            else {

                intent = new Intent(Splash.this, GetStart.class);
                startActivity(intent);
                finish();
            }
        }
    }
}