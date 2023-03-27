package com.project.lms.repository.custom;

import java.util.List;

import com.project.lms.entity.SubjectInfoEntity;
import com.project.lms.entity.TestInfoEntity;
import com.project.lms.vo.ScoreAllListBySubjectVO;
import com.project.lms.vo.grade.StudentClassGradeVO;


public interface GradeInfoRepositoryCustom {
    List<StudentClassGradeVO> studentClassChange(TestInfoEntity test);

    List<ScoreAllListBySubjectVO> scoreBySubject(TestInfoEntity test, SubjectInfoEntity reading, SubjectInfoEntity vocabulary, SubjectInfoEntity grammar, SubjectInfoEntity listening);
}
