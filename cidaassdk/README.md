Readme :            # CidaasSDK-Android


## Requirements

    minSdkVersion 18

## Installation


## Usage

The following steps are to be followed to use this CidaasSDK login control

    1. Include the View in to your XML
    Eg: <?xml version="1.0" encoding="utf-8"?>
        <RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
            xmlns:tools="http://schemas.android.com/tools"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:id="@+id/layout_root">

        </RelativeLayout>


    2. Get the instance of CidaasSDK.
    Eg: cidaasSDK = cidaasHelper = new CidaasSDK(getApplicationContext());

    3. Set all the properties of CidaasSDK such as AuthorizationURL, TokenURL, RedirectURI, ClientID,
        ClientSecret, ResponseType, ViewType, GrantType by using this object

    4. Also assign cidaasHelper.callback_ and override printmessage function of interface Icallback_ if necessary.
    Eg: cidaasSDK.callback_ = new Icallback_() {
                @Override
                public void printMessage(String msg) {
                    //Do your stuff
                }
            };

    6. Call the loadView function of CidaasSDK which has root layout and context as its parameter.
    Eg: Get the root layout and pass it to loadview function
        layout_root = (CidaasSDK) findViewById(R.id.layout_root);
        // pass this root layout as a parameter
        cidaasSDK.loadView(layout_root, context);

    7. Receive the access token as callback



