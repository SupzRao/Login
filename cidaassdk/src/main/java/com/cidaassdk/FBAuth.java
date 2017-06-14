package com.cidaassdk;

import android.content.Context;
import android.content.Intent;

/**
 * Created by Suprada on 14-Jun-17.
 */

public class FBAuth implements I_LoginFB {


    @Override
    public void Login(Context context, String facebook_app_id) {

        Intent fbIntent = new Intent(context, MainActivity.class);
        fbIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        fbIntent.putExtra("facebook_app_id", facebook_app_id);
        context.startActivity(fbIntent);
    }

}
