package com.project.lms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.lms.entity.GradeInfoEntity;
import com.project.lms.entity.SubjectInfoEntity;
import com.project.lms.entity.TestInfoEntity;
import com.project.lms.entity.member.MemberInfoEntity;
import com.project.lms.entity.member.StudentInfo;
import com.project.lms.entity.member.TeacherInfo;
import com.project.lms.repository.custom.GradeInfoRepositoryCustom;
import com.project.lms.vo.ScoreAllListBySubjectVO;
import com.project.lms.vo.ScoreAvgListBySubjectVO;
import com.project.lms.vo.grade.SameGrade;
import com.project.lms.vo.grade.ScoreTestTop10VO;
import com.project.lms.vo.grade.ScoreTop10VO;
import com.project.lms.vo.request.AvgBySubjectTotalVO;
import com.project.lms.vo.request.ScoreAvgBySubject2VO;
import com.project.lms.vo.request.ScoreAvgBySubjectVO;
import com.project.lms.vo.request.ScoreListBySubjectYearVO;
import com.project.lms.vo.response.ScoreRankBySubjectVO;

public interface GradeInfoRepository extends JpaRepository<GradeInfoEntity, Long>, GradeInfoRepositoryCustom { //querydsl로 만든 쿼리문을 사영하기 위해 상속받음.
    List<GradeInfoEntity> findByStudent(MemberInfoEntity student);

    GradeInfoEntity findByTest(TestInfoEntity test);

    // @Query(value =
    // "select ti.testName as testName, si.subName as subjectName, gi.student.miSeq as studentNum, gi.grade as grade from GradeInfoEntity gi "
    // + 
    // "join TestInfoEntity ti on gi.test.testSeq = ti.testSeq "
    // +
    // "join SubjectInfoEntity si on gi.subject.subSeq = si.subSeq "
    // +
    // "where gi.student.miSeq =:seq and function('date_format',ti.testDate ,'%Y' ) = function('date_format', now(),'%Y')"
    // )
    // List<ScoreListBySubjectYearVO> findByYearScoreList(@Param("seq") Long seq);

    @Query(value =
    "SELECT ti.testName as testName, "
    +
    "GROUP_CONCAT(DISTINCT CASE WHEN si.subName = '독해' THEN gi.grade END) as comprehension, "
    +
    "GROUP_CONCAT(DISTINCT CASE WHEN si.subName = '어휘' THEN gi.grade END) as vocabulary, "
    +
    "GROUP_CONCAT(DISTINCT CASE WHEN si.subName = '문법' THEN gi.grade END) as grammar, "
    +
    "GROUP_CONCAT(DISTINCT CASE WHEN si.subName = '듣기' THEN gi.grade END) as listening "
    +
    "FROM GradeInfoEntity gi JOIN TestInfoEntity ti ON gi.test.testSeq = ti.testSeq "
    +
    "JOIN SubjectInfoEntity si ON gi.subject.subSeq = si.subSeq "
    +
    "WHERE gi.student.miSeq =:seq AND YEAR(ti.testDate) = YEAR(CURRENT_DATE) GROUP BY ti.testSeq")
    List<ScoreListBySubjectYearVO> findByYearScoreList(@Param("seq") Long seq);


    @Query("select avg(g.grade)as avg from GradeInfoEntity g where g.teacher = :teacher group by g.subject")
    ScoreAvgListBySubjectVO avgBySubject(@Param("teacher") TeacherInfo teacher);

    @Query("select (select sum(g.grade) from GradeInfoEntity g where g.test = :test and g.student = g2.student) as totalSum, Group_concat(Distinct g2.student) as student "
        +"from GradeInfoEntity g2 group by totalSum "
        +"having count(DISTINCT g2.student) >=2"
    )
    
    List<SameGrade> sameGrade(@Param("test") TestInfoEntity test);
    
    @EntityGraph(attributePaths = {"subject"})
    List<GradeInfoEntity> findByTestAndStudent(TestInfoEntity test, StudentInfo student);

    @Query("select g from GradeInfoEntity g join fetch g.subject join fetch g.student where g.test =:test and g.student.miSeq in (:seqs)")
    List<GradeInfoEntity> findStudentAndTest(@Param("test") TestInfoEntity test, @Param("seqs") long[] seqs);



    @Query("SELECT si.miSeq FROM ClassStudentEntity cst join cst.classInfo ci join cst.student si WHERE ci.ciSeq = :classSeq")
    List<Long> findByCsSeq(@Param("classSeq") Long classSeq); // 조회하려는 반의 학생 시퀀스를 모두 리스트에 담는다.

    @Query("SELECT sub.subName AS subject, AVG(grd.grade) as avg FROM GradeInfoEntity grd "
            + "JOIN SubjectInfoEntity sub ON grd.subject.subSeq = sub.subSeq "
            + "JOIN TestInfoEntity tt ON tt.testSeq = grd.test.testSeq "
            + "WHERE DATE_FORMAT(tt.testDate, '%Y%m') = :yearMonth AND grd.student.miSeq IN :seqs " 
            + "GROUP by grd.subject.subSeq")
    List<ScoreAvgListBySubjectVO> avgBySubject(@Param("seqs") List<Long> list, @Param("yearMonth") Integer yearMonth); // 과목별 평균을 찾아 리스트에 담는다.

    @Query("SELECT mi.miName AS name, ci.ciName AS className, g.grade AS score, RANK() OVER(ORDER BY g.grade DESC) AS ranking FROM GradeInfoEntity g "
            + "JOIN MemberInfoEntity mi ON mi.miSeq = g.student.miSeq "
            + "JOIN ClassStudentEntity cs ON cs.student.miSeq = mi.miSeq "
            + "JOIN ClassInfoEntity ci ON ci.ciSeq = cs.classInfo.ciSeq "
            + "WHERE g.subject.subSeq = :seq")
    List<ScoreRankBySubjectVO> rankBySubject(@Param("seq") Long seq); // 과목별 학생 랭킹을 찾아 리스트에 담는다.

    // @Query("SELECT mi.miSeq FROM MemberInfoEntity mi WHERE mi.miRole = :role")
    // List<Long> findByMiRole(@Param("role") Role role); // 학생 시퀀스를 모두 리스트에 담는다.

    // @Query("SELECT sub.subName AS subject, grd.grade AS score FROM GradeInfoEntity grd "
    //         + "JOIN SubjectInfoEntity sub ON grd.subject.subSeq = sub.subSeq "
    //         + "JOIN TestInfoEntity tt ON tt.testSeq = grd.test.testSeq "
    //         + "WHERE DATE_FORMAT(tt.testDate, '%Y%m') = :yearMonth AND grd.student.miSeq IN :seqs " 
    //         + "GROUP by grd.subject.subSeq")
    // List<ScoreListBySubjectVO> scoreBySubject(@Param("seqs") List<Long> list, @Param("yearMonth") Integer yearMonth); 


    // @Query("SELECT si.miSeq as seq,si.miName as name, ci.ciName as className, "
    //             // + "(SELECT gi.grade from GradeInfoEntity gi WHERE gi.test = :test AND gi.student.miSeq = si.miSeq AND gi.subject.subSeq=1L) AS reading, "
    //             // + "(SELECT gi.grade from GradeInfoEntity gi WHERE gi.test = :test AND gi.student.miSeq = si.miSeq AND gi.subject.subSeq=2L) AS vocabulary, "
    //             // + "(SELECT gi.grade from GradeInfoEntity gi WHERE gi.test = :test AND gi.student.miSeq = si.miSeq AND gi.subject.subSeq=3L) AS grammar, "
    //             + "(SELECT gi.grade FROM GradeInfoEntity gi join fetch gi.student WHERE gi.test = :test and gi.student.miSeq = si.miSeq AND gi.subject=:sub) AS listening "
    //         + "FROM StudentInfo si "
    //         // + "join MemberInfoEntity mi on mi.miSeq = si.miSeq " 
    //         + "LEFT OUTER JOIN GradeInfoEntity gi ON gi.student.miSeq = si.miSeq "
    //         + "JOIN ClassStudentEntity cs ON cs.student.miSeq = si.miSeq "
    //         + "JOIN ClassInfoEntity ci ON ci.ciSeq = cs.classInfo.ciSeq")
    // List<ScoreAllListBySubjectVO> scoreBySubject(@Param("test") TestInfoEntity test, @Param("sub") SubjectInfoEntity sub);
        

    //  이건 사용안함.
    @Query(
        "SELECT AVG(CASE WHEN si.subName = '독해' THEN gi.grade END) AS avgComprehension, " +
        "AVG(CASE WHEN si.subName = '어휘' THEN gi.grade END) AS avgVocabulary, " +
        "AVG(CASE WHEN si.subName = '문법' THEN gi.grade END) AS avgGrammar, " +
        "AVG(CASE WHEN si.subName = '듣기' THEN gi.grade END) AS avgListening " +
        "FROM GradeInfoEntity gi " + 
        "JOIN TestInfoEntity ti ON gi.test.testSeq = ti.testSeq " +
        "JOIN SubjectInfoEntity si ON gi.subject.subSeq = si.subSeq " +
        "WHERE gi.student.miSeq = :seq AND YEAR(ti.testDate) = YEAR(CURRENT_DATE)")
        ScoreAvgBySubjectVO findByAvgBySubject(@Param("seq") Long seq);


    @Query(
        "select si.subName as subject, avg(gi.grade) as avg from GradeInfoEntity gi "
        + 
        "join TestInfoEntity ti on gi.test.testSeq = ti.testSeq "
        + 
        "join SubjectInfoEntity si on gi.subject.subSeq = si.subSeq "
        +
        "where gi.student.miSeq = :seq AND YEAR(ti.testDate) = YEAR(CURRENT_DATE) group by si.subName"
        )
        List<ScoreAvgBySubject2VO> findByAvgBySubject2(@Param("seq") Long seq);

    // 최신순 + 과목별 + 평균
    @Query(value =
        "SELECT b.subName As subjectName, AVG(a.grade) as totalAvg FROM GradeInfoEntity a, SubjectInfoEntity b "
        +
        "Join TestInfoEntity c "
        +
        "WHERE c.testSeq =:testSeq AND b.subSeq = a.subject.subSeq "
        +
        "group by b.subName ")
        List<AvgBySubjectTotalVO> findBySubjectTotal(@Param("testSeq") Long testSeq);

    //과목별 성적 내림차순으로 리스트 출력(사용안함)
    @Query("select g from GradeInfoEntity g where g.subject = :subject order by g.grade desc")
    List<GradeInfoEntity> findAllBySubjectOrderByGradeDesc(SubjectInfoEntity subject);
    
    //시험별 +과목별 상위 10% 
    @Query("select a.subject as sub, a.test as test , COUNT(a)/0.1 as count from GradeInfoEntity a "
    +
    "group by a.subject, a.test ")
    List<ScoreTestTop10VO> getScoreTestTop10(@Param("top") Integer top);
    //가장 최신 + 과목별 + 상위 10% 평균
    @Query("select a from GradeInfoEntity a where a.subject = :subject and a.test = :test order by a.grade desc limit :count")
    List<GradeInfoEntity> findAllBySubjectAndTestOrderByGradeDescTopCount(
        @Param("subject") SubjectInfoEntity subject,
        @Param("test") TestInfoEntity test,
        @Param("count") double count 
    );

    //해당시험 총 응시자 수
    @Query("SELECT count(DISTINCT g.student) from GradeInfoEntity g where g.test = :test ")
    Integer countTestStudent(@Param("test") TestInfoEntity test);

    //상위 10% 학생을 구하기 위한 쿼리문
    // Select  gi_mi_seq1  from grade_info gi where gi_test_seq =6 group by gi_mi_seq1 order by sum(gi.gi_grade) desc limit 10[위 쿼리문의 총 응시자수 /10한 값]
    // @Query("SELECT g.student FROM GradeInfoEntity g JOIN FETCH g.student WHERE g.test = :test GROUP BY g.student ORDER BY SUM(g.grade) DESC limit :cut")
    // List<StudentInfo> findTop10List(@Param("test") TestInfoEntity test, @Param("cut") Integer cut);

    //상위 10% 학생의 평균 점수 조회
    // select avg(gi_grade), gi_sub_seq  from grade_info gi where gi_test_seq = 6 and gi_mi_seq1 in (19,28,49,110,16,106,63,88,86,44) group by gi_sub_seq 
    @Query("select avg(g.grade) as grade, g.subject as subject from GradeInfoEntity g where g.test = :test and g.student in (:students) group by g.subject")
    List<ScoreTop10VO> avgTop10(@Param("test") TestInfoEntity test, @Param("students") List<StudentInfo> studentInfos);
}
