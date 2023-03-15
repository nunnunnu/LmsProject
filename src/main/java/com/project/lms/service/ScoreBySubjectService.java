package com.project.lms.service;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

import com.project.lms.entity.member.MemberInfoEntity;

import org.springframework.http.HttpStatus;
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
					.scoreList(voList).code(HttpStatus.OK).build(); // responseVO에 담는다.
			return result; // 출력한다.
		}
		// 그게 아니라면 
		ScoreListBySubjectResponseVO result = ScoreListBySubjectResponseVO.builder().message("성공").status(true)
				.scoreList(voList).code(HttpStatus.OK).build(); // responseVO에 담는다.
		return result; // 출력한다.
	}

	

	// 올해 시험 정보(과목 명, 점수) & 성적 통계 메세지 출력
	
	public ScoreListBySubjectYearResponseVO getSubjectList2(String id) {
		MemberInfoEntity memEntity = memberInfoRepository.findByMiId(id); // 토큰을 가진 학생의 정보를 가져온다.
		Long seq = memEntity.getMiSeq(); // 학생의 고유번호를 변수 seq로 받는다.
		List<ScoreListBySubjectYearVO> voList = gradeInfoRepository.findByYearScoreList(seq); // 이번년 과목별 성적을 list로 받는다.
		List<Object> ExplantionList  = new LinkedList<>(); // 성적 통계 설명을 넣을 list를 생성한다.
			Integer score_difference1 = voList.get(voList.size() - 1).getComprehension() // 전 월달의 독해 과목의 점수차이
					- voList.get(voList.size() - 2).getComprehension();
			ExplantionList.add("전 월달에 비해 독해과목이 " + score_difference1 + "점 "
					+ (score_difference1 > 0 ? "올랐습" : score_difference1 < 0 ? "내려갔습" : "동일합") + "니다.");
			
			Integer score_difference2 = voList.get(voList.size() - 1).getGrammer() // 전 월달의 문법 과목의 점수차이
			- voList.get(voList.size() - 2).getGrammer();
			ExplantionList.add("전 월달에 비해 문법과목이 " + score_difference2 + "점 "
					+ (score_difference2 > 0 ? "올랐습" : score_difference2 < 0 ? "내려갔습" : "동일합") + "니다.");

		Integer score_difference3 = voList.get(voList.size() - 1).getListening() // 전 월달의 듣기 과목의 점수차이
			- voList.get(voList.size() - 2).getListening();
			ExplantionList.add("전 월달에 비해 듣기과목이 " + score_difference3 + "점 "
					+ (score_difference3 > 0 ? "올랐습" : score_difference3 < 0 ? "내려갔습" : "동일합") + "니다.");

		Integer score_difference4 = voList.get(voList.size() - 1).getVocabulary() // 전 월달의 어휘 과목의 점수차이
			- voList.get(voList.size() - 2).getVocabulary();
		ExplantionList.add("전 월달에 비해 어휘과목이 " + score_difference4 + "점 "
					+ (score_difference4 > 0 ? "올랐습" : score_difference4 < 0 ? "내려갔습" : "동일합") + "니다.");		
		ScoreListBySubjectYearResponseVO result = new ScoreListBySubjectYearResponseVO("올해 시험 과목 별 점수 조회 성공", true, HttpStatus.OK,
				voList, ExplantionList);
				

		return result;

	}
	
	// // 이번 년 과목 별 평균 구해서 취약 과목 알아내기
	// public ScoreListBySubjectYearResponseVO getSubjectAvgList(String id) {
	// 	MemberInfoEntity memEntity = memberInfoRepository.findByMiId(id); // 토큰을 가진 학생의 정보를 가져온다.
	// 	Long seq = memEntity.getMiSeq(); // 학생의 고유번호를 변수 seq로 받는다.
	// 	List<ScoreListBySubjectYearVO> voList = gradeInfoRepository.findByYearScoreList(seq); // 이번년 과목별 성적을 list로 받는다.
	// 	List<ScoreAvgBySubjectVO> avgList = new LinkedList<>();
		
	// 	for (ScoreListBySubjectYearVO vo : voList) {

	// 	}


	// 	return null;
	// }
	
}
