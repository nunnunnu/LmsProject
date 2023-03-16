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
import com.project.lms.vo.ScoreAvgBySubjectVO;
import com.project.lms.vo.grade.SameGrade;
import com.project.lms.vo.request.ScoreListBySubjectYearVO;

public interface GradeInfoRepository extends JpaRepository<GradeInfoEntity, Long> {
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
    "GROUP_CONCAT(DISTINCT CASE WHEN si.subName = '문법' THEN gi.grade END) as grammer, "
    +
    "GROUP_CONCAT(DISTINCT CASE WHEN si.subName = '듣기' THEN gi.grade END) as listening "
    +
    "FROM GradeInfoEntity gi JOIN TestInfoEntity ti ON gi.test.testSeq = ti.testSeq "
    +
    "JOIN SubjectInfoEntity si ON gi.subject.subSeq = si.subSeq "
    +
    "WHERE gi.student.miSeq =:seq AND FUNCTION('date_format',ti.testDate ,'%Y' ) = FUNCTION('date_format', now(),'%Y') GROUP BY ti.testSeq")
    List<ScoreListBySubjectYearVO> findByYearScoreList(@Param("seq") Long seq);


    @Query("select avg(g.grade)as avg from GradeInfoEntity g where g.teacher = :teacher group by g.subject")
    ScoreAvgBySubjectVO avgBySubject(@Param("teacher") TeacherInfo teacher);

    @Query("select (select sum(g.grade) from GradeInfoEntity g where g.test = :test and g.student = g2.student) as totalSum, Group_concat(Distinct g2.student) as student "
        +"from GradeInfoEntity g2 group by totalSum "
        +"having count(DISTINCT g2.student) >=2"
    )
    List<SameGrade> sameGrade(@Param("test") TestInfoEntity test);
    @EntityGraph(attributePaths = {"subject"})
    List<GradeInfoEntity> findByTestAndStudent(TestInfoEntity test, StudentInfo student);
}
