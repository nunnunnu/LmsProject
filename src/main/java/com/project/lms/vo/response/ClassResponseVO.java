package com.project.lms.vo.response;

import org.springframework.http.HttpStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public class ClassResponseVO {
    @Schema (description = "상태" , example = "true")
    private Boolean status;
    @Schema (description = "메세지" , example = "성공")
    private String message;
    @Schema (description = "code" , example = "OK.")
    private HttpStatus code;
}
