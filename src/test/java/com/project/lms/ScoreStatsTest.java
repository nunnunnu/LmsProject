package com.project.lms;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.project.lms.repository.GradeInfoRepository;
import com.project.lms.repository.SubjectInfoRepository;

@SpringBootTest
@Transactional
public class ScoreStatsTest {
    @Autowired GradeInfoRepository gradeInfoRepository;
    @Autowired SubjectInfoRepository subjectInfoRepository;


    @DisplayName("반별 + 과목별 평균조회 → 월을 입력 받기")
    @Test
    void ScoreAvgListByClass() {
        for(long i=1l; i<=4l; i++){
            Assertions.assertThat(gradeInfoRepository.avgBySubject(gradeInfoRepository.findByCsSeq(1l), 202302));
        }
    }

    @DisplayName("과목별 점수 높은순or낮은순 학생 조회")
    @Test
    void ScoreLankListBySubject() {
        Assertions.assertThat(gradeInfoRepository.rankBySubject(subjectInfoRepository.findById(1l).get().getSubSeq()));
    }
}
