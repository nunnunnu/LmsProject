package com.project.lms.vo.member;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "엑세스 토큰 재발급을 위한 VO")
public class RefreshTokenVO {
    @Schema(description = "로그인할때 받은 refresh token을 보내주세요")
    private String refresh;
}
