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

</aside>

### [개발 문서](https://www.notion.so/LMS-NCT-61cfe7e7a0ef4a919fc45102cdb1ae9f)

— 엑셀 파일로 보기

[3차 프로젝트(성적관리) (2).xlsx](LMS%20%E1%84%89%E1%85%B5%E1%84%89%E1%85%B3%E1%84%90%E1%85%A6%E1%86%B7%20%E1%84%91%E1%85%B3%E1%84%85%E1%85%A9%E1%84%8C%E1%85%A6%E1%86%A8%E1%84%90%E1%85%B3%20-%20NCT%20%E1%84%92%E1%85%A1%E1%86%A8%E1%84%89%E1%85%B3%E1%86%B8%E1%84%80%E1%85%AA%E1%86%AB%E1%84%85%E1%85%B5%E1%84%89%E1%85%B5%E1%84%89%E1%85%B3%E1%84%90%E1%85%A6%E1%86%B7%2061cfe7e7a0ef4a919fc45102cdb1ae9f/3%25EC%25B0%25A8_%25ED%2594%2584%25EB%25A1%259C%25EC%25A0%259D%25ED%258A%25B8(%25EC%2584%25B1%25EC%25A0%2581%25EA%25B4%2580%25EB%25A6%25AC)_(2).xlsx)

[테이블명세서(길이가 길어서 페이지로 분리했습니다.)](https://www.notion.so/ac79a336f0c14872bd48a0819572e9b4)

- [요구사항 정의서](https://www.notion.so/LMS-NCT-61cfe7e7a0ef4a919fc45102cdb1ae9f)
    
    ![Untitled](LMS%20%E1%84%89%E1%85%B5%E1%84%89%E1%85%B3%E1%84%90%E1%85%A6%E1%86%B7%20%E1%84%91%E1%85%B3%E1%84%85%E1%85%A9%E1%84%8C%E1%85%A6%E1%86%A8%E1%84%90%E1%85%B3%20-%20NCT%20%E1%84%92%E1%85%A1%E1%86%A8%E1%84%89%E1%85%B3%E1%86%B8%E1%84%80%E1%85%AA%E1%86%AB%E1%84%85%E1%85%B5%E1%84%89%E1%85%B5%E1%84%89%E1%85%B3%E1%84%90%E1%85%A6%E1%86%B7%2061cfe7e7a0ef4a919fc45102cdb1ae9f/Untitled.png)
    
- [요구사항 추적표](https://www.notion.so/LMS-NCT-61cfe7e7a0ef4a919fc45102cdb1ae9f)
    
    ![Untitled](LMS%20%E1%84%89%E1%85%B5%E1%84%89%E1%85%B3%E1%84%90%E1%85%A6%E1%86%B7%20%E1%84%91%E1%85%B3%E1%84%85%E1%85%A9%E1%84%8C%E1%85%A6%E1%86%A8%E1%84%90%E1%85%B3%20-%20NCT%20%E1%84%92%E1%85%A1%E1%86%A8%E1%84%89%E1%85%B3%E1%86%B8%E1%84%80%E1%85%AA%E1%86%AB%E1%84%85%E1%85%B5%E1%84%89%E1%85%B5%E1%84%89%E1%85%B3%E1%84%90%E1%85%A6%E1%86%B7%2061cfe7e7a0ef4a919fc45102cdb1ae9f/Untitled%201.png)
    
- [시험시나리오](https://www.notion.so/LMS-NCT-61cfe7e7a0ef4a919fc45102cdb1ae9f)
    
    ![Untitled](LMS%20%E1%84%89%E1%85%B5%E1%84%89%E1%85%B3%E1%84%90%E1%85%A6%E1%86%B7%20%E1%84%91%E1%85%B3%E1%84%85%E1%85%A9%E1%84%8C%E1%85%A6%E1%86%A8%E1%84%90%E1%85%B3%20-%20NCT%20%E1%84%92%E1%85%A1%E1%86%A8%E1%84%89%E1%85%B3%E1%86%B8%E1%84%80%E1%85%AA%E1%86%AB%E1%84%85%E1%85%B5%E1%84%89%E1%85%B5%E1%84%89%E1%85%B3%E1%84%90%E1%85%A6%E1%86%B7%2061cfe7e7a0ef4a919fc45102cdb1ae9f/Untitled%202.png)
    
- [WBS](https://www.notion.so/LMS-NCT-61cfe7e7a0ef4a919fc45102cdb1ae9f)
    
    ![Untitled](LMS%20%E1%84%89%E1%85%B5%E1%84%89%E1%85%B3%E1%84%90%E1%85%A6%E1%86%B7%20%E1%84%91%E1%85%B3%E1%84%85%E1%85%A9%E1%84%8C%E1%85%A6%E1%86%A8%E1%84%90%E1%85%B3%20-%20NCT%20%E1%84%92%E1%85%A1%E1%86%A8%E1%84%89%E1%85%B3%E1%86%B8%E1%84%80%E1%85%AA%E1%86%AB%E1%84%85%E1%85%B5%E1%84%89%E1%85%B5%E1%84%89%E1%85%B3%E1%84%90%E1%85%A6%E1%86%B7%2061cfe7e7a0ef4a919fc45102cdb1ae9f/Untitled%203.png)

### PPT 주소

[컬러풀하고 단순 명료한 취미와 관심사 디지털 프레젠테이션 파티용 재미있는 프레젠테이션](https://www.canva.com/design/DAFeoLJAiNU/ZTfasGRXLzHSC37ZdJ_9Pw/edit?utm_content=DAFeoLJAiNU&utm_campaign=designshare&utm_medium=link2&utm_source=sharebutton)

[구현 기능](https://www.notion.so/098eca99fefd48d29c75b0fa20dd07b7)

### 와이어 프레임

[https://www.figma.com/file/rjXHfPx3BrIU5h6LXoylBS/%EC%84%B1%EC%A0%81%EA%B4%80%EB%A6%AC?node-id=0-1&t=XWNRTcnjBuZdFUFS-0](https://www.figma.com/file/rjXHfPx3BrIU5h6LXoylBS/%EC%84%B1%EC%A0%81%EA%B4%80%EB%A6%AC?node-id=0-1&t=XWNRTcnjBuZdFUFS-0)

### 화면 기획서

[https://www.figma.com/file/N199vzjaFmtsciywRb3vMY/LMS?node-id=0-1&t=rfOklOLKWJJ3ZRpv-0](https://www.figma.com/file/N199vzjaFmtsciywRb3vMY/LMS?node-id=0-1&t=rfOklOLKWJJ3ZRpv-0)

---

### 시연 동영상

[https://youtu.be/lPJy2byc4uU](https://youtu.be/lPJy2byc4uU)

---

[회의록](https://www.notion.so/039957de06c649a291e34f0db439b27f)

---

[작업일지](https://www.notion.so/fa19995d50b644f3975b0494707c98f0)
