package com.project.lms.vo.member;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UpdateMemberVO {
    @Schema(description = "비밀번호", example = "123456789")
    private String miPwd;
    @Schema(description = "변경된 비밀번호", example = "121212121")
    private String changeMiPwd;
}
