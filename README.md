# Getum
Repository for Getum (AngelHack)

---
## 0. Background
### (1) 테스트 방법 (Emulator)
* apk위치 : git 프로젝트의 apk 디렉토리
* 안드로이드 스튜디오에서 emulator 실행 후 드래그&드롭으로 apk 설치 가능
* 메뉴 이동 후 Getum 앱 실행
### (2) 테스트 방법 (Physical Device)
* 실제 기기 연결 후 안드로이드 스튜디오에서 빌드
### (3) 개발 및 테스트 환경
* 개발 환경 : Android Studio, Api 26, Oreo (Nauget 이상에서 실행해야 함)
* 테스트 환경 : Emulator : Pixel 3a API 26, Real Device : LG V30

---

## 1. 어플리케이션 사용법
  ### 1) 우산 보관함 위치 확인
  * 어플리케이션 실행 후 우산 아이콘의 위치를 확인한다.
  * 아이콘을 눌렀을 때, 상단에 우산 개수, 위치 및 이용료를 확인한다.
  ### 2) 로그인/회원가입   
  * 상단의 햄버거 버튼을 누른 후 `로그인/회원가입` 탭을 선택한다.    
  * `SIGN IN` 버튼을 누른 후 각각의 정보를 입력한다.    
  * 정보 입력 후 `회원가입완료`를 눌러 회원가입을 완료한다.      
  * 로그인 화면에서 아이디와 비밀번호를 입력해 로그인을 진행한다.    
  ### 3) 우산 대여    
  - 로그인을 완료한 후에 진행할 수 있다.   
  - 로그인 이후 지도에 위치한 `QR코드` 버튼을 누른다.   
  - "**기획/src/qrcode**": 대여용 QR 코드 5개, 반납용 QR코드 5개(샘플)를 받아온다.   
  - 대여용 QR 코드를 인식시킨다.    
  - `결제하기`버튼을 누른 후 결제수단을 선택한다.     
  - `proceed payment` 버튼을 눌러 결제를 완료한다.   
  - `돌아가기` 버튼을 눌러 메인화면으로 돌아간다.    
  ### 4) 우산 반납   
  - 로그인되어있는 상태에서 반납용 QR코드 인식한다.     
  - 반납이 완료된 것을 확인한다.   
  - `돌아가기` 버튼을 눌러 메인화면으로 들어간다.   
  ### 5) 마이페이지   
  - 좌측 상단의 메뉴버튼을 눌러 `마이페이지` 버튼을 누른다.   
  - 사용자 정보, 카드정보, 진행했던 대여/반납 내역을 확인할 수 있다.    
  ### 6) 추가 정보 확인   
  - 좌측 상단의 메뉴버튼을 눌러 `안내사항`/`고객센터` 버튼을 누른다.    
  - 이후 각각의 페이지의 `사용방법`/`대여요금` 버튼을 눌러 정보를 확인한다. 
    
    ---

## 2. Activity 설명
|Activity|Description
|--------|------|
|CreateQR|샘플 QR 코드 생성용 |
|CustomcenterActivity|고객센터 화면으로 이동 |
|description|이용방법 화면으로 이동 |
|howto|대여요금안내 화면으로 이동|
|InfoActivity| 안내사항 화면으로 이동 |
|LoginActivity|입력받은 아이디와 패스워드로 로그인|
|MainActivity|현재 위치 조회, 지도로부터 보관함 위치확인, QR 코드인식, 로그인 이전 drawable menu/로그인 이후 drawable menu로 이동 |
|MyAdapter| Listview 생성|
|ProceedPay|결제 이후 DB 정보 수정 |
|RecordData|Rental log 받아온 후 Listview 출력|
|ReturnActivity|반남 엑션|
|ScanQR|QR 코드 스캔|
|SignInActivity|회원가입화면으로 이동|
