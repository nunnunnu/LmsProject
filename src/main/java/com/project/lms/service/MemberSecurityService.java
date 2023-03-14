package com.project.lms.service;

import org.springframework.http.HttpStatus;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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
import com.project.lms.vo.MailVO;
import com.project.lms.vo.MemberLoginResponseVO;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class MemberSecurityService {
    private final JavaMailSender mailSender;
    private final MemberInfoRepository memberInfoRepository;
    private final AuthenticationManagerBuilder authBulider;
    private final JwtTokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;
    private static final String FROM_ADDRESS = "rudwns0401@gmail.com";
    

    public Boolean pwdCheck(String id, String rawPw){ 
        MemberInfoEntity member = memberInfoRepository.findByMiId(id);
        if(passwordEncoder.matches(rawPw, member.getMiPwd())){
            return true;
        } else{
            return false;
    }
}
    @Transactional
    public MemberLoginResponseVO securityLogin(LoginVO login) {
        // 로그인 시 입력한 아이디 값으로 DB에 저장된 아이디를 찾고
        MemberInfoEntity loginUser = memberInfoRepository.findByMiId(login.getId());
        // 찾은 아이디가 없거나(null) 입력한 비밀번호가 일치하지 않을 때 오류 메시지 출력
        if(loginUser == null || !passwordEncoder.matches(login.getPwd(), loginUser.getMiPwd())){
            return MemberLoginResponseVO.builder().status(false).message("아이디 또는 비밀번호 오류입니다.").cod(HttpStatus.FORBIDDEN).build();
        }
        // 일치하는 아이디가 있더라도 사용 불가능한 상태이면 오류 메시지 출력
        else if (!loginUser.isEnabled()) {
            return MemberLoginResponseVO.builder().status(true).message("이용정지된 사용자입니다.").cod(HttpStatus.FORBIDDEN).build();
        }
        // 입력한 아이디와 비밀번호를 통해서 인증 과정을 거쳐서
        UsernamePasswordAuthenticationToken authenticationToken =
        new UsernamePasswordAuthenticationToken(loginUser.getMiId(), loginUser.getMiPwd());
        System.out.println(authenticationToken);
        
        Authentication authentication =
        authBulider.getObject().authenticate(authenticationToken);
        // 토큰 발급
        String accessToken = tokenProvider.generateToken(authentication).getAccessToken();
        String refreshToken = tokenProvider.generateToken(authentication).getRefreshToken();
        // redisService.setValues(refreshToken, loginUser.getMiId());
        MemberLoginResponseVO response = MemberLoginResponseVO.builder().status(true).message("로그인 성공").token(tokenProvider.
                        generateToken(authentication)).cod(HttpStatus.OK).build();
        return response;
        
    }
    
}
 