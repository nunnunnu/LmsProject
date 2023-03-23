package com.project.lms.vo.grade;

import java.util.LinkedHashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScoreTop10Response {
    private String testName;
    private Map<String, Double> map = new LinkedHashMap<>();


}
