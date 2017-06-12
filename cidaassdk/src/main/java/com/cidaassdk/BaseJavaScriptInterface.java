package com.cidaassdk;

import android.content.Context;
import android.webkit.JavascriptInterface;

/**
 * Created by Suprada on 09-Mar-17.
 */

public class BaseJavaScriptInterface {
    private static final String TAG = BaseJavaScriptInterface.class.getName();
    public Context context;

    public BaseJavaScriptInterface(Context context)
    {
        this.context = context;
    }
    @JavascriptInterface
    public void sendMsg(String msg) {
        if (msg.startsWith("/toLogin")) {
            toLogin();
        }
    }
    /** Show a toast from the web page */
    @JavascriptInterface
    public void toLogin()
    {

    }
}
