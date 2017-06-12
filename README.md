## More about Cidaas

To know more about Cidaas visit [CIDaaS](https://www.cidaas.com)

## Cidaas Documentation 

https://docs.cidaas.de/

## Requirements

    minSdkVersion 18

## Installation

CidaasSDK is available through [Github](http://github.com). To install
it, add the following line to your app level gradle file:

Add it in your root build.gradle at the end of repositories:
```java        
        allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		              }
		    }
```		
 Add the dependency
 ```java
       dependencies {
       		compile 'com.github.Cidaas:cidaas-sdk-android:0.1.2'

	}
 ```

## Getting started

The following steps are to be followed to use this CidaasSDK login control

1. Once Gradle sync is completed, Create the object for the class CidaasSDK by passing the application context as a parameter.  
```java
            CidaasSDK cidaassdk = new CidaasSDK(getApplicationContext()); 
	    				OR
	    CidaasSDK cidaassdk = CidaasSDK.getCidaasSDKInst(getApplicationContext());
```
      
2. Create a folder called "assets"  in your project inside main folder. Create a xml file to map values to URL configurations.

 Eg: Properties.xml
 
 Copy below Properties and modify it by giving respective URL
```xml
         <?xml version="1.0" encoding="utf-8"?>
        <resources>
            <item name="RedirectURI" type="string">Your Redirect URI</item>
            <item name="ClientId" type="string">Your ClientId</item>
            <item name="ViewType" type="string">Your View Type</item>
            <item name="ClientSecret" type="string">Your ClientSecret</item>
            <item name="TokenURL" type="string">Your Token URL</item>
            <item name="UserIdURL" type="string">Your UserId URL</item>
            <item name="LogoutURL" type="string">Your Logout URL</item>
            <item name="AuthorizationURL" type="string">Your Authorization URL</item>
        </resources>
```
3. Next Map the .xml file values to our CIDaasSDK by calling the API method setURLFile(assets,filename).
  ```java             
  
			cidaasHelper.setURLFile(getAssets(),"Properties.xml");
			
  ```              
Here filename i.e Properties.xml is the file which has all the configuration URL.It will read the file and assign all the values to the corresponding to fields and helps in constructing URL.

4.  Call Login API when you wants to load Login page of Cidaas. To Get Access token, Use the following method which gives Response Entity.
```java
  		    layout_root = (RelativeLayout) findViewById(R.id.layout_root);
                    cidaasSDK.login(layout_root, getApplicationContext(), new CidaasSDK.ILogin() {
			    @Override
			    public void OnSuccess(ResponseEntity entity) {
				//entity.getAccess_token() to get access token
			    }
			    @Override
			    public void OnError(String s) {
				//Handle error			   
				}
        		});
```  
   This root layout must be RelativeLayout it is necessary to add view to your root view.     
    Here callback_ is neccessary to get Access_token.


5. To get the User profile using access_token call getUserInfoFromAccessToken API. Where pass the context and access token as a parameter.Here userProfCallback is Callback method it will be triggered once the User Profile loaded from SDK. 

```java
                  CidaasSDK.getUserInfoFromAccessToken(new CidaasSDK.UserProfCallback() {
                    @Override
                    public void onSuccess(UserProfile userProfile) {
                       //Do your stuff after getting profile
                    }

                    @Override
                    public void onError(String s) {
			//handle error
                    }
                }, AccessToken, getApplicationContext());
```
6. To get the Access token by userid call the getAccessTokenByUserId API.Pass the userid and Callbackmethod name        
```java
        CidaasSDK.getAccessTokenByUserId(userId, new CidaasSDK.ILogin() {
            @Override
            public void OnSuccess(ResponseEntity responseEntity) {
                  //Do your stuff after getting access_token from entity.getAccess_token()
            }

            @Override
            public void OnError(String s) {
  		//Do your stuff to handle error
            }
        });
```        
7. To Logout the User                
    Call the API to logout the user where pass the userId and context as a parameter. 
    
```java    
                CidaasSDK.logout(UserId,context)
```

## Steps to find app id and urls

1. Open the CIDaaS admin UI dashboard page

<p align="center">

<img src = "https://cloud.githubusercontent.com/assets/26590601/24350391/a6495556-12ff-11e7-8df0-b37f5a538ae2.png" alt="Dashboard" width="50%">

</p>

2. Move to the Apps menu

<p align="center">

<img src = "https://cloud.githubusercontent.com/assets/26590601/24350449/e1a57954-12ff-11e7-9e73-4fb96e5b934d.png" alt="Apps" width="50%">

</p>

3. Switch to Android app tab

<p align="center">

<img src = "https://cloud.githubusercontent.com/assets/26590601/24598485/3b17b8da-1869-11e7-87b2-3ae5ce522a18.png" alt="Android App" width="50%">

</p>

4. Press edit button in your app

5. Note down the app id and app secret for your app

<p align="center">

<img src = "https://cloud.githubusercontent.com/assets/26590601/24598720/9295d74e-186a-11e7-8898-bea953662fbe.png" alt="App Id" width="50%">

</p>

6. Move to the OAuth2 endpoints menu

7. Note down the authorization url, token url, user info url and logout url

<p align="center">

<img src = "https://cloud.githubusercontent.com/assets/26590601/24355195/9fea006c-1312-11e7-9af1-2566303631d6.png" alt="End points" width="50%">

</p>

## Example

Look at the example project [here](https://github.com/Cidaas/sdk-Android/tree/master/CidaasSDKExample)

## Sample Code
activity.xml
```xml
	<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
	    xmlns:tools="http://schemas.android.com/tools"
	    android:id="@+id/activity_main"
	    android:layout_width="match_parent"
	    android:layout_height="match_parent"
	    android:paddingBottom="@dimen/activity_vertical_margin"
	    android:paddingLeft="@dimen/activity_horizontal_margin"
	    android:paddingRight="@dimen/activity_horizontal_margin"
	    android:paddingTop="@dimen/activity_vertical_margin"
	    tools:context="com.cidaassdkexample.MainActivity">

	    <RelativeLayout
		android:id="@+id/root_layout"
		android:layout_width="match_parent"
		android:layout_height="300dp"
		android:layout_alignParentStart="true"
		android:layout_marginTop="14dp">

		<TextView
		    android:id="@+id/textView"
		    android:layout_width="wrap_content"
		    android:layout_height="wrap_content"
		    android:text="Hai"
		    android:textSize="25sp"
		    android:visibility="gone"
		    android:layout_alignParentTop="true"
		    android:layout_centerHorizontal="true"
		    android:layout_marginTop="199dp" />
	    </RelativeLayout>

	    <RelativeLayout
		android:layout_width="match_parent"
		android:layout_height="match_parent"
		android:layout_below="@+id/root_layout">

		<Button
		    android:id="@+id/button_user_prof"
		    android:layout_width="100dp"
		    android:layout_height="wrap_content"
		    android:layout_alignParentStart="true"
		    android:layout_alignParentTop="true"
		    android:layout_marginStart="41dp"
		    android:text="User Profile"
		    android:visibility="gone" />

		<Button
		    android:id="@+id/button_log_out"
		    android:layout_width="100dp"
		    android:layout_height="wrap_content"
		    android:layout_alignParentEnd="true"
		    android:layout_alignParentTop="true"
		    android:layout_marginEnd="33dp"
		    android:text="Log out"
		    android:visibility="gone" />
	    </RelativeLayout>
	</RelativeLayout>
```
MainActivity.java


```java
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
		cidaasHelper.login(root, getApplicationContext(), cidaasHelper.callback_);
		button_log_out.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View view) {
			CidaasSDK.logout(userId, getApplicationContext());
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
		
	    }

```    

## Screen shots
<p align="center">

<img src = "https://cloud.githubusercontent.com/assets/26590601/24597479/ca4a3966-1863-11e7-82aa-c2bbff695acb.png" alt="Screen 1" style="width:40%" height="600">

<img src = "https://cloud.githubusercontent.com/assets/26590601/24597565/3f050a4c-1864-11e7-8613-bd94f87e0f70.png" alt="Screen 2" style="width:40%" height="600">

</p>

## Note
1. If you miss any one of the properties value, view will be empty (Make sure you have configured all the values in properties file without changing the name)
2. You may face Run time error like 
    Error:Execution failed for task ':app:transformResourcesWithMergeJavaResForDebug'.
    > com.android.build.api.transform.TransformException: com.android.builder.packaging.DuplicateFileException: 
    Duplicate files copied in APK META-INF/LICENSE 
[Solution](http://stackoverflow.com/questions/37586800/android-gradle-duplicate-files-copied-in-apk-meta-inf-license-txt)

	

## Help and Support

For more support visit [CIDaaS Support](http://support.cidaas.com/en/support/home)


