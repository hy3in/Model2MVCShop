# [Model2MVCShop]
![캡처](https://user-images.githubusercontent.com/72587100/100417030-3fad2580-30c3-11eb-890c-04dbd72735c1.PNG)
상용으로 운영될 쇼핑몰을 구축하는 것이 아닌 개발 능력 향상을 목적으로 제작했습니다. 일반적인 물건을 등록하고 유저에 의해 구매 되는 등 일반적인 업무 로직은 동일합니다.


## 사용기술 및 개발환경
JAVA,
HTML, CSS, JavaScript,
Spring, MyBatis,
MySQL,
Apache Tomcat, GIThub


## 11단계 Refactoring
1.	J2SE / J2EE Basic
구현되어있는 공통모듈분석 후 회원, 상품, 구매모듈 구현
2.	검색조건 유지, 페이지나누기, ROWNUM을 이용한 효율적인 query
3.	EL / JSTL 적용
4.	Spring, MyBatis Framework를 이용해여 Business Logic 구현 및 Junit을 이용하여 단위테스트
5.	AOP 적용하여 log남기기, Transaction 처리
6.	Annotaion기반 Controller 적용
7.	다양한 view를 지원하기 위해 URL mapping방식 변경
8.	JSON을 이용하여 RestFul Server – Client 구현


## Model2MVCShop 기능설명
### Rule
- 비회원
- 회원
- 관리자
### UserController
1. addUser
>- 입력된 정보를 통해 회원가입을 하는 기능
>- checkDuplication을 통해 중복아이디 체크
2. getUser
>- 유저 상세보기 요청
3. updateUser
>- 유저 정보 수정 요청
4. getUserList
>- 유저 목록 요청
5. getTotalCount
>- 게시판 Page 처리를 위한 RowNum추출
6. insertGrade
>- getTotalPrice를 통해 얻은 총금액으로 회원등급 요청
### ProductController
1. addProduct
>- 상품 등록 요청
2. getProduct
>- 상품 상세 보기 요청
3. updateProduct
>- 상품 정보 수정 요청
4. listProduct
>- 상품 목록 조회 요청
### PurchaseController
1. addPurchase
>- 구매 요청
2. getPurchase
>- 구매 상세정보 요청
3. updatePurchase 
>- 구매 상세정보 수정 요청
4. listPurchase
>- 구매 목록 요청
5. updateTranCode
>- 배송 상태 수정 요청
