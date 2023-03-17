package com.project.lms.service;

import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.project.lms.entity.ClassStudentEntity;
import com.project.lms.entity.ClassTeacherEntity;
import com.project.lms.entity.CommentInfoEntity;
import com.project.lms.entity.feedback.FeedbackInfo;
import com.project.lms.entity.member.MemberInfoEntity;
import com.project.lms.entity.member.StudentInfo;
import com.project.lms.entity.member.TeacherInfo;
import com.project.lms.repository.ClassStudentRepository;
import com.project.lms.repository.ClassTeacherRepository;
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
import com.project.lms.vo.feedback.FeedBackDetailVO;
import com.project.lms.vo.MapVO;
import com.project.lms.vo.feedback.CommentInsertVO;
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
    private final CommentInfoRepository ciRepo;
    private final MemberInfoRepository miRepo;


    // 피드백 리스트
    public ShowFeedBackVO showFeedBack(String id, Pageable page) {
        TeacherInfo tInfo = tRepo.findByMiId(id); // 로그인한 선생님의 정보를 찾는다.
        Page<FeedbackInfo> list = fRepo.findByTeacher(tInfo, page);

        Page<FeedBackListVO> voList = list.map((f)-> new FeedBackListVO(f)); // entity 페이지 리스트를 페이지 DTO 형태로 변환하기 위한 람다식
       
        ShowFeedBackVO sVo = ShowFeedBackVO.builder()
        .status(true)
        .message("피드백 리스트를 조회하였습니다.")
        .code(HttpStatus.ACCEPTED)
        .list(voList)
        .build();
        return sVo;
    }

    // 피드백 상세 조회
    public ShowFeedBackDetailVO showFeedBackDetail(String id, Long fiSeq) {
        TeacherInfo tInfo = tRepo.findByMiId(id);
        FeedbackInfo entity = fRepo.findById(fiSeq).orElse(null);
        if(entity==null){
            ShowFeedBackDetailVO fVo = ShowFeedBackDetailVO.builder()
            .status(false)
            .message("존재하지 않는 글 번호입니다.")
            .code(HttpStatus.NOT_FOUND)
            .build();
            return fVo;
        }
        if(entity.getTeacher().getMiSeq() != tInfo.getMiSeq()){
            ShowFeedBackDetailVO fVo = ShowFeedBackDetailVO.builder()
            .status(false)
            .message("조회할 수 없는 게시글입니다.")
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
        Boolean check = false;
        for(FeedbackInfo f : fInfoList){
            if(f.getFiSeq() == fiSeq){ 
                check = true;
            }
        }
        
        if(!check){
            return UpdateFeedBackResponseVO.builder()
            .code(HttpStatus.BAD_REQUEST)
            .message("해당 로그인한 사람의 글이 아니거나 잘못된 글 번호입니다.")
            .status(false)
            .build(); 
        }
        if(data.getTitle()==null && data.getContent()==null) {
            return UpdateFeedBackResponseVO.builder()
            .code(HttpStatus.BAD_REQUEST)
            .message("수정할 내용이 없습니다.")
            .status(false)
            .build();
        }
            FeedbackInfo fInfo1 = fRepo.findById(fiSeq).get();
            fInfo1.updateData(data.getTitle(), data.getContent());
            
            fRepo.save(fInfo1);
            UpdateFeedBackResponseVO u = UpdateFeedBackResponseVO.builder()
            .status(true).message("피드백 수정을 완료하였습니다.").code(HttpStatus.ACCEPTED)
            .build();
            return u;
    }

    // 피드백 삭제
    public FeedBackResponseVO deleteFeedBack(String id, Long fiSeq) {
        TeacherInfo tInfo = tRepo.findByMiId(id);
        FeedbackInfo entity = fRepo.findById(fiSeq).orElse(null);
        if(entity==null){
            FeedBackResponseVO fVo = FeedBackResponseVO.builder()
            .status(false)
            .message("존재하지 않는 글 번호입니다.")
            .code(HttpStatus.NOT_FOUND)
            .build();
            return fVo;
        }
        if(entity.getTeacher().getMiSeq() != tInfo.getMiSeq()){
            FeedBackResponseVO fVo = FeedBackResponseVO.builder()
            .status(false)
            .message("삭제할 수 없는 게시글입니다.")
            .code(HttpStatus.NOT_FOUND)
            .build();
            return fVo;
        }
        else{
            fRepo.delete(entity);
            FeedBackResponseVO fVo = FeedBackResponseVO.builder()
            .status(true)
            .message("게시글이 삭제 되었습니다.")
            .code(HttpStatus.ACCEPTED)
            .build();
            return fVo;
        }

    }
        }
        // 댓글작성
        public MapVO addComment(Long seq, CommentInsertVO data, UserDetails user) {
            FeedbackInfo feedback = fRepo.findById(seq).orElseThrow(()->new NotFoundFeedback()); // seq로 게시글을 찾고 없으면 오류처리
            MemberInfoEntity member = miRepo.findByMiId(user.getUsername()); // 로그인한 유저의 아이디로 회원정보를 찾음
            // if(member.getMiRole().equals("EMPLOYEE")) {
            //     MapVO map = MapVO.builder().code(HttpStatus.NOT_ACCEPTABLE).message("댓글은 선생님 및 학생만 작성 가능합니다.").status(false).build();
            //     return map;
            // }
            // System.out.println(member.getMiRole()); 
            CommentInfoEntity comment = CommentInfoEntity.builder().cmtTitle(data.getComment()).feedback(feedback).member(member).build(); // 댓글을 작성하고
            if (feedback.getStudent() == member || feedback.getTeacher()==member) { // 게시글 작성자 및 담당 학생이 아닌 사람이 댓글 작성시 오류
                if (data.getComment() ==null || data.getComment() =="") { // 게시글 작성사 및 담담학생이 댓글을 달때
                    MapVO map = MapVO.builder().code(HttpStatus.NOT_ACCEPTABLE).message("내용을 작성해주세요").status(false).build(); // 내용 미입력시 오류 처리
                    return map;
                }
                else{
                    ciRepo.save(comment);// 오류가 없을 시에는 댓글 작성후 저장
                    MapVO map = MapVO.builder().code(HttpStatus.OK).message("댓글이 작성되었습니다.").status(true).build();
                    return map;
                }
            }
            else{
                MapVO map = MapVO.builder().code(HttpStatus.FAILED_DEPENDENCY).message("담당 학생 및 선생님만 댓글 작성이 가능합니다.").status(false).build(); // 게시글 작성자 및 담당 학생이 아닌 사람이 댓글 작성시 오류
                return map;
            }
            
        }
}
