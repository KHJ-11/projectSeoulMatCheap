# SeoulMatCheap
#### 개발기간 : 2021년 03월 08일 ~ 2021년 05월 28일
---
### [ 개요 ]

**서울의 착한가격업소에 대한 정보를 제공하는 모바일 서비스**

행정안전부와 서울특별시가 지정한 물가안정 지정업소(착한가격업소)를
보다 쉽게 검색하고 찾아갈 수 있는 모바일 서비스
* 로그인
* 현재위치를 통한 탐색
* 검색
* 지역별, 카테고리별 탐색
* 마이페이지
---
## [ Preview ]

---
## [ 개발환경 ]
* Language : Kotlin
* Front-end : Android studio
* back-end : Spring boot, mySql, Aws, IntelliJ
* Design : Xd, Ps, Ai
---
## [ 라이브러리 ]
* NaverMap
* Retrofit2
* rxjava2
* navigation
* glide
* kakaoLogin
* NaverLogin
* Google FusedLocationProvider
* room
--- 
### [ API ]
* 서울시 착한가격업소 공공 API
---
### [ 역할 ]
* Category
* Review
---
### [ 구현 ]
* 카테고리 (메인페이지)
  
  ```
  - 지역별 탐색
    - RecyclerView를 이용하여 해당 지역별 아이템 리스트 나열
  - 분류별 탐색
    - RecyclerView를 이용하여 해당 분류별 아이템 리스트 나열
  - 상단 텍스트뷰
    - ellipsize, singleLine을 이용한 움직이는 텍스트뷰
    - 클릭 시 AlertDialog를 이용한 가이드 팝업창 생성
  - 현위치 주소
    - GPS 현재 위치 주소 텍스트로 출력
  ```

* 카테고리 (지역별)
  
  ```
  - 지역구
    - intent.putExtra를 이용하여 클릭한 지역구명을 ToolBar title에 적용
    - RecyclerView를 이용하여 해당 지역별 아이템 리스트 나열
    - TabLayout과 tabPosition를 이용해 선택한 탭 별로 각각 다른 리스트 나열
    - sortedByDescending를 이용하여 평점순 리스트를 내림차순으로 정렬
    - sortedBy를 이용하여 거리순 리스트를 오름차순으로 정렬
  ```

* 카테고리 (분류별)

  ```
  - 분류별
    - intent.putExtra를 이용하여 클릭한 분류명을 ToolBar title에 적용
    - RecyclerView를 이용하여 해당 분류별 아이템 리스트 나열
    - TabLayout과 tabPosition를 이용해 선택한 탭 별로 각각 다른 리스트 나열
    - sortedByDescending를 이용하여 평점순 리스트를 내림차순으로 정렬
    - sortedBy를 이용하여 거리순 리스트를 오름차순으로 정렬
  ```
  
  * 리뷰 (리뷰목록)

  ```
  - 사용자 리뷰
    - RecyclerView를 이용하여 사용자 리뷰 아이템 리스트 나열
  ```
