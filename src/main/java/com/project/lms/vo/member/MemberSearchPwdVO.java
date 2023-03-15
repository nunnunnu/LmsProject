package com.project.lms.vo.member;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class MemberSearchPwdVO {
    @Schema (description = "아이디" , example = "user012")
    private String id;
    @Schema (description = "이름" , example = "차경준")
    private String name;
    @Schema (description = "이메일" , example = "say052@naver.com")
    private String email;
    
}
