package com.project.lms.service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.lms.entity.ClassInfoEntity;
import com.project.lms.entity.SubjectInfoEntity;
import com.project.lms.entity.TestInfoEntity;
import com.project.lms.entity.member.TeacherInfo;
import com.project.lms.error.custom.NotFoundMemberException;
import com.project.lms.repository.ClassInfoRepository;
import com.project.lms.repository.GradeInfoRepository;
import com.project.lms.repository.SubjectInfoRepository;
import com.project.lms.repository.TestInfoRepository;
import com.project.lms.repository.member.TeacherInfoRepository;
import com.project.lms.vo.ScoreAllListBySubjectVO;
import com.project.lms.vo.ScoreAvgListBySubjectVO;
import com.project.lms.vo.ScoreAvgStatsListByClassVO;
import com.project.lms.vo.response.ScoreRankBySubjectVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ScoreStatsService {
    private final TeacherInfoRepository teacherInfoRepository;
    private final GradeInfoRepository gradeInfoRepository;
    private final ClassInfoRepository classInfoRepository;
    private final TestInfoRepository tRepo;
    private final SubjectInfoRepository subRepo;

    // 연월별 학생 성적 조회
    public List<ScoreAllListBySubjectVO> ScoreList(YearMonth yearMonth, UserDetails userDetails, Pageable pageable) {
        LocalDate frist = LocalDate.of(yearMonth.getYear(), yearMonth.getMonth(), 1);
        LocalDate last = frist.withDayOfMonth(frist.lengthOfMonth());

        TestInfoEntity test = tRepo.findByTestDateBetween(frist, last);
        SubjectInfoEntity reading = subRepo.findBySubSeq(1l);
        SubjectInfoEntity vocabulary = subRepo.findBySubSeq(2l);
        SubjectInfoEntity grammar = subRepo.findBySubSeq(3l);
        SubjectInfoEntity listening = subRepo.findBySubSeq(4l);

        List<ScoreAllListBySubjectVO> resultList = gradeInfoRepository.scoreBySubject(test, reading, vocabulary, grammar, listening); // 최종 결과를 담을 리스트를 만든다.
        return resultList; // 결과값을 내보낸다.
    }
    

    // 반별 + 연월별 + 과목별 평균
    public List<ScoreAvgStatsListByClassVO> ClassScoreStats(Integer yearMonth, UserDetails userDetails) {
        List<ScoreAvgStatsListByClassVO> resultList = new ArrayList<>(); // 최종 결과를 담을 리스트를 만든다.
        List<ClassInfoEntity> classEntity = classInfoRepository.findAll(); // 전체 반 정보를 담는 리스트를 만들어서
        for(ClassInfoEntity c : classEntity){ // 조회를 한다.
            List<Long> studentSeq = gradeInfoRepository.findByCsSeq(c.getCiSeq()); // 반마다 해당하는 학생 시퀀스를 Long값의 리스트에 담고
            List<ScoreAvgListBySubjectVO> avgBySubject = gradeInfoRepository.avgBySubject(studentSeq, yearMonth); // 학생 시퀀스들과 조회하려는 연월로 과목별 평균값을 리스트에 받는다.
            ScoreAvgStatsListByClassVO result = new ScoreAvgStatsListByClassVO(avgBySubject, c.getCiName()); // 리스트에 담은 결과를 반이름과 함께 result VO에 담는다.
            resultList.add(result); // result를 최종 vo 리스트에 담는다.
        }
        return resultList; 
    }


    // 과목별 전체 학생 랭킹
    public List<ScoreRankBySubjectVO> ScoreRank(Long subjectSeq, UserDetails userDetails) {
        TeacherInfo teacher = teacherInfoRepository.findByMiId(userDetails.getUsername()); // 로그인 한 회원을 MemberInfoEntity에서 찾아서
        if (teacher == null) {
            throw new NotFoundMemberException(); // 선생님이 아닌 값을 제어처리
        }
        List<ScoreRankBySubjectVO> result = gradeInfoRepository.rankBySubject(subjectSeq); // 과목별로 학생 랭크를 조회해서 리스트에 담는다.
        return result;
    }

}
