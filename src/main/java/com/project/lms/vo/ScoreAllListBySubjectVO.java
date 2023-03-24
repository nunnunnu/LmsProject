package com.project.lms.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScoreAllListBySubjectVO {
    @Schema(description = "학생 시퀀스 번호")
    private Long seq;
    @Schema(description = "학생 명")
    private String name;
    @Schema(description = "독해") 
    private Integer reading;
    @Schema(description = "어휘") 
    private Integer vocabulary;
    @Schema(description = "문법") 
    private Integer grammar;
    @Schema(description = "듣기") 
    private Integer listening;
    @Schema(description = "반 명")
    private String className;
}
