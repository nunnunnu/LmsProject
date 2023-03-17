package com.project.lms.repository.custom;

import java.util.List;

import com.project.lms.entity.TestInfoEntity;
import com.project.lms.vo.grade.StudentClassGradeVO;

public interface GradeInfoRepositoryCustom {
    List<StudentClassGradeVO> studentClassChange(TestInfoEntity test);
}
