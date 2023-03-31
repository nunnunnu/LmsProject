package com.project.lms.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.assertj.core.api.Assertions;
import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.project.lms.repository.GradeInfoRepository;
import com.project.lms.repository.SubjectInfoRepository;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
class ScoreAvgTotalServiceTest {
    
    @Autowired private SubjectInfoRepository subjectInfoRepository;
    @Autowired private GradeInfoRepository gradeInfoRepository; 

    @DisplayName("과목별 상위 10프로 평균 점수")
    @Test
    void top_10_avg_score() {
        // var subjects = subjectInfoRepository.findAll();
        // List<AvgScoreTop10PerSubject> test = new ArrayList<>();
        // Map<String, Double> test2 = new HashMap<>();
        // for(var s : subjects) {
        //     var scores = gradeInfoRepository.findAllBySubjectOrderByGradeDesc(s);
        //     double top10 =  scores.size() * 0.1;
        //     var sum = 0;
        //     for(int i = 0; i < top10; i++) {
        //         sum += scores.get(i).getGrade();
        //     }
        //     var avg = sum / (double) top10;
        //     test2.put(s.getSubName(), avg);
        // }

        // Assertions.assertThat(test2.size()).isEqualTo(4);
        // // 독해 어휘 문법 듣기
        // Assertions.assertThat(test2.get("독해")).isCloseTo(95.9167, Percentage.withPercentage(0.001));
        // Assertions.assertThat(test2.get("어휘")).isCloseTo(95.8500, Percentage.withPercentage(0.001));
        // Assertions.assertThat(test2.get("문법")).isCloseTo(96.4333, Percentage.withPercentage(0.001));
        // Assertions.assertThat(test2.get("듣기")).isCloseTo(95.7500, Percentage.withPercentage(0.001));
    }
    
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private class AvgScoreTop10PerSubject {
        private String subjectName;
        private Double avgScore;
    }
}

