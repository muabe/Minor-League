## Propose
ActionMoudle을 등록하고 OnActionListener와 연결해 주는 역할을 한다.<br>
Motion 합성을 Scan하여 우선순위 Motion을 추출한다.<br>
ActionMoudle에서 Action이 발생하면 Motion의 PlugIn을 통해 우선순위를 검사하는 필터링이 이루어진다.<br>
다시말해 Propose에서 ActionEvent가 발생하면 필터링과 우선순위를 비교해서 적합한 모션을 찾아준다.<br>
여기서 PlugIn은 해당 ActionEvent를 필터링하기 위한 하나의 도구이다.<br>
 - OnActionListener 가지고 있고 구현함
 - Motion을 필터링하여 적한할 ActionPlugIn에 OnAction을 통해 Event를 전달함
 - ActionModule에 OnActionListener를 객체를 전달하여 직접 이벤트를 받을 수 있게함
 - Motion 객체를 전달받고 객체를 가지고 있음 
 
### ActionModule과 PlugIn의 관계
ActionModule과 PlugIn은 직접적인 관계는 없다.<br>
ActionModule은 외부의 Input을 받아 오는일을 하고<br>
PlugIn은 Motion의 우선순위를 정하는 일을 하기 때문이다.<br> 
하지만 우선순위를 정하는데 있어서 이벤트 객체를 재료로 하고 있다.<br>
Propose는 Action이 발생할때 해당 이벤트 객체를 PlugIn에 전달하여 우선순위를 정한다.<br>
결론적으로 Propose는 PlugIn에 Action 이벤트를 전달하는 역할을 하고<br>
PlugIn을 통하여 Motion에 우선순위를 정한다.

### Motion
- Action-PlugIn을 가지고 있다.
- 여러개의 Motion들이 합성되어 다시 하나의 모션이 된다.
- Action-PlugIn을 통해서 합성된 Motion들 중 우선순위가 높은 모션하나를 추출한다.

### Action Module  
- Action은 터치, 사운드, 센서 등 Input으로 발생하는 모든 것을 말한다.
- Action이 발생(이벤트) 했음을 Propose에 알리기 위한 목적이 Action Module이다.
- Input으로 발생하는 모든 이벤트의 종류가 다르기 때문이 이를 통합하기 위한 하나의 인터페이스이다.
- 각 Action의 이벤트가 발생하면 OnActionListener의 onAction()을 호출하여 Propose에 이벤트가 발생했음을 알린다.
- Propose는 각기 다른 이벤트 객체에 대해서 필터링하여 Plug-In에 적합한 이벤트 객체를 전달한다.

### Action Plug-In
- PlugIn의 주된 목적은 Motion의 합성 우선순위를 정하는 것이다.
- 하나의 Action에 여러개의 PlugIn을 만들 수 있고 각 PlugIn에 따른 우선순위를 달리 정할 수 있다.<br>
  (예를들어 Touch action 하나에 left,right,up,down,회전 등 여러개의 PlugIn을 만들수 있다) 
- 하나의 Motion에 한개의 PlugIn만 넣을 수 있다.


