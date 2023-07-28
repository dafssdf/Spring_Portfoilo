# SpringBoot-Project-MONTHLY
스프링부트 프로젝트

## 🖥️ 프로젝트 소개
한 달에 한번, 나를 위한 선물, 구독 서비스 이커머스 플랫폼
<br>

## 🕰️ 개발 기간
* 23.06.04일 - 23.07.17일

### 🧑‍🤝‍🧑 맴버구성
 - 팀장: 정우정 : 판매자 페이지(브랜드 등록,주문 관리,상품 관리,로그인), 판매장 신청 페이지
 - 부팀장: 김준혁 : 정기결제, 구독관리, 제품 상세 페이지, 댓글,회원정보 수정(배송지,비밀번호,회원탈퇴) 
 - 팀원1: 박은서 : 관리자 페이지(차트페이지,회원관리,상품관리,구독자관리), 결제날짜 문자 발송 기능
 - 팀원2: 강승연 : 메인 페이지, 브랜드 페이지, 상품 페이지, 더보기 사이드바, 상품검색
 - 팀원3: 김덕수 : 로그인, 회원가입, 아이디/비밀번호 찾기, 카카오,네이버 로그인 api

### ⚙️ 개발 환경
- `Java 11`
- `JDK 1.8.0`
- **IDE** : STS 3.9
- **Framework** : Springboot(2.x)
- **Database** : Oracle DB(11xe)
- **ORM** : Mybatis

### 📰 기획 배경
-다양한 분야의 구독 서비스 제공과 사용 증가(넷플릭스,쿠팡,마켓커리,카페박스.OTT서비스 등)
-이용 중인 구독 서비스에 대해 일괄적인 파악 어려움으로 인해 이를 관리와 구독 플랫폼을 모아서 관리해주는 플랫폼이 있으면 좋겠다는 생각에서 시작하게 되었습니다.

### ✨ 기대 효과
- 구매자 : 개별 서비스들에 대해 각각의 사업체에 대한 가입절차를 거치지 않고도 바로 서비스를 이용가능,개인의 구독 서비스 파악,관리,이용 편리성 증가
- 사업체의 개별적 홍보 어려움 극복, 정기 결제를 통한 메출 안정화, 향후 매출을 예측,일괄된 경험과 혜택을 제공하여 고객 충성도를 향상
- 관리자 : 구독 데이터를 수집하고 분석하여 선호도와 구매 패턴 등을 파악, 마케팅 전략을 개선하고 개인화된 서비스를 제공 가능 구독 서비스를 기반으로 추후 플랫폼의 사업 확장 가능성 

## 📌 내가 맡은 기능
#### 마이 페이지 <a href="https://github.com/dafssdf/Spring_Portfoilo/wiki/%EB%A7%88%EC%9D%B4-%ED%8E%98%EC%9D%B4%EC%A7%80" >상세보기 - WIKI 이동</a>
- 구독 관리(내부 구독, 외부 구독)
- 내부구독 삭제
- 외부 구독 추가 삭제
- 회원 정보 수정 페이지 이동

#### 결제 페이지 - 정기결제 <a href="https://github.com/dafssdf/Spring_Portfoilo/wiki/%EA%B2%B0%EC%A0%9C-%ED%8E%98%EC%9D%B4%EC%A7%80" >상세보기 - WIKI 이동</a>
- 새로운 배송지 등록
- kakao pay 정기 결제 api 실행 후 결제

#### 회원정보 수정 <a href="https://github.com/dafssdf/Spring_Portfoilo/wiki/%ED%9A%8C%EC%9B%90%EC%A0%95%EB%B3%B4-%EC%88%98%EC%A0%95" >상세보기 - WIKI 이동</a>
- 회원 정보 변경(비밀번호, 배송지)
- 회원 탈퇴

#### 제품 상세 페이지 <a href="https://github.com/dafssdf/Spring_Portfoilo/wiki/%EC%A0%9C%ED%92%88-%EC%83%81%EC%84%B8-%ED%8E%98%EC%9D%B4%EC%A7%80" >상세보기 - WIKI 이동</a>
- 제품 정보 설정(수량, 배송 날짜)
- 제품 상세보기(더보기, 감추기 버튼)
- 배너(제품 정보, 상품 구매 안내, 상품 후기) 스크롤 이동
- 댓글(등록,수정,삭제,페이징)

