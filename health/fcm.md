# PUSH
push메세지는 서버, 모바일 두 분야에서 개발이 이루어지는 만큼 개발자들간에 소통이 쉽지 않고 혼동이 많이 생기는 부분입니다. <br> 
잘되던 push 메세지가 시간이 지나면 오지않는 경우,  android와 iso 둘중 하나만 간다던지 하는일을 겪에 되는데<br>
이런일은 개발자들이 FCM에 대한 이해가 부족하기 때문입니다.<br>
firebase cloud message를 기반으로 push 메세지에 관련해여 알아야할 기본적인 사항들에 대해 정리해 보았습니다.<br>

## Fcm 메세지 유형 3가지
### 1. 알림 메세지 : SDK(OS)에서 자동 처리됨
 - 기기에서 자동으로 노티등 알림을 처리
 - 서버에서 직접 노티, 사운드 등 직적 지정함
 - 앱이 떠 있을땐 OS별로 각각 아래를 호출함
   - android onMessageReceived()
   -  iOS에서는 didReceiveRemoteNotification:
   -  자바스크립트에서는 onMessage()  
>  note 프로토컬 종류<br> https://firebase.google.com/docs/reference/fcm/rest/v1/projects.messages?hl=ko#Notification

sample json
```json
{
  "message":{
    "token":"bk3RNwTe3H0:CI2k_HHwgIpoDKCIZvvDMExUdFQ3P1...",
    "notification":{
      "title":"Portugal vs. Denmark",
      "body":"great match!"
    }
  }
}
```

### 2. 데이터 메세지 : 클라이언트 앱에서 처리
 - 개발자가 직접 처리하고 키-값 형태
 - Data에 키-값을 넣어서 커스텀하게 처리
sample json
```json 
{
  "message":{
    "token":"bk3RNwTe3H0:CI2k_HHwgIpoDKCIZvvDMExUdFQ3P1...",
    "data":{
      "title" : "Mario",
      "body" : "great match!",
      "Room" : "PortugalVSDenmark"
    }
  }
}
```

### 3. 복합 메세지
 - 선택사항으로 데이터를 알림 메세지를 보낼수 있음
 - 노티에 관해서는 서버에서 처리
 - Data는 앱에서 처리
 - 백그라운드 상태일때 사용자가 알림을 탭한 경우에만 앱이 데이터 처리
 - 한마디로 사운드등 이런것은 서버에서 처리하고 이동하는 링크 등을 데이터를 이용해 앱에서 처리

sample json
```json
{
  "message":{
    "token":"bk3RNwTe3H0:CI2k_HHwgIpoDKCIZvvDMExUdFQ3P1...",
    "notification":{
      "title":"Portugal vs. Denmark",
      "body":"great match!"
    },
    "data" : {
      "Nick" : "Mario",
      "Room" : "PortugalVSDenmark"
    }
  }
}
```

### FCM 키 유형 2가지
Fcm 데이터 키를 선정하는데 있어서 공통키와 커스텀키가 있습니다.<br>
이는 서버에서 내려주는 JOSN 포멧을 실제 FCM에서는 플랫폼마다 다르기 내려주기 때문입니다.
## 1. 공통키 : 모든 플랫폼을 지원하지 위한 일종에 표준키를 구글에서 미리 정해줌
 - ex)message.notification.title, message.notification.body, message.data
 - 공통키를 사용하는 이유는 플랫폼 마다 값을 다르게 해석할수 있단다.(같은키에 다른 해석)
   예를들어 우리만 쓰는 push면 상관이 없겠지만 push API 서비스를 한다고 했을때 각플랫폼마다
   키에 대한 해석이 달라질수 있다
   또 용어가 달라 메세지가 정의를 각갂 따로해야할때 android(acivity), ios(category)
## 2. 플랫폼별 키(커스텀 키) : 공통 이외의 데이터나 플랫폼별 따로 데이터를 달리줘야할 때
 - IOS를 제외해고 보내야 한다던지 할때
 
sample json
```json 
{
  "message":{
     "token":"bk3RNwTe3H0:CI2k_HHwgIpoDKCIZvvDMExUdFQ3P1...",
     "notification":{
       "title":"Match update",
       "body":"Arsenal goal in added time, score is now 3-0"
     },
     "android":{
       "ttl":"86400s",
       "notification"{
         "click_action":"OPEN_ACTIVITY_1"
       }
     },
     "apns": {
       "headers": {
         "apns-priority": "5",
       },
       "payload": {
         "aps": {
           "category": "NEW_MESSAGE_CATEGORY"
         }
       }
     },
     "webpush":{
       "headers":{
         "TTL":"86400"
       }
     }
   }
 }
```

## 전송 옵션 2가지
 모바일은 PC환경과 달리 예측하기 힘든 상황들이 있습니다.(엘레베이터, 베터리부족 등등)<br>
 디바이스가 메세지를 받을수 없는 상황일때(폰이 꺼진상태 등)<br>
 FCM에서 메세지를 누적하고 있다가 받을수 있는 상황으로 되었을때 처리하는 방법은 아래와 같습니다.<br>
 > NOTE FCM은 전송 순서를보장하지 않는다.
### 1.비축소형 메시지
  - 안드로이드의 경우 100개까지 fcm에서 저장하고 있으며 100개가 넘으면 삭제
  - 삭제되면 특수한 메세지를 보냄
  - 특수한 메세지를 받았을때 서버에 조회를 해서 데이터를 동기화하는 로직을 추가해야함
### 2.축소형 메시지
 - 마지막에 오는 하나만 보내주기
 - collapse_key 라는 키를 설정해야하며 같은 키일경우 덮어씀 
 - 총 4개를 서로다른 키로 최신화를 할수 있음
 - 4개가 넘어버리면 어떤것이 지워질지 보장하지 않음

### 메시지 우선순위 설정
메세지 우선순위는 priority로 설정이 가능하며 default는 아래와 같습니다.
 - 보통 우선순위
 - 일반적이 메시지
 - 절전모드일때 전송이 지연되됨
