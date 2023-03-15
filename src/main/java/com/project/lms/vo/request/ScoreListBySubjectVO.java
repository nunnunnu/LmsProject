package com.project.lms.vo.request;

import com.project.lms.entity.SubjectInfoEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScoreListBySubjectVO {
    //  private SubjectInfoEntity subject;
    private String subjectName;
    private Integer grade;


}   
