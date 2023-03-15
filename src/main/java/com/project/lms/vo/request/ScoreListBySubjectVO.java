package com.project.lms.vo.request;

import com.project.lms.entity.SubjectInfoEntity;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "과목 명")
    private String subjectName;
    @Schema(description = "과목 점수")
    private Integer grade;


}   
