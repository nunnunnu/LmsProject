package com.project.lms.vo;

import org.springframework.http.HttpStatus;

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
    
}
