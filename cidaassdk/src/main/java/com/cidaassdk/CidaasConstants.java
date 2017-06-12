package com.cidaassdk;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Suprada on 08-Mar-17.
 */

public class CidaasConstants {


    public final static String REST_CONTENT_TYPE_URLENCODED = "application/x-www-form-urlencoded; charset=UTF-8";
    public final static String GRANT_TYPE = "authorization_code";
    public final static String RESPONSE_TYPE = "code";
    public final static String VIEW_TYPE = "login";

    public static boolean isInternetAvailable(final Context context) {

        ConnectivityManager conManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = conManager.getActiveNetworkInfo();
        return !((networkInfo == null) || (!networkInfo.isConnected()) || (!networkInfo.isAvailable()));
    }


}
