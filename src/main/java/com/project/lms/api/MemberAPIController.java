package com.project.lms.api;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.lms.service.MemberSecurityService;
import com.project.lms.vo.LoginVO;
import com.project.lms.vo.MailVO;
import com.project.lms.vo.MemberLoginResponseVO;
import com.project.lms.vo.member.MemberSearchIdVO;
import com.project.lms.vo.member.MemberSearchPwdVO;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
@RequestMapping("/api/member")
@RestController
@RequiredArgsConstructor
public class MemberAPIController {
    private final MemberSecurityService memberSecurityService;
    // @PostMapping("/login")
    // public ResponseEntity<MemberLoginResponseVO> postMemberLogin(@RequestBody LoginVO login) {
    //     MemberLoginResponseVO response = memberSecurityService.securityLogin(login);
    //     return new ResponseEntity<>(response,response.getCod());
    // }
    @Operation(summary = "아이디 찾기", description ="이름,생년월일,이메일(name,birth,email)을 입력 받아 일치하는 정보의 아이디 출력")
    @PostMapping("/id")
    public ResponseEntity<Object> searchMemberId(MemberSearchIdVO data){
        Map<String, Object> map = memberSecurityService.searchMemberId(data);
        return new ResponseEntity<Object>(map, (HttpStatus)map.get("code"));
    }
    @Operation(summary = "비밀번호 찾기", description ="아이디,이름,이메일(id,name,email)을 입력 받아 일치하는 유저의 등록된 메일로 임시 비밀번호 발급")
    @PostMapping("/pwd")
        public ResponseEntity<MailVO> searchMemberPwd(MemberSearchPwdVO data){
        MailVO mail = memberSecurityService.searchMemberPwd(data);
        // System.out.println(mail);
        memberSecurityService.mailSend(mail);
        return new ResponseEntity<MailVO>(mail, HttpStatus.OK);
    }
    

}
