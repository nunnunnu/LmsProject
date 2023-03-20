package com.project.lms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.lms.entity.GradeInfoEntity;
import com.project.lms.entity.TestInfoEntity;
import com.project.lms.entity.member.MemberInfoEntity;
import com.project.lms.entity.member.StudentInfo;
import com.project.lms.entity.member.TeacherInfo;
import com.project.lms.repository.custom.GradeInfoRepositoryCustom;
import com.project.lms.vo.ScoreAvgListBySubjectVO;
import com.project.lms.vo.grade.SameGrade;
import com.project.lms.vo.request.AvgBySubjectTotalVO;
import com.project.lms.vo.request.ScoreAvgBySubject2VO;
import com.project.lms.vo.request.ScoreAvgBySubjectVO;
import com.project.lms.vo.request.ScoreListBySubjectYearVO;

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


    @Query("SELECT si.miSeq FROM ClassStudentEntity cst join cst.classInfo ci join cst.student si WHERE ci.ciSeq = :classSeq")
    List<Long> findByCsSeq(@Param("classSeq") Long classSeq); // 조회하려는 반의 학생 시퀀스를 모두 리스트에 담는다.

    @Query("SELECT sub.subName AS subject, AVG(grd.grade) as avg FROM GradeInfoEntity grd "
            + "JOIN SubjectInfoEntity sub ON grd.subject.subSeq = sub.subSeq "
            + "JOIN TestInfoEntity tt ON tt.testSeq = grd.test.testSeq "
            + "WHERE DATE_FORMAT(tt.testDate, '%Y%m') = :yearMonth AND grd.student.miSeq IN :seqs " 
            + "GROUP by grd.subject.subSeq")
    List<ScoreAvgListBySubjectVO> avgBySubject(@Param("seqs") List<Long> list, @Param("yearMonth") Integer yearMonth); // 과목별 평균을 찾아 리스트에 담는다.

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
}
