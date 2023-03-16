package com.project.lms.vo;

import org.springframework.http.HttpStatus;

import com.project.lms.entity.member.enumfile.Role;
import com.project.lms.security.vo.TokenVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberLoginResponseVO {
     @Schema(description = "상태")
     private Boolean status;
     @Schema(description = "메세지")
     private String message;
     @Schema(description = "상태코드")
     private HttpStatus cod;
     @Schema(description = "토큰")
     private TokenVO token;
     @Schema(description = "유저 이름")
     private String name;
     @Schema(description = "유저 권환")
    private Role role;
    
}
