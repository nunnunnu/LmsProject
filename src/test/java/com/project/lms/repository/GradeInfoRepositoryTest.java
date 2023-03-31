package com.project.lms.repository;

import org.assertj.core.api.Assertions;
import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import com.project.lms.entity.GradeInfoEntity;
import com.project.lms.entity.SubjectInfoEntity;
import com.project.lms.repository.GradeInfoRepository;
import com.project.lms.repository.SubjectInfoRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
class GradeInfoRepositoryTest {
    
    @PersistenceContext
    private EntityManager em;

    @Autowired private SubjectInfoRepository subjectInfoRepository;
    @Autowired private GradeInfoRepository gradeInfoRepository;

    @Test
    void 성적순_전체리스트_내림차순() {
        // given
        // var subject = subjectInfoRepository.findById(4L).get();

        // when
        // String jpql = "select g from GradeInfoEntity g where g.subject = :subject order by g.grade desc"; 
        // var grades =  em.createQuery(jpql, GradeInfoEntity.class).setParameter("subject", subject).getResultList();

        // var grades = gradeInfoRepository.findAllBySubjectOrderByGradeDesc(subject);

        // then
        // Assertions.assertThat(grades.size()).isEqualTo(600);
        // Assertions.assertThat(grades.get(0).getGrade()).isEqualTo(100);        

    }

    @Test
    void 성적순_테스트순_상위10프로수() {
    //     var list = gradeInfoRepository.getScoreTestTop10();
    //     Assertions.assertThat(list.get(0).getSub().getSubSeq()).isEqualTo(1L);
    //     Assertions.assertThat(list.get(0).getTest().getTestSeq()).isEqualTo(1L);
    //     Assertions.assertThat(list.get(0).getCount()).isCloseTo(10.0, Percentage.withPercentage(0.00001));

    //     Assertions.assertThat(list.get(9).getSub().getSubSeq()).isEqualTo(2L);
    //     Assertions.assertThat(list.get(9).getTest().getTestSeq()).isEqualTo(3L);
    //     Assertions.assertThat(list.get(9).getCount()).isCloseTo(10.0, Percentage.withPercentage(0.00001));

    //     var grades1 = gradeInfoRepository.findAllBySubjectAndTestOrderByGradeDescTopCount(list.get(0).getSub(), list.get(0).getTest(), list.get(0).getCount());
    //     var avg1 = grades1.stream().mapToDouble(GradeInfoEntity::getGrade).average().orElse(0);
    //     log.info("avg1 = {}", avg1);

    //     Assertions.assertThat(avg1).isCloseTo(97.2000, Percentage.withPercentage(0.00001));

    //     var grades2 = gradeInfoRepository.findAllBySubjectAndTestOrderByGradeDescTopCount(list.get(7).getSub(), list.get(7).getTest(), list.get(7).getCount());
    //     var avg2 = grades2.stream().mapToDouble(GradeInfoEntity::getGrade).average().orElse(0);
    //     log.info("avg2 = {}", avg2);

    //     Assertions.assertThat(avg2).isCloseTo(96.7000, Percentage.withPercentage(0.00001));
    }
}
