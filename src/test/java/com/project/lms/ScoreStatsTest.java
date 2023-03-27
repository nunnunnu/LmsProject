package com.project.lms;

import java.time.LocalDate;
import java.time.YearMonth;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.project.lms.entity.SubjectInfoEntity;
import com.project.lms.entity.TestInfoEntity;
import com.project.lms.repository.GradeInfoRepository;
import com.project.lms.repository.SubjectInfoRepository;
import com.project.lms.repository.TestInfoRepository;

@SpringBootTest
@Transactional
public class ScoreStatsTest {
    @Autowired GradeInfoRepository gradeInfoRepository;
    @Autowired SubjectInfoRepository subjectInfoRepository;
    @Autowired TestInfoRepository testInfoRepository;


    @DisplayName("연월별(시험별) 학생 전체 성적 조회")
    @Test
    void ScoreListByYearMonth() {
        LocalDate frist = LocalDate.of(YearMonth.of(2023, 03).getYear(), YearMonth.of(2023, 03).getMonth(), 1);
        LocalDate last = frist.withDayOfMonth(frist.lengthOfMonth());
        TestInfoEntity test = testInfoRepository.findbyStartAndEnd(frist, last);
        SubjectInfoEntity reading = subjectInfoRepository.findBySubSeq(1l);
        SubjectInfoEntity vocabulary = subjectInfoRepository.findBySubSeq(2l);
        SubjectInfoEntity grammar = subjectInfoRepository.findBySubSeq(3l);
        SubjectInfoEntity listening = subjectInfoRepository.findBySubSeq(4l);
        Assertions.assertThat(gradeInfoRepository.scoreBySubject(test, reading, vocabulary, grammar, listening));
    }


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
