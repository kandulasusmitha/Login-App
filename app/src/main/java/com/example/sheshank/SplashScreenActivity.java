package com.example.sheshank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.sheshank.prefer.AppPreferences;
import com.example.sheshank.prefer.AppPrefernceConnstants;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        if (new AppPreferences().getBoolValue(getApplicationContext(), AppPrefernceConnstants.KEY_USER_LOGGED_IN)) {
            startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
            finish();
        } else {
            startActivity(new Intent(SplashScreenActivity.this, Signin.class));
            finish();
        }
    }
    }
