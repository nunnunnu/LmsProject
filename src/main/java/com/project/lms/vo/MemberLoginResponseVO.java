package com.project.lms.vo;

import org.springframework.http.HttpStatus;

import com.project.lms.entity.member.enumfile.Role;
import com.project.lms.security.vo.TokenVO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberLoginResponseVO {
     private Boolean status;
    private String message;
    private HttpStatus cod;
    private TokenVO token;
    private String id;
    private Role role;
    
}
