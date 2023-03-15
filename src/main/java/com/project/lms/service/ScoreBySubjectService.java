package com.project.lms.service;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

import com.project.lms.entity.member.MemberInfoEntity;
import org.springframework.stereotype.Service;
import com.project.lms.entity.GradeInfoEntity;
import com.project.lms.repository.GradeInfoRepository;
import com.project.lms.repository.member.MemberInfoRepository;
import com.project.lms.vo.request.ScoreListBySubjectVO;
import com.project.lms.vo.request.ScoreListBySubjectYearVO;
import com.project.lms.vo.response.ScoreListBySubjectResponseVO;
import com.project.lms.vo.response.ScoreListBySubjectYearResponseVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ScoreBySubjectService {
    // private StudentInfoRepository studentInfoRepository;
    private final GradeInfoRepository gradeInfoRepository;
	private final MemberInfoRepository memberInfoRepository;
	
	// 이번 달 시험 정보(과목 명, 점수) 출력
	public ScoreListBySubjectResponseVO getSubjectList(String id) {
		LocalDate now = LocalDate.now(); // 현재 날짜 
		String localDateTimeFormat1 = now.format(DateTimeFormatter.ofPattern("yyyy-MM")); // 현재 날짜를 "yyyy-MM"으로 형식으로 바꾼다.
		MemberInfoEntity memEntity = memberInfoRepository.findByMiId(id); // 토큰의 아이디로 해당 회원의 정보를 찾는다.
		List<GradeInfoEntity> stuList = gradeInfoRepository.findByStudent(memEntity); // 해당 회원의 성적 정보를 찾는다. (과목별로 있기 떄문에 List로 받는다.)
		List<ScoreListBySubjectVO> voList = new LinkedList<>(); // 정보를 담을 list를 생성한다.
		for (GradeInfoEntity grade : stuList) { // 회원의 성적 정보를 for문으로 찾는다.
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
			String strDate = grade.getTest().getTestDate().format(formatter); // 해당 회원이 친 테스트의 날짜를 "yyyy-MM" 형식으로 바꾼다.
			if (localDateTimeFormat1.equals(strDate)) { //  이번달 시험 정보만 voList에 담을 수 있도록 한다.
				// vo에 과목 명, 과목 점수를 담는다. 
				ScoreListBySubjectVO vo = ScoreListBySubjectVO.builder()
						.subjectName(grade.getSubject().getSubName())
						.grade(grade.getGrade()).build();
				voList.add(vo); // vo를 리스트에 담는다.
			}
		}
		if (voList.isEmpty()) { // 만약에 volist에 아무것도 없다면
			ScoreListBySubjectResponseVO result = ScoreListBySubjectResponseVO.builder().message("이번달의 시험 정보가 없습니다.")
					.status(true)
					.scoreList(voList).build(); // responseVO에 담는다.
			return result; // 출력한다.
		}
		// 그게 아니라면 
		ScoreListBySubjectResponseVO result = ScoreListBySubjectResponseVO.builder().message("성공").status(true)
				.scoreList(voList).build(); // responseVO에 담는다.
		return result; // 출력한다.
	}

	

	// 올해 시험 정보(과목 명, 점수) 출력
	
	public ScoreListBySubjectYearResponseVO getSubjectList2(String id) {
		MemberInfoEntity memEntity = memberInfoRepository.findByMiId(id);
		Long seq = memEntity.getMiSeq();
		List<ScoreListBySubjectYearVO> voList = gradeInfoRepository.findByYearScoreList(seq);
		ScoreListBySubjectYearResponseVO vo = new ScoreListBySubjectYearResponseVO("이번 년 과목 별 성적 조회 성공", true, voList);
		return vo;
	}
	
	
}
