package com.project.lms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.lms.entity.GradeInfoEntity;
import com.project.lms.entity.TestInfoEntity;
import com.project.lms.entity.member.MemberInfoEntity;
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

}
