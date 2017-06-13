package com.cidaassdk;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;


public class MainActivity extends AppCompatActivity {


    private CallbackManager callbackManager;
    private LoginButton mButtonLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mButtonLogin = (LoginButton) findViewById(R.id.login_button);
        if (getIntent() != null && getIntent().getStringExtra("facebook_app_id") != null) {
            String facebook_app_id = getIntent().getStringExtra("facebook_app_id");

            FacebookSdk.setApplicationId(facebook_app_id);
            FacebookSdk.sdkInitialize(getApplicationContext());
            callbackManager = CallbackManager.Factory.create();
            setContentView(R.layout.cidaas_activity_main);
            loginFB(getApplicationContext(), facebook_app_id);
        } else {
            Toast.makeText(getApplicationContext(), "Error initializing Facebook AppId!", Toast.LENGTH_LONG).show();
        }

    }

    public void loginFB(Context context, String facebook_app_id) {

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        Toast.makeText(MainActivity.this, "Login" + loginResult.getAccessToken().getToken(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancel() {
                        // App code
                        Toast.makeText(MainActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                        Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (getIntent() != null && getIntent().getStringExtra("facebook_app_id") != null) {
            String facebook_app_id = getIntent().getStringExtra("facebook_app_id");
            FacebookSdk.setApplicationId(facebook_app_id);
            FacebookSdk.sdkInitialize(this);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);

    }

}