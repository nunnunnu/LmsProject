package com.project.lms.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;

public interface ScoreAvgBySubjectVO {
    @Schema(description = "독해 평균")
    Double getAvgComprehension();
    @Schema(description = "어휘 평균")
    Double getAvgVocabulary(); 
    @Schema(description = "문법 평균")
    Double getAvgGrammar();
    @Schema(description = "듣기 평균")
    Double getAvgListening(); 
 

}
