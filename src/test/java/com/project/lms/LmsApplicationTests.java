package com.project.lms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.doubleThat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.project.lms.entity.GradeInfoEntity;
import com.project.lms.entity.TestInfoEntity;
import com.project.lms.entity.member.MemberInfoEntity;
import com.project.lms.entity.member.StudentInfo;
import com.project.lms.entity.member.TeacherInfo;
import com.project.lms.entity.member.enumfile.Role;
import com.project.lms.repository.GradeInfoRepository;
import com.project.lms.repository.SubjectInfoRepository;
import com.project.lms.repository.TestInfoRepository;
import com.project.lms.repository.member.MemberInfoRepository;
import com.project.lms.repository.member.StudentInfoRepository;
import com.project.lms.service.MemberSecurityService;
import com.project.lms.service.ScoreBySubjectService;
import com.project.lms.vo.LoginVO;
import com.project.lms.vo.request.ScoreAvgBySubject2VO;
import com.project.lms.vo.request.ScoreAvgBySubjectVO;
import com.project.lms.vo.request.ScoreListBySubjectVO;
import com.project.lms.vo.request.ScoreListBySubjectYearVO;
import com.project.lms.vo.response.ScoreListBySubjectResponseVO;

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

	@Autowired
	ScoreBySubjectService scoreBySubjectService;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	MemberSecurityService memberSecurityService;

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
					.subjectName(grade.getSubject().getSubName())
					.build();
		}

	}

	@Test
	void getId() {
		String id = "hyuk1";
		MemberInfoEntity memEntity = memberInfoRepository.findByMiId(id);
		System.out.println(memEntity.getMiId());

	}

	@Test
	void Year() {
		LocalDate now = LocalDate.now();
		System.out.println(now.getYear());
		List<TestInfoEntity> testList = testInfoRepository.findAll();
		for (TestInfoEntity monthScoreList : testList) {
			if (now.getYear() == monthScoreList.getTestDate().getYear()) {
				System.out.println("ok");
			}

		}
	}

	@Test
	void Query() {
		Long student = 2L;
		List<ScoreListBySubjectYearVO> voList = gradeInfoRepository.findByYearScoreList(student);
		for (ScoreListBySubjectYearVO vo : voList) {
			System.out.println(vo.getComprehension());
		}
	}

	@Test
	void 더블_list_min_값찾기() {
		Long seq = 2L;
		ScoreAvgBySubjectVO vo = gradeInfoRepository.findByAvgBySubject(seq);

		// Double[] doubles = {vo.getAvgComprehension(), vo.getAvgGrammar(), vo.getAvgListening(), vo.getAvgVocabulary()};
		List<Double> doubleList = new LinkedList<>();
		doubleList.add(vo.getAvgComprehension());
		doubleList.add(vo.getAvgGrammar());
		doubleList.add(vo.getAvgListening());
		doubleList.add(vo.getAvgVocabulary());

		double min = doubleList.stream().mapToDouble(Double::doubleValue).min().getAsDouble();
		System.out.println(min);

		// double[] doubleArray = Arrays.stream(doubles).mapToDouble(Double::doubleValue).toArray();
		// DoubleStream doubleStream = DoubleStream.of(doubleArray);
		// double minValue = doubleStream.min().orElseThrow(NoSuchElementException::new);
		// Double minValue = DoubleList.stream().mapToDouble(Double::doubleValue).min().orElseThrow(NoSuchElementException::new);
	}

	@Test
	void 가장_낮은_평균_찾기() {
		Long seq = 2L;
		List<ScoreAvgBySubject2VO> voList = gradeInfoRepository.findByAvgBySubject2(seq);
		Double min = null;
		for (ScoreAvgBySubject2VO vo : voList) {
			min = vo.getAvg();
		}

		Double resultValue = null;
		for (ScoreAvgBySubject2VO vo1 : voList) {
			if (vo1.getAvg() < min) {
				resultValue = vo1.getAvg();
			}
		}
	}

	@Test 
	void 가장_약한_과목_찾기(){
		Long seq = 2L;
		List<ScoreAvgBySubject2VO> voList = gradeInfoRepository.findByAvgBySubject2(seq);
		Double min = null;
		for (ScoreAvgBySubject2VO vo : voList) {
			min = vo.getAvg();
		}

		Double resultValue = null;
		for (ScoreAvgBySubject2VO vo1 : voList) {
			if (vo1.getAvg() < min) {
				resultValue = vo1.getAvg();
			}
		}
	
		List<String> WeeknessSubjectList = new LinkedList<>();
		for (ScoreAvgBySubject2VO vo2 : voList) {
			if (vo2.getAvg() == resultValue) {
				WeeknessSubjectList.add(vo2.getSubject());
			}
		}
		System.out.println(WeeknessSubjectList.get(0));

		}
		
		@Test
		void 가장최근테스트정보찾기() {
			List<TestInfoEntity> tList = testInfoRepository.findAll();
			Long seq = null;
			for (TestInfoEntity t : tList) {
				seq = t.getTestSeq();
			}
			System.out.println(seq);
		}
		

		@Test
		void 마스터계정_제외한_멤버조회() {
			Pageable page = null;
			Page<MemberInfoEntity> memberList = memberInfoRepository.findByMiRoleNotAndMiStatus(Role.MASTER, true, page);
			System.out.println(memberList.getTotalElements());
		}
		@Test
		void 키워드_포함한_멤버_조회() {
			Pageable page = null;
			String keyword = "이";
			Page<MemberInfoEntity> memberList = memberInfoRepository.findByMiNameContainingAndMiStatus(keyword, true, page);
			System.out.println(memberList.getTotalElements());
		}

	}
	
