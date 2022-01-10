package com.example.sheshank;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sheshank.prefer.AppPreferences;
import com.example.sheshank.prefer.AppPrefernceConnstants;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import org.json.JSONObject;

import ApiPackage.RetrofitInteractor;

import request.login.LoginRequest;
import response.login.LoginResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.Utils;

public class Signin extends AppCompatActivity {

    private EditText mUserNameEditText;
    private EditText mPasswordEditText;
    private Button mLoginButton;
    private String mUserName, mPassword;
    private ProgressBar mLoadingProgressBar;
    private AppPreferences mAppPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        initUI();
        initViews();

    }

    private void initUI() {

        mUserNameEditText = findViewById(R.id.username);
        mPasswordEditText = findViewById(R.id.password);
        mLoginButton = findViewById(R.id.login);
        mLoadingProgressBar = findViewById(R.id.loading);
    }

    private void initViews() {

        mAppPreference = new AppPreferences();
        mUserNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mUserName = charSequence.toString();
                validatePasswordField(mUserName, mPassword);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mPasswordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mPassword = charSequence.toString();
                validatePasswordField(mUserName, mPassword);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utils.isOnlineMessage(Signin.this)) {
                    mLoadingProgressBar.setVisibility(View.VISIBLE);
                    LoginRequest loginRequest = new LoginRequest();
                    loginRequest.setEmail(mUserName);
                    loginRequest.setPassword(mPassword);
                    Gson gson = new Gson();
                    JsonElement jsonElement = gson.toJsonTree(loginRequest);
                    Call<LoginResponse> loginAPICall = new RetrofitInteractor().getAPIClass(Signin.this).login(jsonElement);
                    loginAPICall.enqueue(new Callback<LoginResponse>() {

                        @Override
                        public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                            if (response.body() != null && response.isSuccessful()) {
                                if (response.body().getStatus() == 1) {
                                    Utils.showToast(Signin.this, response.body().getMessage());
                                    mAppPreference.setBoolValue(getApplicationContext(), AppPrefernceConnstants.KEY_USER_LOGGED_IN,true);
                                    mAppPreference.setStringValue(getApplicationContext(),AppPrefernceConnstants.KEY_USER_ID,response.body().getData().getId());
                                    mAppPreference.setStringValue(getApplicationContext(),AppPrefernceConnstants.KEY_USER_NAME,response.body().getData().getName());
                                    mAppPreference.setStringValue(getApplicationContext(),AppPrefernceConnstants.KEY_USER_EMAIL,response.body().getData().getEmail());
                                    mAppPreference.setStringValue(getApplicationContext(),AppPrefernceConnstants.KEY_USER_MOBILE,response.body().getData().getPhone());
                                    mAppPreference.setStringValue(getApplicationContext(),AppPrefernceConnstants.KEY_ROLE_NAME,response.body().getData().getRole());
                                    mAppPreference.setStringValue(getApplicationContext(),AppPrefernceConnstants.KEY_USER_LOGO,response.body().getData().getLogo());
                                    mAppPreference.setStringValue(getApplicationContext(),AppPrefernceConnstants.KEY_USER_CUSTOMER,response.body().getData().getCustomer());
                                    mAppPreference.setStringValue(getApplicationContext(), AppPrefernceConnstants.KEY_TOKEN,response.body().getData().getToken());
                                    mLoadingProgressBar.setVisibility(View.GONE);
                                    Intent intent = new Intent(Signin.this, Login.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    finish();

                                } else if (response.body().getStatus() == 0) {
                                    Utils.showToast(Signin.this, response.body().getMessage());
                                    mLoadingProgressBar.setVisibility(View.GONE);
                                }
                            } else if (response.errorBody() != null) {
                                try {
                                    String errorBody = response.errorBody().string();
                                    JSONObject jsonObject = new JSONObject(errorBody);
                                    if(jsonObject.has("status")&&jsonObject.getInt("status")==0){
                                        Utils.showToast(Signin.this, jsonObject.getString("message"));
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    Utils.showToast(Signin.this, getString(R.string.login_failure));
                                }
                                mLoadingProgressBar.setVisibility(View.GONE);
                            } else {
                                mLoadingProgressBar.setVisibility(View.GONE);
                                Utils.showToast(Signin.this, getString(R.string.login_failure));
                            }

                        }

                        @Override
                        public void onFailure(Call<LoginResponse> call, Throwable t) {
                            mLoadingProgressBar.setVisibility(View.GONE);
                            Utils.showToast(Signin.this, getString(R.string.login_failure));
                        }
                    });

                }

            }
        });
    }

    private void validatePasswordField(String username, String password) {
        if (username == null || username.length() == 0 || username.trim() == null || username.trim().length() == 0) {
            mLoginButton.setEnabled(false);
//            Utils.showToast(Signin.this, getString(R.string.please_enter_email));
        } else if (!Utils.isValidEmail(username)) {
            mLoginButton.setEnabled(false);
//            Utils.showToast(Signin.this, getString(R.string.invalid_email));
        } else if (password == null || password.length() == 0 || password.trim() == null || password.trim().length() == 0) {
            mLoginButton.setEnabled(false);
//            Utils.showToast(Signin.this, getString(R.string.please_enter_password));
        } else if (password.trim().length() < 3) {
            mLoginButton.setEnabled(false);
//            Utils.showToast(Signin.this, getString(R.string.error_for_password));
        } else {
            mLoginButton.setEnabled(true);
        }

    }

}
