package com.project.lms.vo;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

public record ScoreAvgStatsListByClassVO(
    @Schema(description = "과목별 평균 점수 리스트")
    List<ScoreAvgListBySubjectVO> scoreList,
    @Schema(description = "반 이름")
    String className
    ) {
} 
