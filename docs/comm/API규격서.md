# API 규격서
모바일 개발에  오류를 최소화 하고 개발 속도를 향상시키기 위함

<br>

## Schema
 모든 API 요청은 HTTPS를 사용합니다.

<br>

### 데이터 송수신 시 Data Type 및 Content-Type
 - 데이터 보낼 때 <br>
  JSON : application/json;charset=UTF-8

 - 데이터 받을 때 <br>
  JSON : application/json;charset=UTF-8 <br>
  Form : application/x-www-form-urlencoded;charset=UTF-8 <br>
  File : multipart/formed-data <br>

Example
```
Content-Type: application/json;charset=UTF-8
Accept: application/json;charset=UTF-8
```
<br><br>

# Header
API 호출 시 필요한 Header 정보입니다.

## Requst

### 필수 Parameter 정보
 헤더 파라미터의 value는 형태는 대문자로 통일 한다.
 - channel : 추후 확장을 위한 업무 채널 ex) GLOBAL, B2B, B2C 
   > Default : NORMAL
 - Accept-Language : 사용자 언어 설정(KO, EN, ZH)
   > Default : KO
 - api-version : 사용할 api version
   > Default : 1.0
 - os : ANDROID, IOS, WEB 등 OS/플랫폼
   > Default : WEB
 
### 선택 Parameter 정보
 - userToken : 로그인 이후 API 호출 시 인증 정보로 사용함
 - device-id : 단말키


## Response HTTP Status Codes
API 통신 결과에 따른 상태 코드는 다음과 같습니다.

Code	Status
200 OK

정상/데이터 리턴
400 Bad Request

사용자 인증 필요 시 인증 안된 경우
401 Unauthorized

단말 호출 에러
404 Not found

리소스가 없는 경우
500 Internal Server Error

> 그외 업무상 오류 코드는 2000 이상 이며 Body 영역에서 따로 처리함

<br><br>

# Body
JSON 형태이며 Status와 contents로 구분합니다. <br>
Status는 업무 로직상 결과에 대한 정보입니다.<br>
contents는 결과에 대한 데이터(VO) 정보입니다. <br>

```json
{ 
   "timestamp":"2019-01-14 11:23:17",
   "status":200,
   "error":"",
   "message":"SUCESS",
   "trace":"",
   "path":"/api/v1/lotto2222222222222",  
   "contents" : {
     "User" : {
        "name" : "오재웅", 
        "age" : 28
     }
 }
}
```

<br>

## Status 영역 정의
 timestamp : 응답 시간 <br>
 status : 서버에서 정의한 결과 코드 <br>
 error : 서버에서 문자형 에러 코드 <br>
 message : 에러 메세지 <br>
 trace : 디버깅용 메세지 <br>
 path : 요청 URI 경로 <br>

- 요청 성공에 대한 결과
성공의 경우 추가로 contents 정보를 보내줍니다.
```json
{  
   "timestamp":"2019-01-14 11:23:17",
   "status":200,
   "error":"",
   "message":"SUCESS",
   "path":"/api/v1/lotto2222222222222",
   "contents" : {
     "User" : {
        "name" : "오재웅", 
        "age" : 28
     }
 }
}
```
 
- 실패에 대한 결과   
 실패의 경우 contents정보를 넣지 않습니다.
```json
{  
   "timestamp":"2019-01-14 11:23:17",
   "status":404,
   "error":"INVALID_DATA",
   "message":"입력 데이터 오류",
   "trace":"java.lang.NullPointerException",
   "path":"/api/v1/lotto2222222222222",

}
```

- 예외 응답 처리  
  비밀번호 미일치 같은 예외적인 결과를 보낼때는 2000 이상의 status를 보낸다.
```json
{  
   "timestamp":"2019-01-14 11:23:17",
   "status":2001,
   "error":"NO_LOGIN",
   "message":"아이디나 비밀번호가 일치하지 않습니다.",
   "trace":"",
   "path":"/api/v1/lotto2222222222222",

}
```   

<br>

## Contents 영역 정의
 서버에서 정의한 VO객체를 JSON 포멧으로 변환된 형태 <br>
 유지보수와 확장성, 에러율을 최소화 하기위해 VO객체는 메이븐에 배포화여 앱과 공유합니다.
 
 - Json형태는 VO개체의 클래스 명과 동일
```json
"contents" : {
     "User" : {
        "name" : "오재웅",
        "age" : 28
     }
   }
```

 - VO형태가 아닌 기본 자료형의 경우는 result 항목으로 표시  
 result에 boolean, inteager 등 기본 자료형 하나를 매핑한다. 
 ```json
 "contents" : {
     "result" : true
   }
```
   
<br>   
   
### Pagination
 갯수가 정해지지 않은 모든 리스트의 형태의 응답 객체는 다음 객체 형태를 사용합니다.
- page 현재 페이지
- size 요청 객체 수
- sort 정렬 조건, API별 제공 여부 따라 옵션으로 사용
- Array 조회하는 객체의 배열
- totalElements 총 객체 수
- numberOfElements 현재 페이지 내 객체 수
- totalPages 총 페이지 수
- number 요청 페이지 번호
- first 첫 페이지 여부
- last 마지막 페이지 여부

```json
"contents" : {
    "Page" : {
                 "last" : true,
                 "totalElements" : 5,
                 "totalPages" : 1,
                 "size" : 20,
                 "number" : 0,
                 "numberOfElements" : 5,
                 "first" : true,
                 "content" : [,,,, ]
    }
}
```
