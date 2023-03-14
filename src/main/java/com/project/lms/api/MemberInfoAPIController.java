package com.project.lms.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.lms.service.MemberSecurityService;
import com.project.lms.vo.MemberResponseVO;
import com.project.lms.vo.member.UpdateMemberVO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "멤버정보 관리", description = "멤버정보 CRUD API")
@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberInfoAPIController {
    private final MemberSecurityService memberInfoService;
    @Operation(summary = "회원정보 수정")
    @PostMapping(value = "/update")
    public ResponseEntity<MemberResponseVO> updateMember(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody UpdateMemberVO data) {
                
        return new ResponseEntity<>(memberInfoService.updateMember(data, userDetails), HttpStatus.OK);
    }
}
