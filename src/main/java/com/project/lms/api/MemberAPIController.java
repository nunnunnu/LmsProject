package com.project.lms.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.project.lms.service.MemberSecurityService;
import com.project.lms.vo.LoginVO;
import com.project.lms.vo.MemberLoginResponseVO;

import lombok.RequiredArgsConstructor;
@RequestMapping("/api/member")
@RestController
@RequiredArgsConstructor
public class MemberAPIController {
    private final MemberSecurityService memberSecurityService;
    @PostMapping("/login")
    public ResponseEntity<MemberLoginResponseVO> postMemberLogin(@RequestBody LoginVO login) {
        MemberLoginResponseVO response = memberSecurityService.securityLogin(login);
        return new ResponseEntity<>(response,response.getCod());
    }
    
}
