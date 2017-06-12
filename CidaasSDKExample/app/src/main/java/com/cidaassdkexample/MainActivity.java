package com.cidaassdkexample;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cidaassdk.CidaasSDK;
import com.cidaassdk.ResponseEntity;
import com.cidaassdk.UserProfile;

public class MainActivity extends AppCompatActivity {
    RelativeLayout root;
    TextView textView;
    Button button_log_out, button_user_prof;
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = sp.edit();
        textView= (TextView) findViewById(R.id.textView);
        root = (RelativeLayout) findViewById(R.id.root_layout);
        button_log_out = (Button) findViewById(R.id.button_log_out);
        button_user_prof = (Button) findViewById(R.id.button_user_prof);
        CidaasSDK cidaasHelper = CidaasSDK.getCidaasSDKInst(getApplicationContext());
        cidaasHelper.setURLFile(getAssets(), "properties.xml");
         /*
        * create call back to get access token and show
        * */
        cidaasHelper.callback_ = new CidaasSDK.ILogin() {

            @Override
            public void OnSuccess(ResponseEntity responseEntity) {
                Toast.makeText(getApplicationContext(), responseEntity.getAccess_token(), Toast.LENGTH_LONG).show();
                editor.putString("Access_Token", responseEntity.getAccess_token());
                editor.commit();
                button_user_prof.setVisibility(View.VISIBLE);
                button_log_out.setVisibility(View.VISIBLE);
            }

            @Override
            public void OnError(String s) {
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();

            }
        };
        button_log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CidaasSDK.logout("Your User ID", getApplicationContext());
            }
        });
        final CidaasSDK.UserProfCallback userProfCallback = new CidaasSDK.UserProfCallback() {
            @Override
            public void onSuccess(UserProfile userProfile) {
                if (userProfile != null) {
                    textView.setVisibility(View.VISIBLE);
                    textView.setText(userProfile.getDisplayName());
                }
            }

            @Override
            public void onError(String message) {
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        };
        button_user_prof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String AccessToken = sp.getString("Access_Token", "");
                CidaasSDK.getUserInfoFromAccessToken(userProfCallback, AccessToken, getApplicationContext());
            }
        });
        cidaasHelper.login(root, getApplicationContext(), cidaasHelper.callback_);
    }
}
