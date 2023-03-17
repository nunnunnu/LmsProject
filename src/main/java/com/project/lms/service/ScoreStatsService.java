package com.project.lms.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.lms.entity.ClassInfoEntity;
import com.project.lms.entity.member.TeacherInfo;
import com.project.lms.repository.ClassInfoRepository;
import com.project.lms.repository.GradeInfoRepository;
import com.project.lms.repository.member.TeacherInfoRepository;
import com.project.lms.vo.ScoreAvgListBySubjectVO;
import com.project.lms.vo.ScoreAvgStatsListByClassVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ScoreStatsService {
    private final TeacherInfoRepository teacherInfoRepository;
    private final GradeInfoRepository gradeInfoRepository;
    private final ClassInfoRepository classInfoRepository;

    // 반별 + 연월별 + 과목별 평균
    public ScoreAvgStatsListByClassVO ClassScoreStats(Long classSeq, Integer yearMonth, UserDetails userDetails) {
        TeacherInfo teacher = teacherInfoRepository.findByMiId(userDetails.getUsername()); // 로그인 한 회원을 MemberInfoEntity에서 찾아서
        if (teacher == null) {
            System.out.println("선생님이 아닙니다."); // 선생님이 아닌 값을 제어처리 (에러코드로 수정하는건 추후 수정)
        }
        List<Long> studentSeq = gradeInfoRepository.findByCsSeq(classSeq); // 조회하려는 반의 시퀀스를 받고 해당 학생 시퀀스를 리스트에 받는다.
        List<ScoreAvgListBySubjectVO> avgBySubject = gradeInfoRepository.avgBySubject(studentSeq, yearMonth); // 학생 시퀀스들과 조회하려는 연월로 과목별 평균값을 리스트에 받는다.
        ClassInfoEntity classInfo = classInfoRepository.findById(classSeq).get(); // 조회하려는 반을 찾아 온다.
        ScoreAvgStatsListByClassVO result = new ScoreAvgStatsListByClassVO(avgBySubject, classInfo.getCiName()); // 리스트에 담은 결과를 반이름과 함께 최종 VO에 담는다.
        // 연월별 + 과목별 평균을 모든 반별로 조회하는 것이 에러가 나서 반 하나씩 선택했을때 뜨도록 구현했습니다.
        // 나머지는 추후 수정하겠습니다.
        return result;
    }
}
