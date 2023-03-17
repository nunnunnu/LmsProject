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
import com.project.lms.entity.CommentInfoEntity;
import com.project.lms.entity.feedback.FeedbackInfo;
import com.project.lms.entity.member.MemberInfoEntity;
import com.project.lms.entity.member.StudentInfo;
import com.project.lms.entity.member.TeacherInfo;
import com.project.lms.error.custom.NotFoundFeedback;
import com.project.lms.repository.ClassInfoRepository;
import com.project.lms.repository.ClassStudentRepository;
import com.project.lms.repository.ClassTeacherRepository;
import com.project.lms.repository.CommentInfoRepository;
import com.project.lms.repository.TestInfoRepository;
import com.project.lms.repository.feedback.FeedbackInfoRepository;
import com.project.lms.repository.member.MemberInfoRepository;
import com.project.lms.repository.member.StudentInfoRepository;
import com.project.lms.repository.member.TeacherInfoRepository;
import com.project.lms.vo.MapVO;
import com.project.lms.vo.feedback.CommentInsertVO;
import com.project.lms.vo.feedback.FeedBackListVO;
import com.project.lms.vo.feedback.FeedBackResponseVO;
import com.project.lms.vo.feedback.FeedBackVO;
import com.project.lms.vo.feedback.ShowFeedBackVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FeedBackService {
    private final ClassTeacherRepository ctRepo;
    private final ClassStudentRepository csRepo;
    private final TeacherInfoRepository tRepo;
    private final StudentInfoRepository sRepo;
    private final FeedbackInfoRepository fRepo;
    private final CommentInfoRepository ciRepo;
    private final MemberInfoRepository miRepo;

    public ShowFeedBackVO showFeedBack(String id) {
        TeacherInfo tInfo = tRepo.findByMiId(id); // 로그인한 선생님의 정보를 찾는다.
        ClassTeacherEntity ctInfo = ctRepo.findByTeacher(tInfo); // 반 정보를 찾는다. 
        ClassInfoEntity cEntity = ctInfo.getClassInfo(); // 로그인한 선생님의 반 정보를 classNum에 담는다.
        List<ClassTeacherEntity> ctList = ctRepo.findByClassInfo(cEntity);
        List<FeedBackListVO> voList = new LinkedList<>();
        List<FeedbackInfo> fInfoList = fRepo.findAll();
        for(ClassTeacherEntity ctEntity : ctList){
            for(FeedbackInfo fList : fInfoList){
               if(fList.getTeacher().getMiSeq() == ctEntity.getTeacher().getMiSeq()){
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

        ClassStudentEntity csInfo = csRepo.findByStudent(sInfo);
        Long csSeq = csInfo.getClassInfo().getCiSeq();
        System.out.println(ctSeq);
        System.out.println(csSeq);
        

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

    public MapVO addComment(Long seq, CommentInsertVO data, UserDetails user) {
        FeedbackInfo feedback = fRepo.findById(seq).orElseThrow(()->new NotFoundFeedback());
        MemberInfoEntity member = miRepo.findByMiId(user.getUsername());
        CommentInfoEntity comment = CommentInfoEntity.builder().cmtTitle(data.getComment()).feedback(feedback).member(member).build();
        if (feedback.getStudent() == member || feedback.getTeacher()==member) {
            if (data.getComment() ==null || data.getComment() =="") {
                MapVO map = MapVO.builder().code(HttpStatus.BAD_REQUEST).message("내용을 작성해주세요").status(false).build();
                return map;
            }
            else{
            ciRepo.save(comment);
            MapVO map = MapVO.builder().code(HttpStatus.OK).message("댓글이 작성되었습니다.").status(true).build();
            return map;
        }
    }
        else{
            MapVO map = MapVO.builder().code(HttpStatus.BAD_REQUEST).message("담당 학생 및 선생님만 댓글 작성이 가능합니다.").status(false).build();
            return map;
        }
        
    }
}
