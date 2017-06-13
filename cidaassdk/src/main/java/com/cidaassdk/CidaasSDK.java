package com.cidaassdk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.http.SslError;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scottyab.aescrypt.AESCrypt;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by Suprada on 08-Mar-17.
 */

public class CidaasSDK extends RelativeLayout {
    private static final String TAG = "TAG";
    ProgressBar progressBar;
    private static WebView webview_ = null;
    private static boolean error_ = false;
    String internetError = "";
    private static SharedPreferences sp = null;
    private String authorizationURL;
    private static String tokenURL;
    private static String logoutURL;
    private static String clientId;
    private static String clientSecret;
    private static String grantType;
    private static String responseType;
    private View view;
    private static String redirectURI;
    public static ILogin callback_;
    public static UserProfCallback userProfCallback;
    private static String viewType;
    private static String error_description = "";
    private static String userIdURL;
    static SharedPreferences.Editor editor;
    private Document propertiesXML;
    static CidaasSDK cidaasHelper;
    public RelativeLayout layout;
    public ILogin finalcallback_;
    public String facebook_app_id,google_app_id;

    public CidaasSDK(Context context) {
        super(context);
        init(context);
    }

    public CidaasSDK(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private WebView getInstanceOfWebview(Context context) {
        if (webview_ == null) {
            webview_ = new WebView(context);
        }
        return webview_;
    }

    private static String getUserIdURL() {
        if (userIdURL == null)
            return "";
        else
            return userIdURL;
    }

    private String getViewType() {
        if (viewType == null)
            return "";
        else
            return viewType;
    }

    private static String getLogoutURL() {
        if (logoutURL == null)
            return "";
        else
            return logoutURL;
    }


    private String getClientId() {
        if (clientId == null)
            return "";
        else
            return clientId;
    }


    private String getRedirectURI() {
        if (redirectURI == null)
            return "";
        else
            return redirectURI;
    }


    private String getResponseType() {
        if (responseType == null)
            return CidaasConstants.RESPONSE_TYPE;
        else
            return responseType;
    }

    private String getAuthorizationURL() {
        if (authorizationURL == null)
            return "";
        else
            return authorizationURL;
    }


    private String getTokenURL() {
        if (tokenURL == null)
            return "";
        else
            return tokenURL;
    }


    private String getClientSecret() {
        if (clientSecret == null)
            return "";
        else
            return clientSecret;
    }


    private String getGrantType() {
        if (grantType == null)
            return CidaasConstants.GRANT_TYPE;
        else
            return grantType;
    }


    /*
    * With the help of the url configuration you provided from your assets
    * Loading the dynamic Webview which is attached to the layout you provide
    * You will get back the result which is ResponseEntity if you have overriden the ILogin -> callback success method
     * if any error you will get it in Onerror() method
    * */
    public void login(final RelativeLayout layout, final Context context, ILogin callback_) {
        this.layout = layout;
        /*
        * remove all the previous view in custom layout
        * */
        // layout.removeAllViews();
        try {
            String errorMsg = "";
            if (CidaasConstants.isInternetAvailable(context)) {
                if (getAuthorizationURL().equals("")) {
                    errorMsg = "AuthorizationURL Missing";
                    getErrorImage(layout, context, errorMsg);

                } else if (getRedirectURI().equals("")) {
                    errorMsg = "RedirectURI Missing";
                    getErrorImage(layout, context, errorMsg);

                } else if (getResponseType().equals("")) {
                    errorMsg = "ResponseType Missing ";
                    getErrorImage(layout, context, errorMsg);

                } else if (getClientId().equals("")) {
                    errorMsg = "ClientId Missing ";
                    getErrorImage(layout, context, errorMsg);

                } else if (getViewType().equals("")) {
                    errorMsg = "ViewType Missing ";
                    getErrorImage(layout, context, errorMsg);

                } else if (getClientSecret().equals("")) {
                    errorMsg = "ClientSecret Missing ";
                    getErrorImage(layout, context, errorMsg);
                } else if (getTokenURL().equals("")) {
                    errorMsg = "TokenURL Missing ";
                    getErrorImage(layout, context, errorMsg);
                } else if (getGrantType().equals("")) {
                    errorMsg = "GrantType Missing ";
                    getErrorImage(layout, context, errorMsg);
                } else if (getUserIdURL().equals("")) {
                    errorMsg = "User ID URL Missing ";
                    getErrorImage(layout, context, errorMsg);
                } else if (getLogoutURL().equals("")) {
                    errorMsg = "Logout URL Missing ";
                    getErrorImage(layout, context, errorMsg);
                } else {
                    finalcallback_ = callback_;
                    String myUrl = constructURL();
                    webview_ = getInstanceOfWebview(context);
                    WebSettings settings = webview_.getSettings();
                    settings.setJavaScriptEnabled(true);
                    CookieSyncManager.createInstance(context);
                    CookieManager cookieManager = CookieManager.getInstance();
                    cookieManager.removeAllCookie();
                    webview_.loadUrl(myUrl);
                    ProgressBar progressBar = new ProgressBar(context);
                    webview_.addJavascriptInterface(new BaseJavaScriptInterface(context), "NativeMsgForwarder");
                    settings.setSavePassword(false);
                    settings.setJavaScriptCanOpenWindowsAutomatically(true);
                    settings.setSupportMultipleWindows(true);
                    settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
                    webview_.setWebViewClient(new BaseWebViewClient(progressBar, context, callback_, getTokenURL(), getClientId(),
                            getRedirectURI(), getClientSecret(), getGrantType(), layout));
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
                    webview_.setOnKeyListener(new OnKeyListener() {
                        @Override
                        public boolean onKey(View view, int i, KeyEvent keyEvent) {
                            if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                                if (webview_ != null) {
                                    // on back from login screen close app
                                    if (webview_.canGoBack()) {
                                        webview_.goBack();

                                    } else {
                                        Intent intent = new Intent(Intent.ACTION_MAIN);
                                        intent.addCategory(Intent.CATEGORY_HOME);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        context.startActivity(intent);
                                    }
                                }
                                return true;
                            }
                            return false;
                        }
                    });
                /*
                * adding second element progress bar overlapping webview
                * */
                    RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams
                            (ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params1.addRule(RelativeLayout.CENTER_IN_PARENT, webview_.getId());
                    layout.addView(webview_, params);
                    layout.addView(progressBar, params1);
                }

            } else {
                internetError = "Try Again";
                getInternetErrorImage(layout, context, internetError);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    void getInternetErrorImage(final RelativeLayout layout, final Context context, String internet_error_msg) {
        ImageView image;
        image = new ImageView(context);
        image.setImageDrawable(context.getResources().getDrawable(R.drawable.no_internet_bg));
        LayoutParams params = new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        Button button = new Button(context);
        button.setText(internet_error_msg);
        button.setPadding(5, 5, 5, 5);
        button.setTextColor(Color.BLACK);
        button.setBackgroundColor(Color.LTGRAY);
        LayoutParams params1 = new LayoutParams
                (ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, image.getId());
        params1.addRule(RelativeLayout.CENTER_IN_PARENT, image.getId());
        params1.setMargins(5, 5, 5, 100);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                login(layout, context, callback_);
            }
        });
        layout.addView(button, params1);
        layout.addView(image, params);
    }

    void getErrorImage(RelativeLayout layout, Context context, String errorMsg) {
        ImageView image = new ImageView(context);
        image.setImageDrawable(context.getResources().getDrawable(R.drawable.settings));
        LayoutParams params = new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        TextView textViewError = new TextView(context);
        textViewError.setText(errorMsg);
        RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams
                (ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params1.addRule(RelativeLayout.CENTER_IN_PARENT, image.getId());
        params1.addRule(RelativeLayout.ALIGN_PARENT_TOP, image.getId());
        textViewError.setPadding(5, 100, 5, 0);
        textViewError.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        textViewError.setTextColor(Color.BLACK);
        layout.addView(textViewError, params1);
        layout.addView(image, params);
    }

    /*
    *
    * generate URL by given redirectURI and client id
    *
    * */
    private String constructURL() {

        return getAuthorizationURL() + "?redirect_uri=" + getRedirectURI() +
                "&response_type=" + getResponseType() + "&client_id=" +
                getClientId() + "&viewtype=" + getViewType() + "&deviceid=";
    }


    private void init(Context context) {
        webview_ = getInstanceOfWebview(context);
        sp = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sp.edit();
    }

    @Override
    public boolean dispatchKeyEventPreIme(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            if (webview_ != null) {
                // on back from login screen close app
                if (webview_.canGoBack()) {
                    webview_.goBack();

                } else {
                    ((Activity) getContext()).finish();
                }
            }
            return true;
        }
        return super.dispatchKeyEventPreIme(event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webview_ != null) {
                // on back from login screen close app
                if (webview_.canGoBack()) {
                    webview_.goBack();

                } else {
                    ((Activity) getContext()).finish();
                }
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /*
    * User has to send their URL configuration file name along with their asset manager
    * So that we will map the url based on the key
    * Please do not change the key name in the file (as suggested in the readme) which
    * will fail in loading view.
    * */
    public void setURLFile(AssetManager assetManager, String fileName) {
        InputStream inputStream = null;
        try {
            inputStream = assetManager.open(fileName);
        } catch (IOException e) {
            Log.e("tag", e.getMessage());
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte buf[] = new byte[1024];
        int len;
        try {
            while ((len = inputStream.read(buf)) != -1) {
                outputStream.write(buf, 0, len);
            }
            outputStream.close();
            inputStream.close();
            propertiesXML = obtenerDocumentDeByte(outputStream.toByteArray());
            XPathFactory xPathfactory = XPathFactory.newInstance();
            XPath xpath = xPathfactory.newXPath();
            XPathExpression expr = xpath.compile("//resources/item[@string]");
            NodeList nl = (NodeList) expr.evaluate(propertiesXML, XPathConstants.NODESET);
            NodeList nodeList = propertiesXML.getElementsByTagName("item");
            for (int x = 0, size = nodeList.getLength(); x < size; x++) {
                if (nodeList.item(x).getAttributes().getNamedItem("name").getNodeValue().
                        equalsIgnoreCase(getContext().getString(R.string.RedirectURI))) {
                    redirectURI = nodeList.item(x).getTextContent().trim();
                } /*else if (nodeList.item(x).getAttributes().getNamedItem("name").getNodeValue().
                        equalsIgnoreCase(getContext().getString(R.string.ResponseType))) {
                    responseType = nodeList.item(x).getTextContent().trim();
                }*/ else if ((nodeList.item(x).getAttributes().getNamedItem("name").getNodeValue().
                        equalsIgnoreCase(getContext().getString(R.string.ClientId)))) {
                    clientId = nodeList.item(x).getTextContent().trim();
                } else if ((nodeList.item(x).getAttributes().getNamedItem("name").getNodeValue().
                        equalsIgnoreCase(getContext().getString(R.string.ViewType)))) {
                    viewType = nodeList.item(x).getTextContent().trim();
                } else if ((nodeList.item(x).getAttributes().getNamedItem("name").getNodeValue().
                        equalsIgnoreCase(getContext().getString(R.string.ClientSecret)))) {
                    clientSecret = nodeList.item(x).getTextContent().trim();
                } else if ((nodeList.item(x).getAttributes().getNamedItem("name").getNodeValue().
                        equalsIgnoreCase(getContext().getString(R.string.TokenURL)))) {
                    tokenURL = nodeList.item(x).getTextContent().trim();
                } /*else if ((nodeList.item(x).getAttributes().getNamedItem("name").getNodeValue().
                        equalsIgnoreCase(getContext().getString(R.string.GrantType)))) {
                    grantType = nodeList.item(x).getTextContent().trim();
                }*/ else if ((nodeList.item(x).getAttributes().getNamedItem("name").getNodeValue().
                        equalsIgnoreCase(getContext().getString(R.string.UserIdURL)))) {
                    userIdURL = nodeList.item(x).getTextContent().trim();
                } else if ((nodeList.item(x).getAttributes().getNamedItem("name").getNodeValue().
                        equalsIgnoreCase(getContext().getString(R.string.LogoutURL)))) {
                    logoutURL = nodeList.item(x).getTextContent().trim();
                } else if ((nodeList.item(x).getAttributes().getNamedItem("name").getNodeValue().
                        equalsIgnoreCase(getContext().getString(R.string.AuthorizationURL)))) {
                    authorizationURL = nodeList.item(x).getTextContent().trim();
                }
                else if ((nodeList.item(x).getAttributes().getNamedItem("name").getNodeValue().
                        equalsIgnoreCase(getContext().getString(R.string.facebook_app_id_)))) {
                    facebook_app_id = nodeList.item(x).getTextContent().trim();
                }
                else if ((nodeList.item(x).getAttributes().getNamedItem("name").getNodeValue().
                        equalsIgnoreCase(getContext().getString(R.string.google_app_id_)))) {
                    google_app_id = nodeList.item(x).getTextContent().trim();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private Document obtenerDocumentDeByte(byte[] documentoXml) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(new ByteArrayInputStream(documentoXml));
    }

    /*
        *
        * Custom Client to Override Pageload functions
        *
        * */
    class BaseWebViewClient extends WebViewClient {
        ILogin callback_;
        ArrayList<String> usedCodes = new ArrayList<>();
        String redirectURI, clientSecret, grantType, clientId, tokenURL, CODE, base[], countString[];
        Context context;
        ProgressBar progressBar;
        RelativeLayout layout;

        public BaseWebViewClient(ProgressBar progressBar, Context context, ILogin callback_, String tokenURL,
                                 String clientId, String redirectURI, String clientSecret, String grantType,
                                 RelativeLayout layout) {
            this.callback_ = callback_;
            this.redirectURI = redirectURI;
            this.clientSecret = clientSecret;
            this.grantType = grantType;
            this.clientId = clientId;
            this.tokenURL = tokenURL;
            this.context = context;
            this.progressBar = progressBar;
            progressBar.setVisibility(View.VISIBLE);
            this.layout = layout;

        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
          /*  if (url.contains("getSocialAuthCode/facebook") && (facebook_app_id != null && !facebook_app_id.equals(""))) {
                view.stopLoading();
                doFBLogin(context);



            } else*/ if (url.contains("getSocialAuthCode/google") && (google_app_id != null && !google_app_id.equals(""))) {
                view.stopLoading();
                doGoogleLogin(context);
            } else {

                view.loadUrl(url);
                try {
                    if (url.contains("error_code")) {
                        //Toast.makeText(loginActivity, R.string.warn_wrong_username_pswd, Toast.LENGTH_LONG).show();
                        return true;
                    } else {
                        if (url.contains("code=") && url.contains("calback.html")) {
                            base = url.split("\\?");
                            countString = base[0].split("&");
                            Map<String, String> queryDataItems = new HashMap<>();
                            String[] value_all = url.split("\\?");
                            String[] queryData = value_all[1].split("&");
                            for (int i = 0; i < queryData.length; i++) {
                                if (queryData[i].split("=").length > 1) {
                                    queryDataItems.put(queryData[i].split("=")[0], queryData[i].split("=")[1]);
                                }
                            }
                            // login
                            CODE = queryDataItems.get("code");
                            if (CODE != null) {
                                if (!usedCodes.contains(CODE)) {
                                    usedCodes.add(CODE);
                                    if (CidaasConstants.isInternetAvailable(context)) {
                                        //  Get Access token Form CODE
                                        getAccessToken(tokenURL, CidaasConstants.REST_CONTENT_TYPE_URLENCODED, clientId,
                                                redirectURI, CODE, clientSecret,
                                                grantType);
                                    } else {
                                        internetError = getContext().getString(R.string.tryAgain);
                                        getInternetErrorImage(layout, context, internetError);
                                    }
                                }

                            }
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            return true;
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            System.out.println("Error:" + failingUrl);
            error_ = true;
            error_description = description;

        }

        @Override
        public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
            error_ = true;
            error_description = "HttpError!";
            super.onReceivedHttpError(view, request, errorResponse);
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            System.out.println("Error:" + error);
            error_ = true;
            error_description = "SSL error!!";
            errorLayout();
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            if (error_ && !error_description.equals("")) {
                errorLayout();
            }
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
            view.loadUrl("javascript:$(document).ready(function() {$(document).foundation();}");
        }

        void errorLayout() {
            // layout.removeAllViews();
            getErrorImage(layout, context, error_description);
            System.out.println("Error has occured!");
            progressBar.setVisibility(View.GONE);
        }

        private void doFBLogin(final Context context) {
            /*Intent fbIntent = new Intent(context, MainActivity.class);
            fbIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            fbIntent.putExtra("facebook_app_id", facebook_app_id);
            context.startActivity(fbIntent);*/
            MainActivity mainActivity=new MainActivity();
            mainActivity.loginFB(getContext(),facebook_app_id);
        }

        private void doGoogleLogin(Context context) {
            Intent fbIntent = new Intent(context, GoogleActivity.class);
            fbIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            fbIntent.putExtra("google_app_id", google_app_id);
            context.startActivity(fbIntent);
        }


        /**
         * Call function get access token
         */
        public void getAccessToken(String tokenURL, String content_type, String client_id,
                                   String redirect_uri, String code, String client_secret, String
                                           grant_type) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://your.api.url/")
                    .addConverterFactory(JacksonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            ICidaasAPI service = retrofit.create(ICidaasAPI.class);
            if (callback_ == null) {
                callback_ = new ILogin() {
                    @Override
                    public void OnSuccess(ResponseEntity entity) {

                    }

                    @Override
                    public void OnError(String error) {

                    }
                };
            }
            service.getAccessTokenApi1(tokenURL, content_type, client_id,
                    redirect_uri, code, client_secret, grant_type)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<LoginEntity>() {
                        @Override
                        public void onCompleted() {
                            System.out.println("Oncomplete");
                        }

                        @Override
                        public void onError(Throwable e) {
                            System.out.println("Onerror");
                            error_ = true;
                            error_description = e.getMessage();
                            callback_.OnError(e.getMessage());
                        }

                        @Override
                        public void onNext(LoginEntity loginEntity) {
                            if (loginEntity != null) {
                                layout.removeView(webview_);
                                saveLoginDetails(loginEntity);
                                ResponseEntity responseEntity = new ResponseEntity();
                                responseEntity.setAccess_token(loginEntity.getAccess_token());
                                responseEntity.setError(null);
                                responseEntity.setSuccess(true);
                                callback_.OnSuccess(responseEntity);
                                userProfCallback = new UserProfCallback() {
                                    @Override
                                    public void onSuccess(UserProfile userProfile) {
                                        saveData(userProfile, context);
                                    }

                                    @Override
                                    public void onError(String message) {
                                    }
                                };
                                getUserInfoFromAccessToken(userProfCallback, loginEntity.getAccess_token(), context);
                            }

                        }
                    });
        }

    }

    private static void saveLoginDetails(LoginEntity loginEntity) {
        ObjectMapper mapper = new ObjectMapper();
        String salt = UUID.randomUUID().toString();
        String en = null;
        long timeinmillis = System.currentTimeMillis();
        long time = timeinmillis / 1000;
        time = time + loginEntity.getExpires_in() - 10;
        LoginWithUserIDEntity loginWithUserIDEntity = new LoginWithUserIDEntity();
        try {
            loginWithUserIDEntity.setExpires_in(time);
            en = AESCrypt.encrypt(salt, loginEntity.getAccess_token());
            loginWithUserIDEntity.setAccess_token(en);
            loginWithUserIDEntity.setId_token(loginEntity.getId_token());
            loginWithUserIDEntity.setRefresh_token(loginEntity.getRefresh_token());
            loginWithUserIDEntity.setScope(loginEntity.getScope());
            loginWithUserIDEntity.setUserstate(loginEntity.getUserstate());
            loginWithUserIDEntity.setSalt(salt);
            String loginEntityAsString = mapper.writeValueAsString(loginWithUserIDEntity);
            editor.putString("CidaasEntity", loginEntityAsString);
            editor.commit();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    /*
    * Get your Complete Profile by passing userProfCallback, access_token and the context
    * If any error you can handle it through the callback onerror method
    * */
    public static void getUserInfoFromAccessToken(final UserProfCallback userProfCallback, String access_token, final Context context) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://your.api.url/")
                .addConverterFactory(JacksonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        ICidaasAPI service = retrofit.create(ICidaasAPI.class);

        service.getUserDetailsApi(getUserIdURL(), access_token).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserProfile>() {
                               @Override
                               public void onCompleted() {
                                   System.out.println("Get user profile completed ");
                               }

                               @Override
                               public void onError(Throwable e) {
                                   error_ = true;
                                   error_description = e.getMessage();
                                   userProfCallback.onError(e.getMessage());
                                   System.out.println("Get user profile err " + e.getMessage());
                               }

                               @Override
                               public void onNext(UserProfile userInfo) {
                                   userProfCallback.onSuccess(userInfo);
                               }
                           }

                );
    }


    private static void saveData(UserProfile userInfo, Context context) {
        System.out.println("Get user profile res User id : " + userInfo.getId());
        if (sp == null) {
            sp = PreferenceManager.getDefaultSharedPreferences(context);
        } else if (editor == null) {
            editor = sp.edit();
        } else {
            ObjectMapper mapper = new ObjectMapper();
            String CidaasEntity = sp.getString("CidaasEntity", "");
            try {
                LoginWithUserIDEntity loginWithUserIDEntity = mapper.readValue(CidaasEntity, LoginWithUserIDEntity.class);
                loginWithUserIDEntity.setId(userInfo.getId());
                String loginEntityAsString = mapper.writeValueAsString(loginWithUserIDEntity);
                editor.putString("CidaasEntity", loginEntityAsString);
                editor.commit();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
/*
* By Passing the userId you can be able to get the access_token
* and get back the new access_token through the callback you send
* */

    public static void getAccessTokenByUserId(String userId, ILogin callback_) {
        ObjectMapper mapper = new ObjectMapper();
        String CidaasEntity = sp.getString("CidaasEntity", "");
        final ResponseEntity responseEntity = new ResponseEntity();
        if (CidaasEntity != null || !CidaasEntity.equals("")) {
            try {
                LoginWithUserIDEntity loginWithUserIDEntity = mapper.readValue(CidaasEntity, LoginWithUserIDEntity.class);
                String UserID = loginWithUserIDEntity.getId();
                Long ExpiresIn = loginWithUserIDEntity.getExpires_in();
                String AccessToken = loginWithUserIDEntity.getAccess_token();
                String RefreshToken = loginWithUserIDEntity.getRefresh_token();
                String Salt = loginWithUserIDEntity.getSalt();
                long timeinmillis = System.currentTimeMillis();
                long time = timeinmillis / 1000;
                System.out.println("Current Time: " + time + "Expires in: " + ExpiresIn);
                if (UserID != null && UserID != "" && UserID.equals(userId)) {

                    if (ExpiresIn > time) {
                        try {
                            String de = AESCrypt.decrypt(Salt, AccessToken);
                            responseEntity.setAccess_token(de);
                            responseEntity.setError("Success");
                            responseEntity.setSuccess(true);
                            callback_.OnSuccess(responseEntity);
                        } catch (GeneralSecurityException e) {
                            e.printStackTrace();
                        }

                    } else {
                        getAccessTokenByRefreshToken(callback_, RefreshToken);

                    }
                } else {
                    callback_.OnError("Invalid UserID!!");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private static void getAccessTokenByRefreshToken(final ILogin callback_, String RefreshToken) {
        final ResponseEntity responseEntity = new ResponseEntity();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://your.api.url/")
                .addConverterFactory(JacksonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        ICidaasAPI service = retrofit.create(ICidaasAPI.class);
        service.getAccessTokenByRefreshToken(tokenURL, CidaasConstants.REST_CONTENT_TYPE_URLENCODED, clientId,
                redirectURI, RefreshToken, clientSecret,
                "refresh_token").subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginEntity>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("Oncompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        error_ = true;
                        error_description = e.getMessage();
                        System.out.println("onError");
                        responseEntity.setError(e.getMessage());
                        responseEntity.setSuccess(false);
                        responseEntity.setAccess_token(null);
                        callback_.OnError(e.getMessage());
                    }

                    @Override
                    public void onNext(LoginEntity loginEntity) {
                        System.out.println("onNext");
                        saveLoginDetails(loginEntity);
                        responseEntity.setError(null);
                        responseEntity.setSuccess(true);
                        responseEntity.setAccess_token(loginEntity.getAccess_token());
                        callback_.OnSuccess(responseEntity);
                        String salt = UUID.randomUUID().toString();
                        String en = null;
                        long timeinmillis = System.currentTimeMillis();
                        long time = timeinmillis / 1000;
                        time = time + loginEntity.getExpires_in() - 10;
                        ObjectMapper mapper = new ObjectMapper();
                        String CidaasEntity = sp.getString("CidaasEntity", "");
                        try {
                            LoginWithUserIDEntity loginWithUserIDEntity = mapper.readValue(CidaasEntity, LoginWithUserIDEntity.class);
                            loginWithUserIDEntity.setExpires_in(time);
                            en = AESCrypt.encrypt(salt, loginEntity.getAccess_token());
                            loginWithUserIDEntity.setAccess_token(en);
                            loginWithUserIDEntity.setId_token(loginEntity.getId_token());
                            loginWithUserIDEntity.setRefresh_token(loginEntity.getRefresh_token());
                            loginWithUserIDEntity.setScope(loginEntity.getScope());
                            loginWithUserIDEntity.setUserstate(loginEntity.getUserstate());
                            loginWithUserIDEntity.setSalt(salt);
                            String loginEntityAsString = mapper.writeValueAsString(loginWithUserIDEntity);
                            editor.putString("CidaasEntity", loginEntityAsString);
                            editor.commit();
                        } catch (GeneralSecurityException e) {
                            e.printStackTrace();
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    }
                });

    }

    /*
    * Gives you the instance of the class CidaasSDK
    * */
    public static CidaasSDK getCidaasSDKInst(Context context) {
        if (cidaasHelper == null) {
            cidaasHelper = new CidaasSDK(context);
        }
        return cidaasHelper;
    }

    /*
    * Logs out the user
    * @param Userid and context which will not return any result
    * it load the og in view after logs out
    * */
    public static void logout(String UserId, final Context context) {
        callback_ = new ILogin() {
            @Override
            public void OnSuccess(ResponseEntity entity) {
                if (CidaasConstants.isInternetAvailable(context)) {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("https://your.api.url/")
                            .addConverterFactory(JacksonConverterFactory.create())
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .build();
                    ICidaasAPI service = retrofit.create(ICidaasAPI.class);
                    if (entity != null && entity.getAccess_token() != null) {
                        service.logoutUser(getLogoutURL(), entity.getAccess_token(), entity.getAccess_token())
                                .subscribeOn(Schedulers.newThread())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Observer<Void>() {

                                    @Override
                                    public void onCompleted() {
                                        System.out.println("Log out Oncompleted");
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        error_ = true;
                                        error_description = e.getMessage();
                                        System.out.println("Log out onError");

                                    }

                                    @Override
                                    public void onNext(Void aVoid) {
                                        CidaasSDK cidaasSDK = getCidaasSDKInst(context);
                                        if (cidaasSDK != null && cidaasSDK.layout != null && cidaasSDK.finalcallback_ != null)
                                            cidaasSDK.login(cidaasSDK.layout, context, cidaasSDK.finalcallback_);
                                        System.out.println("Log out onNext");
                                        CookieSyncManager.createInstance(context);
                                        CookieManager cookieManager = CookieManager.getInstance();
                                        cookieManager.removeAllCookie();
                                        editor.clear();
                                        editor.commit();
                                    }
                                });
                    } else {
                        CookieSyncManager.createInstance(context);
                        CookieManager cookieManager = CookieManager.getInstance();
                        cookieManager.removeAllCookie();
                        editor.clear();
                        editor.commit();
                    }

                } else {
                    CookieSyncManager.createInstance(context);
                    CookieManager cookieManager = CookieManager.getInstance();
                    cookieManager.removeAllCookie();
                    editor.clear();
                    editor.commit();
                }
            }

            @Override
            public void OnError(String error) {
                editor.clear();
                editor.commit();
            }
        };
        getAccessTokenByUserId(UserId, callback_);
    }

    /*
    * call back to get the result when user logged in
    * */
    public interface ILogin {
        public void OnSuccess(ResponseEntity entity);

        public void OnError(String error);

    }

    /*
    * Callback to get the user profile
    * */
    public interface UserProfCallback {
        void onSuccess(UserProfile userProfile);

        void onError(String message);
    }
}




