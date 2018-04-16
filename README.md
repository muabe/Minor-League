# GenieWorks SDK
lib 폴더 안에 genie-sdk.aar파일을 복사하여 넣습니다.

## Build 설정
gradle.bulid 파일에 아래와 같이 repository와 dependency를 지정합니다.
```
repositories{
    flatDir {
        dirs 'libs'
    }
}

dependencies {
    implementation 'net.genieworks.sdk:genie-sdk@aar'
}

```

<br>

## AndroidManifest 설정
- 퍼미션
```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name= "android.permission.ACCESS_NETWORK_STATE"/>
<uses-permission android:name= "android.permission.ACCESS_WIFI_STATE"/>
<uses-permission android:name= "android.permission.WRITE_EXTERNAL_STORAGE"/>
<uses-permission android:name= "android.permission.GET_ACCOUNTS"/>
<uses-permission android:name= "android.permission.READ_PHONE_STATE"/>
```

<br>

- Activity
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
  </activity>
  
<application>  
```

<br>

## SDK실행
```java
String accessToken = "삼성에게 전달 받은 값";
String serviceId = "삼성에게 전달 받은 값";
String guidHash = "삼성에게 전달 받은 값";
String didHash = "삼성에게 전달 받은 값";
String adid = "삼성에게 전달 받은 값 (파라미터 이름은 바뀔 수 있음)";

Intent intent = new Intent(MainActivity.this, GenieActivity.class);
intent.putExtra("accessToken",accessToken);
intent.putExtra("serviceId",serviceId);
intent.putExtra("guidHash",guidHash);
intent.putExtra("didHash",didHash);
intent.putExtra("adid",adid);
startActivity(intent);
```
