package com.project.lms.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;

public interface ScoreRankBySubjectVO {
    @Schema(description = "학생 이름") 
    String getName();
    @Schema(description = "점수") 
    String getScore();
    @Schema(description = "랭킹") 
    String getRanking();
}
