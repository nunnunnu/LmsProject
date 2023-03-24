package com.project.lms.vo;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.swagger.v3.oas.annotations.media.Schema;

public class ScoreListByYearMonthVO {
    @Schema(description = "학생 명")
    String name;
    @Schema(description = "과목별 점수 리스트")
    List<ScoreListBySubjectVO> scoreList;
    @Schema(description = "반 정보")
    String className;

    public ScoreListByYearMonthVO(String name, List<ScoreListBySubjectVO> scoreList, String className){
        this.name = name;
        this.className = className;
        this.scoreList = scoreList;
        // if(scoreList.size()!=0){
        //     this.scoreList = new LinkedHashMap<>();
        //     for(ScoreListBySubjectVO s : scoreList){
        //         this.scoreList.put(s.getSubject(), s.getScore());
        //     }
        // }
    }
}
