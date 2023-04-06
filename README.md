[노션으로 보기 (더 상세한 내용을 확인할 수 있습니다)](https://coordinated-sunset-4f9.notion.site/LMS-NCT-61cfe7e7a0ef4a919fc45102cdb1ae9f)

# LMS 시스템 프로젝트 - NCT 학습관리시스템

기간: 2023년 3월 6일 → 2023년 4월 5일
사용기술: Gradle, JPA, git, java, mysql, querydsl, redis, spring boot, spring security
작성 날짜: 2023년 4월 5일 오후 5:19
작업인원: 12
태그: 협업 프로젝트

- 총 작업 인원 : 12명 (백엔드 - 7명/ 프론트엔드 - 5명)
    - 총괄대표 : **박진희**
    - 백엔드 : 차경준(팀장), 박정은, 박진혁, 이도영, 유지은, 이영은
    - 프론트엔드 : 조승현(팀장), 김철호, 옥지은, 박시은, 이혜영

<aside>
📝 LMS 시스템 프로젝트입니다.
고등학교 영어 학원의 성적을 관리하는 프로젝트입니다. 학생의 과목 별 점수를 기록 후 성적 분석이 가능합니다. 학생의 강점과목과 약점 과목을 조회할 수 있으며 선생님이 학생에게 남기는 피드백 게시판도 존재합니다.
선생님이 학생의 점수 추이를 보며 지도가 가능하며 입력된 과목별 점수로 학생의 추천 반 정보도 엑셀파일로 다운 가능합니다.
    api의 성능 향상을 위해 batch insert, 테이블 index등 sql의 튜닝도 도전해보았습니다.

</aside>

## 구현 기능

|이름|기여도|태그|설명|
|------|-----|-----|---|
|회원가입|100|회원|각 회원 등급별로(마스터/직원/학생/선생) 가입할 수 있는 기능|
|토큰 재발급|100|회원|redis에 refresh토큰을 저장해서 재발급|
|소속 반 학생 조회|100|반|로그인한 선생님의 소속 반에 해당한 학생들의 리스트를 조회|
|동일점수 학생 알림|100|점수|합계가 같은 학생의 과목별 점수를 조회. 성능 향상을 위해 테이블에 index를 추가함 (반 배정 백업 기능.)|
|시험 점수를 토대로 반 배정|100|점수|엑셀로 배정된 반을 다운로드 할 수 있도록 API를 구현. 반의 갯수와 학생의 수로 백분율로 계산해서 대략적으로 반을 권고해줌.|
|상위 10프로 학생의 점수|80|점수|쿼리문을 작성, 로직 작성. 차트에 표현할 데이터를 내보냄|
|시험 점수 일괄 입력|50|점수|이미 작성된 API의 insert의 응답속도 지연으로 JdbcTemplate을 사용해서 batch insert를 구현|
|비밀번호 수정|30|회원|유효성 검사 추가, 기존 유효성 검사의 에러 수정|
|로그인|30|회원|로그인 API에 refresh token을 redis에 저장하는 로직 추가|

## 시연 영상
[![Video Label](http://img.youtube.com/vi/lPJy2byc4uU/0.jpg)]
(https://youtu.be/lPJy2byc4uU)



### 개발 문서

[테이블명세서(길이가 길어서 페이지로 분리했습니다.)](https://coordinated-sunset-4f9.notion.site/ac79a336f0c14872bd48a0819572e9b4)

- 요구사항 정의서
    
![Untitled](https://user-images.githubusercontent.com/110175918/230352957-1c20d526-01c3-4de2-b4b8-4fcfa83d95db.png)
    
- 요구사항 추적표
    
![Untitled (1)](https://user-images.githubusercontent.com/110175918/230353211-92267e49-e197-46be-a67f-59af12db457a.png)

    
- 시험시나리오
    
   ![Untitled (2)](https://user-images.githubusercontent.com/110175918/230353403-c8e27ffa-2243-4bd4-82ae-525b845da3a7.png)

- WBS
    
   ![Untitled (3)](https://user-images.githubusercontent.com/110175918/230353527-dedb62a9-fd20-4322-b0a1-a906b7833756.png)

# 그외 링크

### PPT 주소

[컬러풀하고 단순 명료한 취미와 관심사 디지털 프레젠테이션 파티용 재미있는 프레젠테이션](https://www.canva.com/design/DAFeoLJAiNU/ZTfasGRXLzHSC37ZdJ_9Pw/edit?utm_content=DAFeoLJAiNU&utm_campaign=designshare&utm_medium=link2&utm_source=sharebutton)


### 와이어 프레임

[https://www.figma.com/file/rjXHfPx3BrIU5h6LXoylBS/%EC%84%B1%EC%A0%81%EA%B4%80%EB%A6%AC?node-id=0-1&t=XWNRTcnjBuZdFUFS-0](https://www.figma.com/file/rjXHfPx3BrIU5h6LXoylBS/%EC%84%B1%EC%A0%81%EA%B4%80%EB%A6%AC?node-id=0-1&t=XWNRTcnjBuZdFUFS-0)

### 화면 기획서

[https://www.figma.com/file/N199vzjaFmtsciywRb3vMY/LMS?node-id=0-1&t=rfOklOLKWJJ3ZRpv-0](https://www.figma.com/file/N199vzjaFmtsciywRb3vMY/LMS?node-id=0-1&t=rfOklOLKWJJ3ZRpv-0)


[회의록](https://coordinated-sunset-4f9.notion.site/039957de06c649a291e34f0db439b27f?v=cfd6cebb463a43b786d9948749f38bca)


[작업일지](https://coordinated-sunset-4f9.notion.site/fa19995d50b644f3975b0494707c98f0?v=6c0d37fbc62745b3a6baf1f30b93736b)

