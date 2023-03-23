package com.project.lms.vo;

import io.swagger.v3.oas.annotations.media.Schema;

public interface ScoreListBySubjectVO {
    @Schema(description = "독해") 
    String getReading();
    @Schema(description = "어휘") 
    String getVocabulary();
    @Schema(description = "문법") 
    String getGrammar();
    @Schema(description = "듣기") 
    String getListening();
    // @Schema(description = "점수")
    // Integer getScore();
}
