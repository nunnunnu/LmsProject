package com.project.lms.vo.grade;

import com.project.lms.entity.SubjectInfoEntity;
import com.project.lms.entity.TestInfoEntity;

public interface ScoreTestTop10VO {
    SubjectInfoEntity getSub();
    TestInfoEntity getTest();
    Double getCount();
}
