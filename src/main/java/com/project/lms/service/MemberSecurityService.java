package com.project.lms.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.lms.entity.member.MemberInfoEntity;
import com.project.lms.repository.member.MemberInfoRepository;
import com.project.lms.security.provider.JwtTokenProvider;
import com.project.lms.security.vo.TokenVO;
import com.project.lms.vo.LoginVO;
import com.project.lms.vo.MemberLoginResponseVO;

import lombok.RequiredArgsConstructor;
 // SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        // TokenVO jwt = tokenProvider.generateToken(authenticationToken);
@Service
@RequiredArgsConstructor
public class MemberSecurityService {
    private final MemberInfoRepository memberInfoRepository;
    private final AuthenticationManagerBuilder authBulider;
    private final JwtTokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;

    public Boolean login(String id, String rawPw){ 
	MemberInfoEntity member = memberInfoRepository.findByMiId(id);
    if(passwordEncoder.matches(rawPw, member.getMiPwd())){
    	return true;
    } else{
    	return false;
    }
}

    public MemberLoginResponseVO securityLogin(LoginVO login) {
        MemberInfoEntity loginUser = memberInfoRepository.findByMiId(login.getId());

        if(loginUser == null || !passwordEncoder.matches(login.getPwd(), loginUser.getMiPwd())){
            return MemberLoginResponseVO.builder().status(false).message("아이디 또는 비밀번호 오류입니다.").cod(HttpStatus.FORBIDDEN).build();
        }
        else if (!loginUser.isEnabled()) {
            return MemberLoginResponseVO.builder().status(true).message("이용정지된 사용자입니다.").cod(HttpStatus.FORBIDDEN).build();
        }
      
        System.out.println("vvvvvv");
        UsernamePasswordAuthenticationToken authenticationToken
        = new UsernamePasswordAuthenticationToken(loginUser.getMiId(),loginUser.getMiPwd());
        
        System.out.println("aaaaa");
        Authentication authentication = authBulider.getObject().authenticate(authenticationToken);
        System.out.println(authentication);

        MemberLoginResponseVO response = MemberLoginResponseVO.builder().status(true).message("로그인 성공").token(tokenProvider.
                        generateToken(authentication)).cod(HttpStatus.OK).build();
        return response;
        
    }
    
}
 