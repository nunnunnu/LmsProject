package com.project.lms;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.project.lms.entity.GradeInfoEntity;
import com.project.lms.entity.TestInfoEntity;
import com.project.lms.entity.member.StudentInfo;
import com.project.lms.entity.member.TeacherInfo;
import com.project.lms.repository.GradeInfoRepository;
import com.project.lms.repository.SubjectInfoRepository;
import com.project.lms.repository.TestInfoRepository;
import com.project.lms.repository.member.MemberInfoRepository;
import com.project.lms.repository.member.StudentInfoRepository;
import com.project.lms.vo.request.ScoreListBySubjectVO;

import jakarta.transaction.Transactional;

@SpringBootTest
class LmsApplicationTests {
	@Autowired
	GradeInfoRepository gradeInfoRepository;

	@Autowired
	MemberInfoRepository memberInfoRepository;

	@Autowired
	StudentInfoRepository studentInfoRepository;
	@Autowired
	SubjectInfoRepository subjectInfoRepository;

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
	@Test
	void currentMonthScoreList() {
		LocalDate now = LocalDate.now();
		// 오늘 날짜 "yyyy-MM" 으로 포맷하기

		String localDateTimeFormat1 = now.format(DateTimeFormatter.ofPattern("yyyy-MM"));
		List<TestInfoEntity> testList = testInfoRepository.findAll();
		for (TestInfoEntity monthScoreList : testList) {
			LocalDate testDate = monthScoreList.getTestDate();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
			String strDate = testDate.format(formatter);
			if (localDateTimeFormat1.equals(strDate)) {
				System.out.println(monthScoreList.getTestName());
			}
		}
	}
	
	@Test
	@Transactional
	void PrintVO() {
		Long student = 2L;
		StudentInfo memEntity = studentInfoRepository.findById(student).get();
		List<GradeInfoEntity> stuList = gradeInfoRepository.findByStudent(memEntity);
		
		for (GradeInfoEntity grade : stuList) {
		
			ScoreListBySubjectVO gradeBySubjectList = ScoreListBySubjectVO.builder()
					.grade(grade.getGrade())
					.subject(grade.getSubject())
					.build();
		}

	}
}