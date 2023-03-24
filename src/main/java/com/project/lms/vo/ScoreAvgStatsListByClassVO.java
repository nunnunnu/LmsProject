package com.project.lms.vo;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
public class ScoreAvgStatsListByClassVO{
    @Schema(description = "과목별 평균 점수 리스트")
    private Map<String, Double> scoreList;
    @Schema(description = "반 이름")
    private String className;

    public ScoreAvgStatsListByClassVO(List<ScoreAvgListBySubjectVO> scoreList, String className){
        this.className = className;
        if(scoreList.size()!=0){
            this.scoreList = new LinkedHashMap<>();
            for(ScoreAvgListBySubjectVO s : scoreList){
                this.scoreList.put(s.getSubject(), s.getAvg());
            }
        }
    }
} 
