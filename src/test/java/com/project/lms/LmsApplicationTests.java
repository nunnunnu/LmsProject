package com.project.lms;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.project.lms.entity.GradeInfoEntity;
import com.project.lms.entity.TestInfoEntity;
import com.project.lms.entity.member.MemberInfoEntity;
import com.project.lms.entity.member.StudentInfo;
import com.project.lms.entity.member.TeacherInfo;
import com.project.lms.repository.GradeInfoRepository;
import com.project.lms.repository.TestInfoRepository;
import com.project.lms.repository.member.MemberInfoRepository;
import com.project.lms.repository.member.StudentInfoRepository;

@SpringBootTest
class LmsApplicationTests {
	@Autowired
	GradeInfoRepository gradeInfoRepository;

	@Autowired
	MemberInfoRepository memberInfoRepository;

	@Autowired
	StudentInfoRepository studentInfoRepository;

	@Autowired
	TestInfoRepository testInfoRepository;
	@Test
	void contextLoads() {
		TeacherInfo t = new TeacherInfo();

	}
	// 학생 조회하여 과목 점수 출력
	@Test
	void ScoreList() {
		Long student = 2L;
		StudentInfo memEntity = studentInfoRepository.findById(student).get();
		List<GradeInfoEntity> stuEntity = gradeInfoRepository.findByStudent(memEntity);

		for (GradeInfoEntity score : stuEntity) {
			System.out.println(score.getGrade());
		}
	}
	
	// 이번 달 과목 점수 출력
	// @Test
	// void currentMonthScoreList() {
	// 	 LocalDate now = LocalDate.now();
	// 	int monthValue = now.getMonthValue();
	// 	System.out.println(monthValue);
		 
	// 	List<TestInfoEntity> testList = testInfoRepository.findAll();

	// 	for (TestInfoEntity monthScoreList : testList) {
	// 		if(monthScoreList.getTestDate().getYear)
	// 	}
	// }

}
