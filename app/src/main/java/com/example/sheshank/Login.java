package com.example.sheshank;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sheshank.prefer.AppPreferences;
import com.example.sheshank.prefer.AppPrefernceConnstants;

public class Login extends AppCompatActivity {

    private TextView mUserTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        initViews();

    }

    private void initViews() {
        mUserTextView.setText("Hello " + new AppPreferences().getStringValue(getApplicationContext(), AppPrefernceConnstants.KEY_USER_NAME) + ", Welcome!!");
    }

    private void initUI() {mUserTextView = findViewById(R.id.user_name);

    }
}