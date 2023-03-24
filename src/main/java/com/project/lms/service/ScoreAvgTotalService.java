package com.project.lms.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.project.lms.entity.GradeInfoEntity;
import com.project.lms.entity.SubjectInfoEntity;
import com.project.lms.entity.TestInfoEntity;
import com.project.lms.entity.member.StudentInfo;
import com.project.lms.repository.GradeInfoRepository;
import com.project.lms.repository.SubjectInfoRepository;
import com.project.lms.repository.TestInfoRepository;
import com.project.lms.repository.member.StudentInfoRepository;
import com.project.lms.vo.grade.ScoreTop10Response;
import com.project.lms.vo.grade.ScoreTop10VO;
import com.project.lms.vo.request.AvgBySubjectTotalVO;
import com.project.lms.vo.response.AvgListBuSubjectResponseVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ScoreAvgTotalService {
    private final GradeInfoRepository gRepository;
    private final TestInfoRepository tRepository;
    private final SubjectInfoRepository sRepository;
    private final StudentInfoRepository stuRepo;
    // 가장 최신순 + 과목별 평균
    public AvgListBuSubjectResponseVO getSubjectTotalList(UserDetails userDetails) {
    List<TestInfoEntity> tList = tRepository.findAll();// db에서 모든 테스트 정보(TestInfoEntity)를 가져온다.
    Long seq = null;//seq변수를 지정
    for(TestInfoEntity t : tList){// for문으로 테스트 정보를 조회한다.
        seq = t.getTestSeq();// 가져온 테스트 정보 중 가장 마지막 테스트 시퀀스 번호(TestSeq)를 seq 변수에 저장한다.
    }
    List<AvgBySubjectTotalVO> avgList = gRepository.findBySubjectTotal(seq);//저장된 시퀀스 번호를 이용하여 과목별 전체 평균을 조회한다.
    return AvgListBuSubjectResponseVO.builder().message("과목별 전체 평균을 조회하였습니다.")
    .code(HttpStatus.OK)
    .status(true)
    .avgList(avgList)
    .build();// 조회된 과목별 평균을 AvgListBuSubjectResponseVO 객체에 담아 반환한다.
    }

    public List<ScoreTop10Response> getScoreTestTop10() {
        TestInfoEntity test = tRepository.findTop1ByOrderByTestDateDesc();// TestInfoEntity에서 testSeq가 가장 최신인 정보를 가져온다.
        Integer totalStudent = gRepository.countTestStudent(test);// GradeInfoRepository에서 해당 시험에 참가한 학생 수를 가져와서 totalStudent 변수에 저장한다.
        System.out.println(totalStudent/10);
        List<StudentInfo> studentInfos = stuRepo.findTop10List(test, totalStudent/10);// 시험에서 상위 10% 학생의 정보를 가져와 list 형태로 저장한다.
        // System.out.println(grades.size());

        List<ScoreTop10VO> list = gRepository.avgTop10(test, studentInfos);// GradeInfoRepository에서 해당 시험에서 상위 10% 학생들의 평균 성적을 가져와서 list 변수에 저장한다.

        List<ScoreTop10Response> result = new ArrayList<>(); // ScoreTop10Response 클래스의 객체들을 담을 수 있는 빈 리스트를 생성,
        ScoreTop10Response vo = new ScoreTop10Response();// ScoreTop10Response 객체를 생성하고
        vo.setTestName(test.getTestName());// 해당 시험의 이름을 설정한 후,
        Map<String, Double> map = new LinkedHashMap<>();// , Map에 과목별로 상위 10%의 평균 성적을 저장합니다.
        for(ScoreTop10VO sub : list){
            vo.getMap().put(sub.getSubject().getSubName(), sub.getGrade());    
        }
        result.add(vo);//result 리스트에 ScoreTop10Response 객체를 추가하고 반환합니다.
        // for (ScoreTestTop10VO vo : result) {
        //     double avg = getTop10AverageScoreBySubjectAndTest(vo.getSub(), vo.getTest());
        //     System.out.println(avg);
        // }
        return result;
        // return null;
    }

    private double getTop10AverageScoreBySubjectAndTest(SubjectInfoEntity subject, TestInfoEntity test) {
        List<GradeInfoEntity> top10Grades = gRepository.findAllBySubjectAndTestOrderByGradeDescTopCount(subject, test, 0.1);
        double sum = 0;
        for (GradeInfoEntity grade : top10Grades) {
            sum += grade.getGrade();
        }
        return sum / top10Grades.size();
    }

}



