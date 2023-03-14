package com.project.lms.vo.member;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class MemberSearchIdVO {
    @Schema (description = "이름" , example = "차경준")
    private String name;
    @Schema (description = "생년월일" , example = "2022-01-01")
    private LocalDate birth;
    @Schema (description = "이메일" , example = "say052@naver.com")
    private String email;
    
}
