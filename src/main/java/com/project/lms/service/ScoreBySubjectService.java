package com.project.lms.service;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.project.lms.entity.GradeInfoEntity;
import com.project.lms.entity.SubjectInfoEntity;
import com.project.lms.entity.TestInfoEntity;
import com.project.lms.entity.member.MemberInfoEntity;
import com.project.lms.entity.member.StudentInfo;
import com.project.lms.error.custom.NoContentsException;
import com.project.lms.error.custom.NotFoundMemberException;
import com.project.lms.error.custom.NotFoundStudent;
import com.project.lms.error.custom.NotFoundSubject;
import com.project.lms.error.custom.NotFoundTest;
import com.project.lms.error.custom.NotFoundTestException;
import com.project.lms.entity.member.MemberInfoEntity;
import com.project.lms.entity.member.StudentInfo;
import com.project.lms.entity.member.TeacherInfo;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.project.lms.entity.ClassInfoEntity;
import com.project.lms.entity.ClassStudentEntity;
import com.project.lms.entity.ClassTeacherEntity;
import com.project.lms.entity.GradeInfoEntity;
import com.project.lms.repository.ClassInfoRepository;
import com.project.lms.repository.ClassStudentRepository;
import com.project.lms.repository.ClassTeacherRepository;
import com.project.lms.repository.GradeInfoRepository;
import com.project.lms.repository.SubjectInfoRepository;
import com.project.lms.repository.TestInfoRepository;
import com.project.lms.repository.member.MemberInfoRepository;
import com.project.lms.repository.member.StudentInfoRepository;
import com.project.lms.vo.GradeVO;
import com.project.lms.vo.grade.SameGrade;
import com.project.lms.vo.grade.SameGraderesponse;
import com.project.lms.vo.grade.StudentSubjectInfo;
import com.project.lms.repository.member.TeacherInfoRepository;
import com.project.lms.vo.request.ScoreAvgBySubject2VO;
import com.project.lms.vo.request.ScoreAvgBySubjectVO;
import com.project.lms.vo.request.ScoreListBySubjectVO;
import com.project.lms.vo.request.ScoreListBySubjectYearVO;
import com.project.lms.vo.response.ScoreListBySubjectResponseVO;
import com.project.lms.vo.response.ScoreListBySubjectYearResponseVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ScoreBySubjectService {
	private final StudentInfoRepository studentInfoRepository;
	private final GradeInfoRepository gradeInfoRepository;
	private final MemberInfoRepository memberInfoRepository;
	private final TestInfoRepository testInfoRepository;
	private final ClassStudentRepository classStudentRepository;
	private final ClassInfoRepository classInfoRepository;
	private final TeacherInfoRepository teacherInfoRepository;
	private final ClassTeacherRepository classTeacherRepository;
	private final SubjectInfoRepository subjectInfoRepository;

	// 선생님 or 직원의 담당 반 학생 이번 달 시험정보(과목명, 점수) 출력
	public ScoreListBySubjectResponseVO getStuSubjectList(UserDetails userDetails, Long student) {
		StudentInfo sInfo = studentInfoRepository.findById(student).orElse(null);
		if (sInfo == null) { // 학생 정보가 없다면
			ScoreListBySubjectResponseVO result = new ScoreListBySubjectResponseVO("번호가 존재하지 않거나, 잘못된 학생 고유번호입니다.",
					false, HttpStatus.BAD_REQUEST, null);
			return result; // 메세지와 코드, 상태값을 반환함.           
		}
		MemberInfoEntity mInfo = memberInfoRepository.findByMiId(userDetails.getUsername());

		if (mInfo.getMiRole().toString().equals("EMPLOYEE")) { // 권한이 "EMPLOYEE"와 같다면
			String eId = userDetails.getUsername(); // 로그인한 직원의 ID를 찾아
			MemberInfoEntity eInfo = memberInfoRepository.findByMiId(eId); // 정보를 가져온다.
			ClassInfoEntity cInfo = classInfoRepository.findByEmployee(eInfo); // 반 정보를 가져온다.
			if (cInfo == null) { // 직원의 반 정보가 없으면
				ScoreListBySubjectResponseVO result = new ScoreListBySubjectResponseVO("담당하고 있는 반이 없습니다.", false,
						HttpStatus.BAD_REQUEST, null); // 메세지와 코드, 상태값을 반환함.
				return result;
			}
			ClassStudentEntity csInfo = classStudentRepository.findByStudent(sInfo);
			if (csInfo == null || cInfo.getCiSeq() != csInfo.getClassInfo().getCiSeq()) { // 만약 해당 학생의 반 번호와 로그인한 직원의 반 번호가 같지 않으면
				ScoreListBySubjectResponseVO result = new ScoreListBySubjectResponseVO(
						"담당 반의 학생이 아니거나 해당 학생의 반 정보를 찾을 수 없습니다.", false,
						HttpStatus.BAD_REQUEST, null); // 메세지와 코드, 상태값을 반환함.
				return result;
			}
		}

		else if (mInfo.getMiRole().toString().equals("TEACHER")) { // 권한이 "TEACHER"와 같다면
			String tId = userDetails.getUsername(); // 해당 토큰의 아이디를 변수 tId에 넣음.
			TeacherInfo tInfo = teacherInfoRepository.findByMiId(tId); // 아이디를 가지고 로그인한 선생님의 정보를 찾음. 
			ClassTeacherEntity cmInfo = classTeacherRepository.findByTeacher(tInfo); // 해당 선생님의 반 정보를 찾음.
			if (cmInfo == null) {
				ScoreListBySubjectResponseVO result = new ScoreListBySubjectResponseVO("담당하고 있는 반이 없습니다.", false,
						HttpStatus.BAD_REQUEST, null); // 메세지와 코드, 상태값을 반환함.
				return result;
			}
			ClassStudentEntity csInfo = classStudentRepository.findByStudent(sInfo); // 학생 고유번호를 갖고 학생 정보를 찾음.
			if (csInfo == null || cmInfo.getClassInfo().getCiSeq() != csInfo.getClassInfo().getCiSeq()) { // 만약 해당 학생의 반 번호와 로그인한 선생님의 반 번호가 같지 않으면
				ScoreListBySubjectResponseVO result = new ScoreListBySubjectResponseVO(
						"담당 반의 학생이 아니거나 해당 학생의 반 정보를 찾을 수 없습니다.", false,
						HttpStatus.BAD_REQUEST, null); // 메세지와 코드, 상태값을 반환함.
				return result;
			}
		}
		ScoreListBySubjectResponseVO result = getSubjectList(sInfo.getMiId());
		return result;
	}

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
		List<Object> explanationList1 = new LinkedList<>(); // 성적 통계 설명을 넣을 list를 생성한다.
		Integer score_difference1 = voList.get(voList.size() - 1).getComprehension() // 전 월달의 독해 과목의 점수차이
				- voList.get(voList.size() - 2).getComprehension();
		explanationList1.add("전 월달에 비해 독해과목이 " + score_difference1 + "점 "
				+ (score_difference1 > 0 ? "올랐습" : score_difference1 < 0 ? "내려갔습" : "동일합") + "니다.");

		Integer score_difference2 = voList.get(voList.size() - 1).getGrammar() // 전 월달의 문법 과목의 점수차이
				- voList.get(voList.size() - 2).getGrammar();
		explanationList1.add("전 월달에 비해 문법과목이 " + score_difference2 + "점 "
				+ (score_difference2 > 0 ? "올랐습" : score_difference2 < 0 ? "내려갔습" : "동일합") + "니다.");

		Integer score_difference3 = voList.get(voList.size() - 1).getListening() // 전 월달의 듣기 과목의 점수차이
				- voList.get(voList.size() - 2).getListening();
		explanationList1.add("전 월달에 비해 듣기과목이 " + score_difference3 + "점 "
				+ (score_difference3 > 0 ? "올랐습" : score_difference3 < 0 ? "내려갔습" : "동일합") + "니다.");

		Integer score_difference4 = voList.get(voList.size() - 1).getVocabulary() // 전 월달의 어휘 과목의 점수차이
				- voList.get(voList.size() - 2).getVocabulary();
		explanationList1.add("전 월달에 비해 어휘과목이 " + score_difference4 + "점 "
				+ (score_difference4 > 0 ? "올랐습" : score_difference4 < 0 ? "내려갔습" : "동일합") + "니다.");
		
		String resultExplanation = ""; // String 타입의 변수를 하나 만들고
		for (Object explanation : explanationList1) { // for문을 돌려서
				if (StringUtils.hasText(resultExplanation)) { // resultExplanation에 공백이 없으면
					resultExplanation += "\n"; // 줄바꿈으로 나눈다.
				}	
				resultExplanation += explanation; 
			}
			ScoreListBySubjectYearResponseVO result = ScoreListBySubjectYearResponseVO.builder().code(HttpStatus.OK)
			.message("올해 과목 별 점수 조회 및 통계 메세지 및 취약과목 출력 성공")
			.scoreList(voList)
			.status(true)
			.explanation(resultExplanation)
			.weeknessSubject(getWeaknessSubject(id)).build();

			return result;
		}
	
	// 선생님 or 직원의 담당 반 학생  올해 시험 정보(과목 명, 점수) & 성적 통계 메세지 출력
	public ScoreListBySubjectYearResponseVO getStuSubjectList2(UserDetails userDetails, Long student) {
		StudentInfo sInfo = studentInfoRepository.findById(student).orElse(null);
		if (sInfo == null) { // 학생 정보가 없다면
			ScoreListBySubjectYearResponseVO result = new ScoreListBySubjectYearResponseVO(
					"번호가 존재하지 않거나, 잘못된 학생 고유번호입니다.", false, HttpStatus.BAD_REQUEST, null, null, null);
			return result; // 메세지와 코드, 상태값을 반환함.           
		}
		MemberInfoEntity mInfo = memberInfoRepository.findByMiId(userDetails.getUsername()); // 로그인한 사람의 정보를 가져온다.

		if (mInfo.getMiRole().toString().equals("EMPLOYEE")) { // 권한이 "EMPLOYEE"와 같다면
			String eId = userDetails.getUsername(); // 아이디를 찾고
			MemberInfoEntity eInfo = memberInfoRepository.findByMiId(eId); // 해당 로그인한 사람의 정보를 찾아서
			ClassInfoEntity cInfo = classInfoRepository.findByEmployee(eInfo); // 반 정보를 찾는다.
			if (cInfo == null) { // 반 정보가 없으면 
				ScoreListBySubjectYearResponseVO result = new ScoreListBySubjectYearResponseVO("담당하고 있는 반이 없습니다.",
						false, HttpStatus.BAD_REQUEST, null, null, null);
				return result;
			}
			ClassStudentEntity csInfo = classStudentRepository.findByStudent(sInfo); // 학생의 반 정보를 찾는다.
			if (csInfo == null || cInfo.getCiSeq() != csInfo.getClassInfo().getCiSeq()) { // 만약 해당 학생의 반 번호와 로그인한 직원 or 선생님의 반 번호가 같지 않으면
				ScoreListBySubjectYearResponseVO result = new ScoreListBySubjectYearResponseVO(
						"담당 반의 학생이 아니거나 해당 학생의 반 정보를 찾을 수 없습니다.", false, HttpStatus.BAD_REQUEST, null, null, null);
				return result;
			}
		}

		else if (mInfo.getMiRole().toString().equals("TEACHER")) { // 권한이 "TEACHER"와 같다면
			String tId = userDetails.getUsername(); // 해당 토큰의 아이디를 변수 tId에 넣음.
			TeacherInfo tInfo = teacherInfoRepository.findByMiId(tId); // 아이디를 가지고 로그인한 직원 or 선생님의 정보를 찾음. 
			ClassTeacherEntity cmInfo = classTeacherRepository.findByTeacher(tInfo); // 해당 직원 or 선생님의 반 정보를 찾음.
			if (cmInfo == null) { // 선생님의 반 정보가 없다면
				ScoreListBySubjectYearResponseVO result = new ScoreListBySubjectYearResponseVO("담당하고 있는 반이 없습니다.",
						false, HttpStatus.BAD_REQUEST, null, null, null);
				return result; // 메세지와 코드, 상태값을 반환함.           
			}
			ClassStudentEntity csInfo = classStudentRepository.findByStudent(sInfo); // 학생 고유번호를 갖고 학생 정보를 찾음.
			if (csInfo == null || cmInfo.getClassInfo().getCiSeq() != csInfo.getClassInfo().getCiSeq()) { // 만약 해당 학생의 반 번호와 로그인한 직원 or 선생님의 반 번호가 같지 않으면
				ScoreListBySubjectYearResponseVO result = new ScoreListBySubjectYearResponseVO(
						"담당 반의 학생이 아니거나 해당 학생의 반 정보를 찾을 수 없습니다.", false, HttpStatus.BAD_REQUEST, null, null, null);
				return result;
			}
		}
		ScoreListBySubjectYearResponseVO result = getSubjectList2(sInfo.getMiId()); // 학생의 올해 시험 과목 별 점수 조회 리스트 가져오기
		return result;
	}

	// 가장 약한 과목 찾기 메서드
	public String getWeaknessSubject(String id) {
		MemberInfoEntity memEntity = memberInfoRepository.findByMiId(id); // 토큰을 가진 학생의 정보를 가져온다.
		Long seq = memEntity.getMiSeq(); // 학생의 고유번호를 변수 seq로 받는다.

		List<ScoreAvgBySubject2VO> voList = gradeInfoRepository.findByAvgBySubject2(seq); // gradeInfoRepository의 기능을 들고와 과목 별 통계정보를 가져온다.
		Double min = voList.get(0).getAvg(); // 기준점이 되는 값을 하나 설정하여
		Double resultValue = null;
		for (ScoreAvgBySubject2VO vo1 : voList) { // 비교하며 최솟값을 찾는다.
			if (vo1.getAvg() <= min) {
				resultValue = vo1.getAvg();
			}
		}

		String weeknessSubject = ""; // 취약과목
		for (ScoreAvgBySubject2VO vo2 : voList) { // 과목별 통계 리스트를 반복돌려
			if (vo2.getAvg() == resultValue) { // 최솟값의 정보를 찾고
				if (StringUtils.hasText(weeknessSubject)) { // weaknessSubject에 공백이 없으면
					weeknessSubject += ", "; // 구분자 , 를 추가한다.
				}	
				weeknessSubject += vo2.getSubject(); // 최솟값의 과목명을 추가한다.
			}
		}
		return weeknessSubject; // 과목명을 반환한다.
	}

	//해당 시험의 동점자 조회
	public List<SameGraderesponse> sameGradeStudent(Long testSeq) {
		//입력받은 시험 번호로 시험 조회
		TestInfoEntity test = testInfoRepository.findById(testSeq).orElseThrow(() -> new NotFoundTestException());
		//찾을 수 없다면 NotFoundTestException 발생. ControllerSupport에서 오류 처리됨

		//같은 점수의 회원 조회
		List<SameGrade> list = gradeInfoRepository.sameGrade(test);

		if (list.size() == 0 || (list.size() == 1 && list.get(0).getTotalSum() == null)) {
			throw new NoContentsException(); //동점자가 없을 경우
		}
		List<SameGraderesponse> result = new ArrayList<>(); //최종으로 반환할 리스트 생성

		SameGraderesponse same = null; //리스트에 담을 객체 미리 생성
		for (SameGrade s : list) { //같은 점수의 회원을 for문을 돌려 데이터 가공
			String[] seqs = s.getStudent().split(","); //Group_concat으로 쉼표로 이어져 나온 회원번호를 분리해서 배열로 만듦
			//같은 점수끼리 묶어져있기때문에 같은 점수인 회원만 비교 가능
			if (s.getTotalSum() != null) {
				same = new SameGraderesponse(s.getTotalSum()); //객체에 동점 점수 세팅
				for (String seq : seqs) { //배열로 만든 회원번호를 for문을 돌려 각각 조회함
					StudentInfo student = studentInfoRepository.findById(Long.parseLong(seq))
							.orElseThrow(() -> new NotFoundMemberException()); //회원번호로 학생정보 조회
					List<GradeInfoEntity> entities = gradeInfoRepository.findByTestAndStudent(test, student); //해당 회원의 과목 정보 조회
					same.addStudentInfo(new StudentSubjectInfo(entities, student)); //list에 담을 객체 안의 list에 add함.
				}
				result.add(same); //최종적으로 반환될 result에 세팅한 객체 추가
			}
		}
		return result; //result 반환
	}
	
}
