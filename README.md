<div align="center">
<h1>🚙 현대자동차그룹 신차 출시 이벤트 🚙</h1>
</div>

### ✨ **목표**
: 현대자동차그룹의 신차 출시를 가정한 **대고객 이벤트 페이지** 설계 및 구현.
고객이 신차에 흥미를 느끼고, 지속적인 관심을 가질 수 있도록 다양한 **참여형 이벤트**를 제공.

### ✨ **필수 포함 기능**
- `선착순 + 추첨` 이벤트
-- 트래픽 폭주 상황을 가정하여, **다수의 사용자 요청을 안정적으로 처리**할 수 있도록 구현

- `어드민 기능`
-- 이벤트 오픈/종료 시간 설정
-- 선착순 당첨 인원 수 설정
-- 당첨자 추첨 및 당첨자 목록 조회

- `한줄 기대평 댓글 작성`, `공유하기(단축 URL)` 기능 등

🔗[이벤트 주제 및 필수 기능](https://www.notion.so/bside/19949efcabbf4ce59a8819eb381d4699?pvs=4)

<div align="center">
<h2>[4조] 실시간 웹소켓 기반 캐스퍼 레이싱 이벤트</h2>
</div>

> _**트래픽에 효과적으로 대응**하기 위해 **web-app**과 **socket-app**으로 분리하여 개발하였습니다._  
아래 내용은 **socket-app**을 중심으로 작성되었습니다.
🔗[web-app 프로젝트](https://github.com/softeerbootcamp4th/Team4-newCar-web-app-BE)

<!--
<img width="891" alt="캐스퍼레이싱" src="https://github.com/user-attachments/assets/fbcd0314-ec81-4211-b8e2-e878ec2d974a">
<img width="647" alt="실시간 기대평" src="https://github.com/user-attachments/assets/3d38f41a-2839-49c4-92fe-6033be142206">
-->

|![레이싱 게임 이미지](https://github.com/user-attachments/assets/fbcd0314-ec81-4211-b8e2-e878ec2d974a)|![실시간 채팅 이미지](https://github.com/user-attachments/assets/3d38f41a-2839-49c4-92fe-6033be142206)|
|:---:|:---:|
|캐스퍼 레이싱|실시간 기대평|

### ✨ **이벤트 개요**
**캐스퍼 레이싱 게임 🏁**
- 간단한 유형 검사를 통해 팀을 배정받고, **충전 버튼을 클릭**하여 자신이 응원하는 팀의 자동차를 움직이는 **실시간 경쟁형 이벤트**입니다.

**실시간 기대평 💬**
- 사용자가 자신이 응원하는 팀을 위해 응원 메시지를 주고받을 수 있도록 설계된 **채팅 시스템**입니다.

### ✨ **이벤트 특징**
1. **실시간 상호작용**
  - 모든 유저의 클릭 및 채팅이 **즉시 화면에 반영됨.** 게임 진행 상황, 순위 변화, 채팅을 실시간으로 확인 가능.
  - 새로고침 없이도 화면이 계속 업데이트 됨.

2. **기대평 필터링**
  - 관리자는 어드민 페이지를 통해 욕설 및 부적절한 표현 **실시간 필터링** 가능.


<div align="center">
<h2>[Socket App] 프로젝트</h2>
</div>

### ✨ **주요 목표**
1. **웹소켓을 통한 실시간 이벤트 기능 구현**
: **캐스퍼 레이싱 게임** 및 **기대평 채팅 시스템**을 웹소켓 연결을 통해 구현한다.

2. **고가용성 아키텍처 설계 및 구현**
: **대규모 트래픽**에도 안정적인 서비스를 제공할 수 있는 고가용성 아키텍처를 설계하고 구현한다.

### ✨ **아키텍처**
<div align="center">
  <img src="https://github.com/user-attachments/assets/bdd54eb5-da96-4838-938c-7c82fb0d820b" alt="아키텍처" width="70%">
</div>
<!-- <img width="614" alt="아키텍처이미지" src="https://github.com/user-attachments/assets/bdd54eb5-da96-4838-938c-7c82fb0d820b"> -->

#### 특징

1. **서비스 분리**
<!-- <img width="581" alt="서비스 분리" src="https://github.com/user-attachments/assets/748acfef-1649-4d06-96f6-84c1ddcf4f81"> -->

|![서비스 분리 이미지](https://github.com/user-attachments/assets/748acfef-1649-4d06-96f6-84c1ddcf4f81)|설명|
|:---:|:---:|

2. **로드 밸런싱 / 오토스케일링 및 트래픽 분산**

3. **Message Broker**

<!-- <img width="692" alt="pub:sub" src="https://github.com/user-attachments/assets/30b42256-8326-4e60-bb7a-a57089e3f96b"> -->

![pub/sub 사진](https://github.com/user-attachments/assets/30b42256-8326-4e60-bb7a-a57089e3f96b)
: **Redis Pub/Sub**를 활용하여 **분산 서버 환경에서 실시간 데이터 동기화**

---

[회의록 노션 링크](https://www.notion.so/bside/b0eb075bb2374e2e8a7dddd5ad35ea9c?v=ed0613449dab48248731f85f79a04f0f&pvs=4)

[Github wiki](https://github.com/softeerbootcamp4th/Team4-newCar-FE/wiki)

## 멤버
| 직책 | 담당 | 이름 | github id  | 취미 | |
|--|--|--|--|--|--|
| 팀장 | FE | 김보민 |  [@nimod7890](https://github.com/nimod7890)| 코딩 | <img src="https://github.com/user-attachments/assets/f2a2d74e-6c39-48ea-ab20-7665eae9be12" alt="김보민" width="100"/> |
| 팀원 | FE | 성락현 | [@racgoo](https://github.com/racgoo) | 명상 | <img src="https://github.com/user-attachments/assets/27a3d5e6-4a37-42e4-9393-d8e353d5b11f" alt="성락현" width="100"/> |
| 팀원 | BE | 장준하 | [@jun-ha](https://github.com/jun-ha) | 코딩 | <img src="https://avatars.githubusercontent.com/u/97020820?v=4" alt="장준하" width="100"/> |
| 팀원 | BE | 배진환 | [@bjh3311](https://github.com/bjh3311) | 서브웨이 | <img src="https://github.com/user-attachments/assets/4e55c61e-48a0-47c4-be22-9ac97b6c9f1a" alt="배진환" width="100"/>  |

## 시연 영상
https://youtu.be/73tDKewWj6I?feature=shared


## 이슈관리 - jira


https://softeer-4-apple.atlassian.net/jira/software/projects/TASK/boards/1

