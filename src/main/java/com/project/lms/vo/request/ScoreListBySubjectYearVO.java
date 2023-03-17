package com.project.lms.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;

public interface ScoreListBySubjectYearVO {
    @Schema(description = "독해")
    Integer getComprehension();
    @Schema(description = "어휘")
    Integer getVocabulary();
    @Schema(description = "문법")
    Integer getGrammar();
    @Schema(description = "듣기")
    Integer getListening();
    @Schema(description = "시험 명")
    String getTestName();
    

     
}
