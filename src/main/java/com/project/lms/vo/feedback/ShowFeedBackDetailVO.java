package com.project.lms.vo.feedback;

import java.util.List;

import org.springframework.http.HttpStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ShowFeedBackDetailVO {
    @Schema (description = "상태" , example = "true")
    private Boolean status;
    @Schema (description = "메세지" , example = "성공")
    private String message;
    @Schema (description = "code" , example = "OK")
    private HttpStatus code;
    @Schema (description = "detail" , example = "피드백 상세 정보")
    private FeedBackDetailVO detail;
}
