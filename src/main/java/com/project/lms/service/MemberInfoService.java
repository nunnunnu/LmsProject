package com.project.lms.service;

import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.project.lms.entity.member.MemberInfoEntity;
import com.project.lms.repository.member.MemberInfoRepository;
import com.project.lms.utils.AESAlgorithm;
import com.project.lms.vo.MemberResponseVO;
import com.project.lms.vo.member.UpdateMemberVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberInfoService {
    @Autowired MemberInfoRepository mRepo;

    public MemberResponseVO updateMember(UpdateMemberVO data, Long seq) {
        Optional<MemberInfoEntity> member = mRepo.findById(seq);
        String passwordPattern = "^(?=.*\\d)(?=.*[~`!@#$%\\^&*()-])(?=.*[a-z]).{8,16}$";
        if(!Pattern.matches(passwordPattern, data.getChngeMiPwd())) {
            MemberResponseVO m = MemberResponseVO.builder()
            .status(false)
            .message("비밀번호는 공백없이 6자리 이상 가능합니다.")
            .code(HttpStatus.BAD_REQUEST)
            .build();
            return m;
        }
        else if(data.getMiPwd() == null || data.getMiPwd().equals("")) {
            MemberResponseVO m = MemberResponseVO.builder()
            .status(false)
            .message("비밀번호를 입력해주세요")
            .code(HttpStatus.BAD_REQUEST)
            .build();
            return m;
        }
        else if(data.getMiPwd().length()<6) {
                MemberResponseVO m = MemberResponseVO.builder()
                .status(false)
                .message("비밀번호는 6자리 이상입니다.")
                .code(HttpStatus.BAD_REQUEST)
                .build();
                return m;
            }
            
        if(member == null) {
            MemberResponseVO m = MemberResponseVO.builder()
            .status(false)
            .message("해당 회원이 존재하지 않습니다.")
            .code(HttpStatus.BAD_REQUEST)
            .build();
            return m;
        }     
        else{
            // try{
            //     String encPwd = AESAlgorithm.Encrypt(data.getMiPwd());
            //     MemberInfoEntity e = member.get();
            //     e.setMiPwd(encPwd);
            // }
            // catch (Exception e) {e.printStackTrace();}
            // MemberInfoEntity e = member.get();
            // e.setMiPwd(data.getMiPwd());
            // mRepo.save(e);
            MemberResponseVO m = MemberResponseVO.builder()
            .status(true).message("회원 수정이 완료되었습니다.")
            .code(HttpStatus.ACCEPTED)
            .build();
            return m;
        }
    }
}
