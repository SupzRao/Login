package com.cidaassdktest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.cidaassdk.CidaasSDK;
import com.cidaassdk.ResponseEntity;
import com.cidaassdk.UserProfile;

public class MainActivity extends AppCompatActivity {
    static CidaasSDK cidaasHelper = null;
    RelativeLayout layout_root;
    Button buttonlogout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layout_root = (RelativeLayout) findViewById(R.id.layout_root);
        buttonlogout = (Button) findViewById(R.id.buttonlogout);
        cidaasHelper = CidaasSDK.getCidaasSDKInst(getApplicationContext());
        buttonlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               CidaasSDK.logout("Your userId",getApplicationContext());
            }
        });
        cidaasHelper.setURLFile(getAssets(), "properties.xml");
        CidaasSDK.callback_ = new CidaasSDK.ILogin() {
            @Override
            public void OnSuccess(ResponseEntity entity) {
                Toast.makeText(MainActivity.this, entity.getAccess_token(), Toast.LENGTH_SHORT).show();
                buttonlogout.setVisibility(View.VISIBLE);
            }

            @Override
            public void OnError(String error) {

            }
        };
        CidaasSDK.userProfCallback = new CidaasSDK.UserProfCallback() {
            @Override
            public void onSuccess(UserProfile userProfile) {

            }

            @Override
            public void onError(String message) {
            }
        };
        cidaasHelper.login(layout_root, getApplicationContext(), CidaasSDK.callback_);
    }
}
