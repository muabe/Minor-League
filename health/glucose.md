# ISens SDK 
Isens SDK alpha <br>
실행 환경 : Android 4.4 이상(현재 5.0 이상 버전)<br>
개발 언어 : Java, Kotlin(릴리즈 이후 지원)
<br>

## SDK Import
Android Gradle 파일에 아래 코드를 추가하여 SDK Import할 수 있습니다
```
  compile 'com.xxxx.xxx.xx'
```

<br>

## SDK 개발 가이드

### 1.SDK 객체 생성 및 초기화
```java
IsensSDK sdk = new IsensSDK(Context, IsensBleListener);
        sdk.enableAutoBluetooth(this); // 자동 블루투스 활성화 옵션
        sdk.regist();
```

IsensSDK 객체를 생성하고 regist()를 합니다.
> Note : Activit의 onDestroy()에서 unreist()를 반드시 명시 해야합니다.

<br>

### 2.블루투스 디바이스 검색

```java
  sdk.startScan(timeout);
```
해당 결과는 onScanResult로 콜백 됩니다.

<br>

### 3.페어링
페어링이 필요한 경우 paring()으로 페어링 할수 있습니다.
```java
  sdk.paring(BluetoothDevice);
```
페이링이 성공하면 onBonded함수로 결과가 콜백 됩니다.

<br>

### 4.Connection
```java
  sdk.connect(BluetoothDevice);
```
connection이 성공하면 onConnected로 결과가 콜백 됩니다.

<br>

### 5.혈당 수치 리스트
```java
  sdk.getClucose(gatt, sequence);
```
해당 결과는 onClucoseResult로 콜백 됩니다.

<br>

### 예외 처리
모든 에러는 onException으로 보내집니다<br>
해당 내용은 Exception Messgae를 참조하시기 바랍니다.
<br>
