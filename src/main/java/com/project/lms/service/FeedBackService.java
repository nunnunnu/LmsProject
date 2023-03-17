package com.project.lms.service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.boot.autoconfigure.jdbc.JdbcProperties.Template;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.project.lms.entity.ClassInfoEntity;
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
import com.project.lms.vo.feedback.FeedBackDetailVO;
import com.project.lms.vo.feedback.FeedBackListVO;
import com.project.lms.vo.feedback.FeedBackResponseVO;
import com.project.lms.vo.feedback.FeedBackVO;
import com.project.lms.vo.feedback.ShowFeedBackDetailVO;
import com.project.lms.vo.feedback.ShowFeedBackVO;
import com.project.lms.vo.feedback.UpdateFeedBackResponseVO;
import com.project.lms.vo.feedback.UpdateFeedBackVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FeedBackService {
    private final ClassTeacherRepository ctRepo;
    private final ClassStudentRepository csRepo;
    private final TeacherInfoRepository tRepo;
    private final StudentInfoRepository sRepo;
    private final FeedbackInfoRepository fRepo;


    // 피드백 리스트
    public ShowFeedBackVO showFeedBack(String id) {
        TeacherInfo tInfo = tRepo.findByMiId(id); // 로그인한 선생님의 정보를 찾는다.
        ClassTeacherEntity ctInfo = ctRepo.findByTeacher(tInfo); // 반 정보를 찾는다. 
        ClassInfoEntity cEntity = ctInfo.getClassInfo(); // 로그인한 선생님의 반 정보를 classNum에 담는다.
        List<ClassTeacherEntity> ctList = ctRepo.findByClassInfo(cEntity);
        List<FeedBackListVO> voList = new LinkedList<>();
        List<FeedbackInfo> fInfoList = fRepo.findAll();
        for(ClassTeacherEntity ctEntity : ctList){
            for(FeedbackInfo fList : fInfoList){
               if(fList.getTeacher().getMiSeq() == ctEntity.getTeacher().getMiSeq()) {
                String regDate = fList.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));                
                    FeedBackListVO voList2 = FeedBackListVO.builder().no(fList.getFiSeq())
                    .title(fList.getFiTitle())
                    .regDt(regDate)
                    .writer(fList.getTeacher().getMiName()).build();
                    voList.add(voList2);
                    voList = voList.stream().sorted(Comparator.comparing(FeedBackListVO::getRegDt).reversed()).collect(Collectors.toList());
               }
            }
        }
        ShowFeedBackVO sVo = ShowFeedBackVO.builder()
        .status(true)
        .message("피드백 리스트를 조회하였습니다.")
        .code(HttpStatus.ACCEPTED)
        .list(voList)
        .build();
        return sVo;
    }

    // 피드백 상세 조회
    public ShowFeedBackDetailVO showFeedBackDetail(Long fiSeq) {
        FeedbackInfo entity = fRepo.findById(fiSeq).orElse(null);
        if(entity==null){
            ShowFeedBackDetailVO fVo = ShowFeedBackDetailVO.builder()
            .status(false)
            .message("존재하지 않는 글 번호입니다.")
            .code(HttpStatus.NOT_FOUND)
            .build();
            return fVo;
        }
            String regDate = entity.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            FeedBackDetailVO vo = FeedBackDetailVO.builder().title(entity.getFiTitle()).regDt(regDate).content(entity.getFiContent()).writer(entity.getTeacher().getMiName()).build();
            ShowFeedBackDetailVO fvo = ShowFeedBackDetailVO.builder()
            .status(true)
            .message("피드백 상세 조회를 하였습니다.")
            .code(HttpStatus.ACCEPTED)
            .detail(vo)
            .build();
            return fvo;
        
    }

    // 피드백 작성
    public FeedBackResponseVO putFeedBack(String id, Long stuSeq, FeedBackVO data) {
        if(data.getFiContent()==null || data.getFiContent()==null){
            return FeedBackResponseVO.builder()
                .code(HttpStatus.BAD_REQUEST)
                .message("필수 입력값이 누락되었습니다.")
                .status(false)
                .build();
        }
        TeacherInfo tInfo = tRepo.findByMiId(id); // 선생님 아이디로 정보를 찾는다.
        ClassTeacherEntity ctInfo = ctRepo.findByTeacher(tInfo); // 그 정보를 가지고 classTeach 정보를 찾는다.
        Long ctSeq = ctInfo.getClassInfo().getCiSeq(); // 선생님의 반 고유번호를 변수 seq에 담는다.
        StudentInfo sInfo = sRepo.findById(stuSeq).orElse(null);
        if(sInfo == null) {
            FeedBackResponseVO f = FeedBackResponseVO.builder()
            .status(false)
            .message("알맞지 않는 학생 고유번호입니다.")
            .code(HttpStatus.FORBIDDEN)
            .build();
            return f; 
        }

        ClassStudentEntity csInfo = csRepo.findByStudent(sInfo); // 학생 정보를 찾는다.
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
            FeedbackInfo entity = new FeedbackInfo(null, data.getFiTitle(), sInfo, tInfo, data.getFiContent(), 1);
            fRepo.save(entity);
            FeedBackResponseVO f = FeedBackResponseVO.builder()
            .status(true).message("피드백 작성을 완료하였습니다.").code(HttpStatus.ACCEPTED)
            .build();
            return f;
        }
    }

    // 피드백 수정
    public UpdateFeedBackResponseVO updateFeedBack(String id, Long fiSeq, UpdateFeedBackVO data){
        TeacherInfo tInfo = tRepo.findByMiId(id); // 선생님 아이디로 정보를 찾는다.
        List<FeedbackInfo> fInfoList = fRepo.findByTeacher(tInfo); // 선생님이 쓴 글들의 정보를 가져온다.
        Long seq = null;
        for(FeedbackInfo f : fInfoList){
            if(f.getFiSeq() == fiSeq){
                seq = f.getFiSeq();
            }
        }
        
        if(data.getTitle()==null || data.getContent()==null) {
            return UpdateFeedBackResponseVO.builder()
            .code(HttpStatus.BAD_REQUEST)
            .message("필수 입력값이 누락되었습니다.")
            .status(false)
            .build();
        }
      

        // ClassTeacherEntity ctInfo = ctRepo.findByTeacher(tInfo); // 그 정보를 가지고 classTeach 정보를 찾는다.
        // teacher ctSeq = ctInfo.getClassInfo().getCiSeq(); // 선생님의 반 고유번호를 변수 seq에 담는다.
        
        
       
        
        
        // FeedbackInfo fInfo = fRepo.findById(fiSeq).orElse(null);
        
        // if(fInfo == null) {
        //     UpdateFeedBackResponseVO u = UpdateFeedBackResponseVO.builder()
        //     .status(false)
        //     .message("알맞지 않은 글 번호입니다.")
        //     .code(HttpStatus.FORBIDDEN)
        //     .build();
        //     return u; 
        // }
        // if(ctSeq != fiSeq) {
        //     UpdateFeedBackResponseVO u = UpdateFeedBackResponseVO.builder()
        //     .status(false)
        //     .message("해당 피드백은 수정할 수 없습니다.")
        //     .code(HttpStatus.FORBIDDEN)
        //     .build();
        //     return u;
        // }
       
            FeedbackInfo entity = new FeedbackInfo(fiSeq, data.getTitle(), null, tInfo, data.getContent(), 1);
            fRepo.save(entity);
            UpdateFeedBackResponseVO u = UpdateFeedBackResponseVO.builder()
            .status(true).message("피드백 수정을 완료하였습니다.").code(HttpStatus.ACCEPTED)
            .build();
            return u;
        
    }
}
