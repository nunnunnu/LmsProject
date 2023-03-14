package com.project.lms.service;

import java.util.regex.Pattern;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.lms.entity.member.MemberInfoEntity;
import com.project.lms.repository.member.MemberInfoRepository;
import com.project.lms.security.provider.JwtTokenProvider;
import com.project.lms.vo.LoginVO;
import com.project.lms.vo.MemberLoginResponseVO;
import com.project.lms.vo.MemberResponseVO;
import com.project.lms.vo.UpdateClassVO;
import com.project.lms.vo.member.UpdateMemberVO;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class MemberSecurityService {
    private final MemberInfoRepository memberInfoRepository;
    private final AuthenticationManagerBuilder authBulider;
    private final JwtTokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;

    public Boolean pwdCheck(String rawPwd, String encodePwd){ 
        if(passwordEncoder.matches(rawPwd, encodePwd)){
            return true;
        } else{
            return false;
    }
}
    @Transactional
    public MemberLoginResponseVO securityLogin(LoginVO login) {
        MemberInfoEntity loginUser = memberInfoRepository.findByMiId(login.getId());

        if(loginUser == null || !passwordEncoder.matches(login.getPwd(), loginUser.getMiPwd())){
            return MemberLoginResponseVO.builder().status(false).message("아이디 또는 비밀번호 오류입니다.").cod(HttpStatus.FORBIDDEN).build();
        }
        else if (!loginUser.isEnabled()) {
            return MemberLoginResponseVO.builder().status(true).message("이용정지된 사용자입니다.").cod(HttpStatus.FORBIDDEN).build();
        }
      
        UsernamePasswordAuthenticationToken authenticationToken =
        new UsernamePasswordAuthenticationToken(loginUser.getMiId(), loginUser.getMiPwd());
        System.out.println(authenticationToken);
        
        Authentication authentication =
        authBulider.getObject().authenticate(authenticationToken);

        MemberLoginResponseVO response = MemberLoginResponseVO.builder().status(true).message("로그인 성공").token(tokenProvider.
                        generateToken(authentication)).cod(HttpStatus.OK).build();
        return response;
        
    }

    public MemberResponseVO updateMember(UpdateMemberVO data, UserDetails userDetails) {
        System.out.println(userDetails);
        MemberInfoEntity entity = memberInfoRepository.findByMiId(userDetails.getUsername());
        String pattern = "^(?=.*\\d)(?=.*[~`!@#$%\\^&*()-])(?=.*[a-z]).{8,16}$";
        if(entity==null) {
            MemberResponseVO m = MemberResponseVO.builder()
            .status(false)
            .message("해당 회원이 존재하지 않습니다.")
            .code(HttpStatus.BAD_REQUEST)
            .build();
            return m;
        }
        if(!pwdCheck(data.getMiPwd(), entity.getMiPwd())){
            MemberResponseVO m = MemberResponseVO.builder()
            .status(false)
            .message("비밀번호가 일치하지않습니다.")
            .code(HttpStatus.BAD_REQUEST)
            .build();
            return m;
        }
        if(data.getMiPwd().length() <8 ) {
            MemberResponseVO m = MemberResponseVO.builder()
            .status(false)
            .message("비밀번호는 8자리 이상 가능합니다")
            .code(HttpStatus.BAD_REQUEST)
            .build();
            return m;
        }
        else if (
            data.getMiPwd().replaceAll(" ", "").length() == 0 ||
            !Pattern.matches(pattern, data.getMiPwd())
            ) {
                MemberResponseVO m = MemberResponseVO.builder()
                .status(false)
                .message("비밀번호에 공백문자를 사용할 수 없습니다.")
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
        else{
            
            entity.updatePwd(passwordEncoder.encode(data.getChangeMiPwd()));
            memberInfoRepository.save(entity);

            MemberResponseVO m = MemberResponseVO.builder()
            .status(true).message("회원 수정이 완료되었습니다.")
            .code(HttpStatus.ACCEPTED)
            .build();
            return m;
        }
    }
}
 