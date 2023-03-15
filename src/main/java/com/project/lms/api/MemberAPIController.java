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
    @PostMapping("")
    public ResponseEntity<Object> searchMemberId(MemberSearchIdVO data){
        Map<String, Object> map = memberSecurityService.searchMemberId(data);
        return new ResponseEntity<Object>(map, (HttpStatus)map.get("code"));
    }
    @PostMapping("/pwd")
        public ResponseEntity<MailVO> searchMemberPwd(MemberSearchPwdVO data){
        MailVO mail = memberSecurityService.searchMemberPwd(data);
        // System.out.println(mail);
        memberSecurityService.mailSend(mail);
        return new ResponseEntity<MailVO>(mail, HttpStatus.OK);
    }
    

}
