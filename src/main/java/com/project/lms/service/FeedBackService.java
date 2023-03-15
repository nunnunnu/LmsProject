package com.project.lms.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.project.lms.entity.ClassStudentEntity;
import com.project.lms.entity.ClassTeacherEntity;
import com.project.lms.entity.feedback.FeedbackInfo;
import com.project.lms.entity.member.StudentInfo;
import com.project.lms.entity.member.TeacherInfo;
import com.project.lms.repository.ClassInfoRepository;
import com.project.lms.repository.ClassStudentRepository;
import com.project.lms.repository.ClassTeacherRepository;
import com.project.lms.repository.TestInfoRepository;
import com.project.lms.repository.feedback.FeedbackInfoRepository;
import com.project.lms.repository.member.StudentInfoRepository;
import com.project.lms.repository.member.TeacherInfoRepository;
import com.project.lms.vo.feedback.FeedBackResponseVO;
import com.project.lms.vo.feedback.FeedBackVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FeedBackService {
    private final ClassInfoRepository cRepo;
    private final ClassTeacherRepository ctRepo;
    private final ClassStudentRepository csRepo;
    private final TeacherInfoRepository tRepo;
    private final StudentInfoRepository sRepo;
    private final FeedbackInfoRepository fRepo;
    
    public FeedBackResponseVO putFeedBack(String id, Long stuSeq, FeedBackVO data) {
        // FeedbackInfoRepository fInfo = fRepo.find
        TeacherInfo tInfo = tRepo.findByMiId(id); // 선생님 아이디로 정보를 찾는다.
        ClassTeacherEntity ctInfo = ctRepo.findByTeacher(tInfo); // 그 정보를 가지고 classTeach 정보를 찾는다.
        Long ctSeq = ctInfo.getClassInfo().getCiSeq(); // 선생님의 반 고유번호를 변수 seq에 담는다.

        StudentInfo sInfo = sRepo.findByMiId(id);
        ClassStudentEntity csInfo = csRepo.findByStudent(sInfo);
        Long csSeq = csInfo.getClassInfo().getCiSeq();

        if(ctSeq != csSeq){
            FeedBackResponseVO f = FeedBackResponseVO.builder()
            .status(false)
            .message("해당 학생은 피드백을 작성할 수 없습니다.")
            .code(HttpStatus.FORBIDDEN)
            .build();
            return f;
        }
        else{
            // FeedbackInfo entity = new FeedbackInfo(, ctSeq, csSeq, data.getFiTitle(), data.getFiContent());

        }

        return null;
    }
}
