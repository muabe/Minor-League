## 모션 우선순위
1. 같은 성향의 액션의 경우 동시(드래그 상하좌우 등)
2. 서로다른 성향의 액션의 경우 Input 순서대로(드래그, 사운드 등)
## 합성에서 cache란
- 프레임이 빠르게 발생되는 액션에서 pull load 검색을 여러번 하지 않기 위함
- 모션의 점유를 유지하기 위함
- 액션이 발생되면 처음부터 검색하지 않고 cache된 모션부터 다시 체크, 모션이 발생되지 않으면 cache된 모션을 기준으로 검색 시작

<br><br>

## Player 요건
- Motion 한개의 합성된 play가 있다.
- 여러개의 play가 합성될수 있어야한다.
- 합성에는 동시성과, 순서가 있어야 한다.
- 동시성은 with, 순서는 next로 구분하여 순차적으로 정렬한다.
- 합성된 묶음의 play(play그룹)은 또 다시 합성이 이루어 진다.
- 합성된 play 그룹은 또다시 하나의 play가 된다. 

<br><br>

## 기존 Combination 엔진으로 가능한가?
- with 합성의 경우 or로 가능하지만 next의 경우는 불가능
- next는 순서를 가지고 있으므로 Combination의 리스트로 표현 할수 있음
- index를 가지고 있는 Combination Set이 필요
  
<br><br>

## 그러면 next 합성은 가능한가?
- 어렵지만 가능해보임

## next 합성이란
- 순서가 있는 연속된 모션의 묶임
- 순서는 모션이 끝났을때 다음 모션으로 연결됨
- 현재 상태에서 next합성시 cache가 될까? (반드시 해야함)


## next 합성 원리
- next로 묶인 순서대로 
- 두개를 동시에 하는구간, 하나만 하는구간

## Action의 설계
- Action module : 외부 라이브러리와 브릿지 역할, 입력 받기 위한 비동기 이벤트등을 직접 구현
- priority : combination에 필요한 우선 순위
- Point : 모션의 값을 나타내며 Player와 소통될 수 있는 화폐 같은 단위
- PlugIn : 각 액션에 맞는 priority 구현하고 point를 환산 
- Motion : PlugIn을 가지고 있고 이들을 조합
- Propose : Action 모듈을 등록하고 Action 모듈에 PlugIn을 연결
> motion(conbination) < plugIn(priority) < Point
> Propose < Motion, Action module

## Play 모듈
- Play Module : 외부 라이브러리와 브릿지 역할, 동기 무브먼트를 직접 구현
- priority : combination에 필요한 우선 순위 
- Point : 
- PlugIn : priority를 각 Player에 맞게 구현
- Motion : PlugIn을 가지고 있고 이들을 조합
- Propose : Motion에서 Player를 꺼내어
