package com.project.lms.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScoreAvgBySubjectVO {
    @Schema(description = "과목 명")
    private String subjectName;
    @Schema(description = "과목 점수")
    private Double avg; 
}
