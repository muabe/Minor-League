# GenieWorks SDK
GenieWorks에서 제공하는 genie-sdk.aar파일을 lib 폴더 안에  복사하여 넣습니다.<br>
GenieSDK를 사용하기 위해선 Build설정과 AndroidManifest 설정이 필요합니다.<br>
> **Note** <br>
현시점 최신버전인 android studio 3.1.1과 gradle4.4 기준으로 작성 되었습니다. 

<br>

## Build 설정
genie-sdk.aar파일을 프로젝트에 import하기 위한 build 설정입니다.<br>
gradle.bulid 파일에 아래와 같이 repository와 dependency를 지정합니다.   

```
repositories{	
    flatDir {
        dirs 'libs'
    }
}

dependencies {
    compile 'net.genieworks.sdk:genie-sdk@aar'
}

```


<br>

## AndroidManifest 설정
- **Permission**<br>
GenieSDK는 다음과 같은 권한이 필요 합니다.<br>
AndroidManifest 파일에 uses-permission를 설정해 줍니다.

```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name= "android.permission.ACCESS_NETWORK_STATE"/>
<uses-permission android:name= "android.permission.ACCESS_WIFI_STATE"/>
<uses-permission android:name= "android.permission.WRITE_EXTERNAL_STORAGE"/>
<uses-permission android:name= "android.permission.GET_ACCOUNTS"/>
<uses-permission android:name= "android.permission.READ_PHONE_STATE"/>
```

<br>

- **Activity**<br>
GenieSDK에서 제공하는 2개의 Activity와 meta-data 정보를<br>
application 태그 아래 선언해 줍니다.

```xml
<application>
        <meta-data
            android:name="partner_id"
            android:value="01647-20180412-390" />
        <meta-data
            android:name="title_bar_color"
            android:value="default" />

        <activity android:name="net.genieworks.sdk.GenieActivity"
                  android:theme="@android:style/Theme.Holo.Light.NoActionBar">

        </activity>

        <activity
            android:name="net.genieworks.sdk.GenieDetailActivity"
            android:theme="@android:style/Theme.Holo.Light.NoActionBar">

            <intent-filter>
                <action android:name="android.intent.action.VIEW" />
                <category android:name="android.intent.category.DEFAULT" />
                <category android:name="android.intent.category.BROWSABLE" />

                <data
                    android:host="banner"
                    android:scheme="geniesdk" />
            </intent-filter>
        </activity>
<application>  
```

<br>

## Sample Code
 아래와 같이 5개의 파미라터를 intent에 전달하고 startActivity 실행합니다.
```java
String accessToken = "삼성에게 전달 받은 값";
String serviceId = "삼성에게 전달 받은 값";
String guidHash = "삼성에게 전달 받은 값";
String didHash = "삼성에게 전달 받은 값";
String adid = "삼성에게 전달 받은 값 (파라미터 이름은 바뀔 수 있음)";
String host = "stg"; // or "prd"

Intent intent = new Intent(MainActivity.this, GenieActivity.class);
intent.putExtra("host",host);
intent.putExtra("accessToken",accessToken);
intent.putExtra("serviceId",serviceId);
intent.putExtra("guidHash",guidHash);
intent.putExtra("didHash",didHash);
intent.putExtra("adid",adid);
startActivity(intent);
```
