# About GS FRESH MALL
---
GS Fresh Mall은 체인 형식으로 운영하는 한국의 기업형 Hypermarket 중 하나 입니다.  
고객에게 신선하고 다양한 상품을 온라인, 오프라인으로 어디서나 편리하게 쇼핑할 수 있는 서비스를 제공합니다. 

<br>

우리는 모바일 환경에서도 쇼핑몰을 이용할 수 있게 모바일 App를 운영하고 있으며,  
빠르고 편리한 사용자 경험을 위해 기존에 웹 방식에서 Native 방식인 Flutter로 개발을 진행하고 있습니다.

<br>

# GS Fresh App 개요
GS FRESH Project는 기존에 웹 App를 Flutter로 대체하는 project 입니다. 따라서 기존 웹 화면을 개발에 참고 할수 있습니다.  


<br>


아래는 웹으로 개발된 Gs Fresh Mall 링크 입니다.  
- GS Fresh Web : https://m.gsfresh.com/

<br>

### 개발 환경
 - Dart :  2.15.0 ~ 3.0.0
 - Flutter : 2.10.4 ~ 3.0.0
 - Android
   - Min version : 21
   - Taget version : 31
   - Jvm : 1.8
 - iOS :    
 - IDE : Visual Studio Code, Android Studio

<br>
<br>


## System Architecture
---
먼저 전체적인 시스템 구성에 대해 알아 봅니다. GS Fresh Project는 Clean Architecture의 구조로 설계 되었습니다. 

![clean_architecture](https://github.com/muabe/Minor-League/blob/master/offshore/image/Clean_Architecture.png)

<br>

Clean Architecture는 Presentation, Domain, Data 3개의 계층으로 분리 되어 있습니다.  
계층은 각각의 역할을 가지며 Domain을 통하여  Data <-> Domain <-> Presentation 제어의 흐름이 이루 집니다. 
- Data Layer : Rest API, Local storage 등 Data access 
- Domain Layer : Business logic, 모델정의 
- Presiontation Layer : Widget 등 UI 
> 역할을 명확히 나눔으로써 수정 범위를 최소화하고 Project의 안정성을 유지하기 위함 입니다.

<br>

초기 개발에서는 GS에서 Data Layer와 Domain Layer를 개발하여 제공할 예정입니다.  
따라서 Presentation Layer를 우선적으로 집중 하면 됩니다. 
하지만 전체적인 구조를 이해하면 개발에 큰 도움이 주기 때문에  
Architecture의 자세한 내용은 아래 추가로 기술하였으니 확인 바랍니다.  

- [GS Fresh Mobile Software Architecture](https://github.com/muabe/Minor-League/blob/master/offshore/MobileSoftwareArchitectureDesign2022_1Q)

<br>

## Project 진행 사항
우리는 Web으로 구성된 Gs Fresh Mall을 Flutter로 변환하고 있습니다.  
현재 main home화면이 Flutter로 개발되어 있고 나머지는 WebView로 이루어져 있습니다.  

<dl><dt>• Flutter로 개발된 Home 화면</dt></dl>

|![home](https://github.com/muabe/Minor-League/blob/master/offshore/image/home.png)|
|---|

<br>

## 개발 범위
#### WebView를 Flutter로 변환하는 과정은 단계별로 진행 됩니다.
<dl><dt>단계별 개발 범위</dt></dl>

- 매장(상단 메뉴) 화면 개발
- 카테고리(사이드 메뉴) 매장 개발
- 상품 상세 화면 개발
> **Note :** 개발 진행 단계는 변경되거나 세분화 될수 있습니다.

<br>

앞으로 개발해야할 내용은 아래와 같이 <u>상단 메뉴</u>와 <u>사이드 메뉴</u>의 2depth 화면 입니다.
<dl><dt>• WebView 상단 메뉴화면 예시</dt></dl>

|![menu1](https://github.com/muabe/Minor-League/blob/master/offshore/image/menu1.png)|![menu2](https://github.com/muabe/Minor-League/blob/master/offshore/image/menu2.png)|![menu3](https://github.com/muabe/Minor-League/blob/master/offshore/image/side.png)|
|---|---|---|

<br><br><br>

# 개발 가이드
---
당분간 Data, Domain Layer는 GS에서 개발하여 제공합니다.  
자세하게 설명드리면, API와 데이터 관련 Business logic을 Domain layer의 Usercase를 GS에서 제공합니다.  
여러분은 제공 받은 것을 토대로 Persentaion Layer의 UI를 구현하시면 됩니다.  

<br>

UI는 기본적으로 BLOC 패턴(Presenter)을 사용하여 앱의 상태를 관리합니다.  
BLOC 패턴과 연관하여 업무 분담에 대해서는 아래 도식을 참고하시기 바랍니다.

<br>

![floder](https://github.com/muabe/Minor-League/blob/master/offshore/image/support.png)

<br>

## 선행 학습  
BLoC 패턴은 여러가지 라이브러리를 조합하여 구성되어 있음으로
라이브러리에 대한 선행 학습이 필요하며 개발에 앞써 반드시 숙지하시기 바랍니다.

<dl><dt>선행 학습 목록</dt></dl>

 - [flutter_bloc](https://pub.dev/packages/flutter_bloc) : BLoC 패턴 FrameWork
 - [Freezed](https://pub.dev/packages/freezed) : 클래스 상태 매칭을 위한 Sealed Class
 - [flutter_hooks](https://pub.dev/packages/flutter_hooks) : 라이프 사이클 상태 관리
 - [get_it](https://pub.dev/packages/get_it) : 객체관리
 - [flutter_svg]() : icon 폰트로 정의(따로 문서 가이드 필요)
 - [cached_network_image](https://pub.dev/packages/cached_network_image) : Image Disk cache

<br>

## 폴더 구조
### ◼︎ Presentation layer 폴더 구조
Presentation layer는 lib 폴더에서 시작합니다.

```
├── [lib] 
│   ├── [app]         // Application에 구성에 필요한 기능 모음
│   ├── [base]        // base classes
│   ├── [helper]      // Helper classes
│   ├── [hooks]       // Custom hook class
│   ├── [screens]     // App views implements 
│   ├── [utils]       // util classes
│   ├── [widgets]     // common widget
│   ├── injector.dart // data, domain layer instance management
│   └── main.dart     // starting point dart main file 
....   
```

<br>

<dl><dt> Presentation layer 예시 </dt></dl>

![floder](https://github.com/muabe/Personal/blob/main/doc/all_folder.png)

<br>

### ◼︎ Screen 폴더 구조
Presentation Layer의 UI 구현부는 Screens에서 시작합니다.  
1. [screens]에 [Product screen] 별로 디렉토리 구성
1. screen 전체 구성을 product_screen.dart에 file 구현
1. screen 디렉토리 별로 개별적인 bloc, pages, widgets 디렉토리를 가짐

```
├── [lib] //root 
│   ├── [screens]
│   │   ├── [screen] //screen product
│   │   │   ├── [bloc]    //bloc source folder
│   │   │   ├── [pages]   //sub pages
│   │   │   ├── [widgets] //Conpoent in screen
│   │   │   └── product_screen.dart  //UI screen source file
│   │   ├ ....   
│   │   
....   
```
> Screen에 직접적으로 연관된 source코드들은(bloc, pages, widget) 직관적으로 파악할 수 있도록 같은 디렉토리에 위치합니다.

<br>

<dl><dt>• intro screen 예시</dt></dl>

![floder](https://github.com/muabe/Minor-League/blob/master/offshore/image/screen_folder.png)

<br>

###  ◼︎ bloc 폴더 구조
bloc 폴더는 bloc에 관련 파일들을 넣습니다.
1. BLoC implement file
2. BLoC event Sealed class file
3. BLoC state Sealed class file

```bash
├── [lib] 
│   ├── [screens]
│   │   ├── [screen] 
│   │   │   ├── [bloc]  //bloc source folder
│   │   │   │   ├── product_bloc.dart         // BLoC implement file
│   │   │   │   ├── product_bloc.freezed.dart // freezed code generation file
│   │   │   │   ├── product_event.dart        // BLoC event Sealed class file
│   │   │   │   └── product_state.dart        // BLoC state Sealed class file
│   │   │   ├── [pages]   
│   │   │   ├── [widgets] 
│   │   ├ ....   
│   │   
....   
```

<br>

<dl><dt>• intro bloc 예시</dt></dl>

![floder](https://github.com/muabe/Minor-League/blob/master/offshore/image/bloc_folder.png)

<br>

### ◼︎ pages 폴더 구조
pages 폴더는 screen에서 사용되는 page들을 모아 놓은 폴더 입니다.

<br>

#### screen과 page의 차이
> 상위 그룹의 view를 screen이라 하며 screen안에 속하는 view들을 page라 합니다.
> 상위 그룹 view라는 것은 화면이 전환이 전환될때 history를 가지는 view를 말하며  
> 그렇지 않는 view는 screen의 page로 편입 됩니다.  
> 즉 route를 이용해 화면이 이동되는 view는 Screen이며 그외는 screen안에 page로 분리 됩니다.  

```
├── [lib] 
│   ├── [screens]
│   │   ├── [screen] 
│   │   │   ├── [bloc]  
│   │   │   ├── [pages] //page source folder  
│   │   │   │   ├── product1.page.dart // product page implement file
│   │   │   │   ├── product2.page.dart
│   │   │   │   └── product3.page.dart
│   │   │   ├── [widgets] 
│   │   │   └── product_screen.dart  //UI screen source file
│   │   ├ ....   
│   │   
....   
```

<br>

<dl><dt>• main page 예시</dt></dl>

![floder](https://github.com/muabe/Minor-League/blob/master/offshore/image/page_folder.png)

<br>

### ◼︎ widgets 폴더 구조
widgets폴더는 screen 내에 사용되는 widget들의 모음 입니다. 

```
├── [lib] 
│   ├── [screens]
│   │   ├── [screen] 
│   │   │   ├── [bloc]  
│   │   │   ├── [pages] 
│   │   │   ├── [widgets] //widget source folder  
│   │   │   │   ├── xxxx1.widget.dart // widget implement file
│   │   │   │   ├── xxxx2.widget.dart
│   │   │   │   └── xxxx3.widget.dart
│   │   ├ ....   
│   │   
....   
```

<br>

<dl><dt>• main widgets 예시</dt></dl>

![floder](https://github.com/muabe/Minor-League/blob/master/offshore/image/widgets_folder.png)

<br>

## Naming conventions
```
 - xxxx.screen.dart // screen implement file
 - xxxx.page        // page implement file
 - xxxx_bloc.dart   // BLoC implement file
 - xxxx_evnet.dart  // BLoC event Sealed class file
 - xxxx_state.dart  // BLoC state Sealed class file
 - xxxx_widget.dart // widget implement file
 - xxxx.hook.dart   // custom hook file
 - xxxx.helper.dart // helper file
 - xxxx.utils.dart  // utils file
``` 

<br>
<br>

## BLoC
presentation과 business logic layer를 분리하기 위해 BLoC 패턴을 사용하고 있습니다.  
우리는 flutter_bloc와 함께 Freezed와 flutter_hooks 라이브러리를 사용하여 bloc를 구성하고 있습니다.  

<br>

### ◼︎ Freezed code generation
state와 event는 각각 화면과, BloC간의 상태를 전달하는 역할을 합니다.  
boilerplate code를 줄이기 위해 Freezed를 사용하여 code generation 합니다.  
아래는 Freezed를 사용해 state와 event의 구성과 code generation에 대한 내용을 자세히 설명한 포스팅 입니다.  
> Link : [bloc와 freezed 사용 예제](https://medium.com/@morning-stars/flutter-freezed-bloc-7-2-0-without-boilerplate-99fe6051f8d)

<br>

BLoC의 구성은 bloc,state,event 세가지로 분리되어 파일로 나뉘게 됩니다.
- Bloc 구현체
- state Sealed class
- event Sealed class

```
[bloc]  //bloc source folder
├── xxxx_bloc.dart         // BLoC implement file
├── xxxx_bloc.freezed.dart // Freezed code generation file      
├── xxxx_event.dart        // BLoC event Sealed class file
└── xxxx_state.dart        // BLoC state Sealed class file
```

> ### Sealed Class 란
> Sealed Class란 추상 클래스로, 상속받는 자식 클래스의 종류를 제한하는 특성을 가집니다.  
> 즉 컴파일 단계에서 컴파일러가 Sealed Class의 자식 클래스가 어떤 것이 있는 지 알 수 있도록 해주는 것입니다.
> 하나의 클래스에서 만들어지는 인스턴스의 종류가 여러개 일 수 있고, 
> 모든 종류를 컴파일러가 알도록 한다고 이해하면 됩니다
> 프로젝트의 코드를 예로 들어보겠습니다.
```dart
 @freezed
 class ProductState with _$ProductState {
   const factory ProductState.initial() = _Initial;

   const factory ProductState.loading() = _Loading;

   const factory ProductState.loaded({required ProductInfo ProductInfos}) = _Loaded;

   const factory ProductState.failed(Exception exception) = _Failed;
 }
  ```

> 위와 같이 상태를 정의하면, 화면 단에서 `when` 을 통해 처리해야 하는 상태를 컴파일 단계에서 미리 알 수 있습니다.  
> 프로젝트에선 freezed annotation을 통해 sealed class를 만들어 사용하고 있습니다.

<br>

### ◼︎ state.dart
state는 screen/page에 영향을 주게되고 view를 결정하는 상태를 의미합니다.
- 데이터 로딩 전/후
- 로그인 전/후

대표적으로 Server에서 rest Api와 통신하는 동안에 view에 표시할 상태를 사례로 들수 있습니다.
- initial : Api를 접속 하기전 상태
- loading : Api를 호출중인 상태
- loaded : Api와 통신 성공인 상태
- failed : Api와 통신 실패인 상태

state.dart는 Freezed를 사용해 sealed class로 작성 합니다.
```dart
// product_state.dart file

part of 'product_bloc.dart';

@freezed
class ProductState with _$ProductState {
  const factory ProductState.initial() = _Initial;

  const factory ProductState.loading() = _Loading;

  const factory ProductState.loaded({required ProductData data}) = _Loaded;

  const factory ProductState.failed(Exception exception) = _Failed;
}
```

<br>

### ◼︎ event.dart
event는 bloc의 상태 변화를 호출하기 위한 key와 같습니다.  
event에 데이터를 실어 bloc에 전달하고 Business logic을 수행 합니다.
bloc는 수행된 결과를 바탕으로 화면의 상태를 변경합니다.
event.dart는 state.dart와 마찬가지로 Freezed를 사용해 sealed class로 작성 합니다.

<br>

아래는 data를 load하기 위한 event class의 예시 입니다.
```dart
// product_event.dart file

part of 'product_bloc.dart';

@freezed
class ProductEvent with _$ProductEvent {
  const factory ProductEvent.loadData() = _loadData;
}
```

<br>

### ◼︎ bloc.dart
bloc는 event를 받아 그에 상응하는 logic을 수행하고 화면에 변경된 상태를 전달합니다. 
우리는 bloc 라이브러리 중 [flutter_bloc](https://pub.dev/packages/flutter_bloc) 라이브러리를 사용하여 Bloc를 구현합니다.
Event-Driven을 위한 구조를 지향하기 때문에 Presenter에 Cubit을 사용하지 않고 Bloc를 상속하여 만듭니다.  
또한 화면은 BlocProvider와 BlocBuilder를 사용하여 state를 정의 합니다.  
bloc를 구현 과정을 아래에 기술하였으니 참고하시기 바랍니다.

- create product_bloc.dart file
 - state.dart, event.dart를 'part'로 코드를 import합니다.  
 - freezed file을(product_bloc.freezed.dart) 여기서 생성 합니다.
 
```dart
// product_bloc.dart file

part 'product_state.dart';
part 'product_event.dart';
part 'product_bloc.freezed.dart';
```

- Bloc를 상속받아 bloc 클래스 생성
```dart
class ProductBloc extends Bloc<ProductEvent, ProductState> 
```

- 생성자  
 - data 조회기능을 제공하는 usecase를 생성자에서 받습니다. 
 - on<ProductEvent>, generic에 event를 지정하고 상태로직을 구현한 _load 메소드와 연결합니다.
 
```dart
final ProductUsecase _productUsecase; //data 조회하는 usecase

CartBloc(this._productUsecase) : super(const ProductState.initial()) { //usecase를 생성자로 받음
  on<ProductEvent>(_load);
}
```

- 상태 로직 구현
  - when을 사용해 event.loadData에 대한 상태 로직을 구현합니다.
  - usecase의 데이터를 조회하여 상태변화와(loaded) 함께 화면에 데이터를 전달합니다.
```dart
Future<void> _load(ProductEvent event, Emitter<ProductState> emit) async {
  await event.when(
    loadData: () async {
      try {
        emit(ProductState.loading()); //데이터를 조회하기전 loading 상태         
        await ProductData data = await _productUsecase.getData(); //usecase에 데이터 조회         
        emit(ProductState.loaded(result));
      } on Exception catch (e) {
        emit(ProductState.failed(e));
      }
    },
  );
}
```
 
<br> 
 
모든 event는 when 메소드를 통해 매칭되어야 합니다(의도하지 않은 에러를 잡기 위한 이유가 큽니다!)  
event가 변경이나 추가가 되면 event를 가져다 쓰는 모든 곳의 코드가 바뀌어야 합니다.  
그래서 어떤 코드를 수정해야 하는지 알기 쉽습니다.  

<br>

 <dl><dt>○ product_bloc.dart 전체 소스코드</dt></dl>
 
• product_state.dart
``` dart
 part of 'product_bloc.dart';

@freezed
class ProductState with _$ProductState {
  const factory ProductState.initial() = _Initial;
  const factory ProductState.loading() = _Loading;
  const factory ProductState.loaded({required ProductData data}) = _Loaded;
  const factory ProductState.failed(Exception exception) = _Failed;
}
```
 
• product_event.dart 
```dart
part of 'product_bloc.dart';

@freezed
class ProductEvent with _$ProductEvent {
 const factory ProductEvent.loadData() = _loadData;
}
``` 
 
• product_bloc.dart file
```dart
part 'product_state.dart';
part 'product_event.dart';
part 'product_bloc.freezed.dart';
 
class ProductBloc extends Bloc<ProductEvent, ProductState>{
  final ProductUsecase _productUsecase; //data 조회하는 usecase

  CartBloc(this._productUsecase) : super(const ProductState.initial()) { //usecase를 생성자로 받음
    on<ProductEvent>(_load);
  }
 
  Future<void> _load(ProductEvent event, Emitter<ProductState> emit) async {
    await event.when(
      loadData: () async {
        try {
          emit(ProductState.loading()); //데이터를 조회하기전 loading 상태         
          await ProductData data = await _productUsecase.getData(); //usecase에 데이터 조회         
          emit(ProductState.loaded(result));
        } on Exception catch (e) {
          emit(ProductState.failed(e));
        }
      },
    );
  }
 
}
``` 
 
 <br>
 
### ◼︎ injector.dart
bloc를 구현 하였다면 injector를 통해 객체를 등록하고 사용해야합니다. 
injector는 내부적으로 [GetIt](https://pub.dev/packages/get_it)을 사용해 객체를 관리합니다.  
객체를 등록하는 injector.dart file은 lib/injector.dart 경로에 있습니다.  
```
├── [lib] 
│   ├── [app]
│   ├── [helper] 
│   ├── .....
│   └── injector.dart 
 
``` 

_presentationLayer() 메소드에서 Bloc 객체를 등록하고 injector통해 UI에서 객체를 사용합니다.
```dart
void _presentationLayer() {
  factory<NavigationBloc>(() => NavigationBloc(injector()));
  ...
  factory<ProductBloc>(() => ProductBloc(injector()));
} 
``` 
 
<br>
<br> 
 
## UI 
지금부터 bloc를 연결되는 ui를 구성합니다. UI는 StatelessWidget만 사용하고 StatefulWidget은 사용하지 않습니다.  
Widget의 생명주기에 때른 제어가 필요할 경우 [HookWidget](https://pub.dev/packages/flutter_hooks)사용하여 StatefulWidget을 대체할 수 있습니다.  

<br>
 
상태에 대한 UI 변경은 [flutter_bloc](https://pub.dev/packages/flutter_bloc)를 사용하여 관리합니다. 
Event-Driven을 위해 BlocProvider와 BlocBuilder 사용하여 화면을 구현합니다.
아래는 UI에 구성하는 소스코드 기본형식입니다.
 
```dart
class ProductScreen extends StatelessWidget {

   @override
   Widget build(BuildContext context) {
     return BlocProvider<PreparationBloc>( //사용할 PreparationBloc 지정
       create: (_) => injector(),  // injector에서 등록한 PreparationBloc 객체
       child: const Scaffold(
         body: _ProductScreen(),
       ),
     );
   }
}
 
class _ProductScreen extends StatelessWidget{
  @override
  Widget build(BuildContext context) {
    return BlocBuilder<PreparationBloc, ProductState>(
       builder: (context, state) {
         return state.when(
           initial: () {
             return InitialPage();
           },
           loading: () {
             return LoadingPage();
           },
           loaded: (ProductData data) {
             return LoadedPage();
           },
           failed: (e) {
             return ErrorPage(e)
           },
         );
       },
     );
   } 
}
```
모든 state는 when 메소드를 통해 화면과 매칭되어야 합니다(의도하지 않은 에러를 잡기 위한 이유가 큽니다!)  
state가 변경이나 추가가 되면 state를 가져다 쓰는 모든 곳의 코드가 바뀌어야 합니다.  
그래서 어떤 코드를 수정해야 하는지 알기 쉽습니다.  

<br> 
 
### ◼︎ HookWidget
우리는 StatefulWidget을 사용하지 않습니다.  
하지만 StatefulWidget의 initState에서 객체를 초기화 하거나 생명주기에 때른 제어가 필요할 때가 있습니다.  
이럴 경우 StatelessWidget 대신 HookWidget의 userEffect를 사용합니다.  
아래처럼 build method에 useEffect를 추가하여 객체를 초기화 하고 dispose 할수 있습니다.
 
```dart
class _ProductScreen extends HookWidget{ // HookWidget을 상속
  @override
  Widget build(BuildContext context) {
   useEffect(() {
          // initalize instance
 
        return () {
          // dispose instatance
        };
      });

 
 return BlocBuilder<PreparationBloc, ProductState>(
       builder: (context, state) {
         return state.when(
              // ... 중략
           },
         );
       },
     );
   } 
} 
```

<br>
 
### ◼︎ UI Event
view 자체에 변화를 주진 않지만 view가 알아야하는 이벤트가 필요할때가 있습니다.  
예를들어 데이터 조회시 '데이터가 없다'라는 팝업 노출이 필요한 시나리오가 존재할 때,   
데이터가 없다라는 이벤트에 대해서 화면이 알아야 할 필요가 있지만 해당 이벤트에 대한 상태, 화면이 따로 정의될 필요는 없습니다.  
 
<br>

이럴땐 별도로 UiEvent로 분리하여 처리 합니다. 그리고 Stream을 통해서 상태를 변경합니다. UiEvent구현은 다음과 같은 과정을 거치게 됩니다.
 - UiEvent sealed class 생성
 - bloc에서 UI상태를 전달할 Stream 생성하고 Stream을 통해 화면에 event를 보냄
 - 화면에선 useEffect에서 Stream을 받아 UI 상태를 처리

• UiEvent sealed class 생성
```dart
// product_event.dart file

@freezed
class ProductUIEvent with _$ProductUIEvent { //UiEvent 정의
  const factory ProductUIEvent.alter({required String msg}) = _alter;
}

@freezed
class ProductEvent with _$ProductEvent {
  const factory ProductEvent.loadData() = _loadData;
}
``` 

• bloc에서 UI상태를 전달할 Stream 생성하고 Stream을 통해 화면에 event를 보냄
```dart
// product_bloc.dart file
 
class ProductBloc extends Bloc<ProductEvent, ProductState>{

  // broadcast를 위해 StreamController 생성
  final _eventController = StreamController<PromotionUiEvent>.broadcast(); 
  Stream<PromotionUiEvent> get eventStream => _eventController.stream; 
 
  final ProductUsecase _productUsecase;

  CartBloc(this._productUsecase) : super(const ProductState.initial()) { //usecase를 생성자로 받음
    on<ProductEvent>(_load);
  }
 
  Future<void> _load(ProductEvent event, Emitter<ProductState> emit) async {
    await event.when(
      loadData: () async {
        try {
          emit(ProductState.loading()); 
          await ProductData data = await _productUsecase.getData(); 
          
          // 데이터가 없을때 팝업 출력
          if(data == null){
            _eventController.add(PromotionLoaded(popupInfo: popupInfo));; // UiEvent 호출
          }
 
          emit(ProductState.loaded(result));
          
        } on Exception catch (e) {
          emit(ProductState.failed(e));
        }
      },
    );
  }
}
``` 

• 화면에선 useEffect에서 bloc의 Stream을 연결하고 UI 상태를 처리
```dart
class _ProductScreen extends HookWidget{ 
  @override
  Widget build(BuildContext context) {
 
   useEffect(() {
 
        var stream = context.read<ProductUIEvent>().eventStream;
        var subscription = stream.listen( (event) {
            event.when(
              alter: (msg) {
                 // 팝업 처리           
                 showPopup(msg); 
              },
            );
          },
        );
 
        return () {
          subscription.cancel(); // Stream 종료
        };
      });
 
  ... 중략 
``` 
> Stream을 사용하기 때문에 반드시 종료 해주어야 합니다.

<br>
<br> 
