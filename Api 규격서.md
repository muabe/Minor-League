# API 규격서

<br>

## Schema
 모든 API 요청은 HTTPS를 사용합니다.

<br>

### 데이터 송수신 시 Data Type 및 Content-Type
 - 데이터 보낼 때
 > JSON : application/json;charset=UTF-8

 - 데이터 받을 때
 > JSON : application/json;charset=UTF-8 <br>
 > Form : application/x-www-form-urlencoded;charset=UTF-8 <br>
 > File : multipart/formed-data <br>

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
 - channel : 추후 확장을 위한 업무 채널 ex) Global, B2B, B2C
 - Accept-Language : 사용자 언어 설정(ko, en, zh)
 
### 선택 Parameter 정보
 - userToken : 로그인 이후 API 호출 시 인증 정보로 사용함


## Response HTTP Status Codes
API 통신 결과에 따른 상태 코드는 다음과 같습니다.

Code	Status
200 OK

정상/데이터 리턴
400 Bad Request

단말 호출 에러
401 Unauthorized

사용자 인증 필요 시 인증 안된 경우
404 Not found

리소스가 없는 경우
500 Internal Server Error

> 그외 업무상 오류 코드는 200 이며 Body 영역에서 따로 처리함

# Body
JSON 형태이며 Status와 contents로 구분합니다. <br>
Status는 업무 로직상 결과에 대한 정보입니다.<br>
contents는 결과에 대한 데이터(VO) 정보입니다. <br>

```
{
 {  
   "timestamp":"2019-01-14 11:23:17",
   "status":200,
   "error":"",
   "message":"",
   "path":"/api/v1/lotto2222222222222",
   
   "contents" : {
     "User" : {
        "name" : "오재웅"
        "age" : 28
     }
   }
 }
}
```
## Status 영역 정의
 timestamp : 응답 시간 <br>
 status : 서버에서 정의한 결과 코드 <br>
 error : 서버에서 문자형 에러 코드 <br>
 message : 에러 메세지 <br>
 path : 요청 URI 경로 <br>

- 요청 성공에 대한 결과
```
{  
   "timestamp":"2019-01-14 11:23:17",
   "status":200,
   "error":"",
   "message":"",
   "path":"/api/v1/lotto2222222222222"
}
```
 
 - 실패에 대한 결과   
```
{  
   "timestamp":"2019-01-14 11:23:17",
   "status":404,
   "error":"Not Found",
   "message":"No message available",
   "path":"/api/v1/lotto2222222222222",
   "BODY" : {}
}
```
   
 

Errors
오류 발생 시 다음 JSON 객체를 리턴합니다.

Response
Fields
Path	Type	Description
status

number

HTTP Status Code

code

String

헬스온 서비스 내 정의 오류 코드

message

String

사용자 안내 팝업용 메세지

trace

String

개발자 디버깅용 오류 메세지

Example
```json
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "status": "BAD_REQUEST",
  "code": "INVALID_DATA",
  "message": "입력 데이터 오류",
  "trace": ""
}
```

Pagination
페이징 가능한 목록의 응답 객체는 다음 객체 형태를 사용합니다.

GET /api/v4/resources?page=0&size=20&sort=name%2CASC&sort=date%2CDESC HTTP/1.1
Request
Request Parameters
Parameter	Description
page

현재 페이지

size

페이지 당 조회할 객체 수

sort

정렬 조건, API별 제공 여부 확인

Response
Fields
Path	Type	Description
content

Array

조회하는 객체의 배열

totalElements

number

총 객체 수

numberOfElements

number

현재 페이지 내 객체 수

totalPages

number

총 페이지 수

number

number

요청 페이지 번호

size

number

요청 객체 수

first

boolean

첫 페이지 여부

last

boolean

마지막 페이지 여부

sort

Array

정렬 정보

Example
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "content" : [ ... ],
  "last" : true,
  "totalElements" : 5,
  "totalPages" : 1,
  "size" : 20,
  "number" : 0,
  "sort" : [ {
    "direction" : "ASC",
    "property" : "name",
    "ignoreCase" : false,
    "nullHandling" : "NATIVE",
    "ascending" : true
  }, {
    "direction" : "DESC",
    "property" : "date",
    "ignoreCase" : false,
    "nullHandling" : "NATIVE",
    "ascending" : false
  } ],
  "numberOfElements" : 5,
  "first" : true
}

