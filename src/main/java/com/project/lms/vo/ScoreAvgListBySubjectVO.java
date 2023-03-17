package com.project.lms.vo;

import io.swagger.v3.oas.annotations.media.Schema;

public interface ScoreAvgListBySubjectVO {
    @Schema(description = "과목 명") 
    String getSubject();
    @Schema(description = "평균 점수")
    Integer getAvgGrade();
}
