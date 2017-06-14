package com.cidaassdk;

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

import java.util.Arrays;


public class MainActivity extends AppCompatActivity{


    private CallbackManager callbackManager;
    private LoginManager mLoginManager;
    private Intent startIntent;
    static MainActivity instanceActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instanceActivity = this;
        if (getIntent() != null && getIntent().getStringExtra("facebook_app_id") != null) {
            String facebook_app_id = getIntent().getStringExtra("facebook_app_id");

            FacebookSdk.setApplicationId(facebook_app_id);
            FacebookSdk.sdkInitialize(getApplicationContext());
            mLoginManager = LoginManager.getInstance();
            callbackManager = CallbackManager.Factory.create();
            //  setContentView(R.layout.cidaas_activity_main);
            loginFB();
        } else {
            Toast.makeText(getApplicationContext(), "Error initializing Facebook AppId!", Toast.LENGTH_LONG).show();
        }

    }

    public static MainActivity getInstance() {
        return instanceActivity;
    }

    public void loginFB() {
        mLoginManager.logInWithReadPermissions(MainActivity.this, Arrays.asList("public_profile"));
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