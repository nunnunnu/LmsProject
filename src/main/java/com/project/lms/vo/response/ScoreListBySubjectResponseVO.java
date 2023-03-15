package com.project.lms.vo.response;

import com.project.lms.vo.request.ScoreListBySubjectVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScoreListBySubjectResponseVO {
    @Schema(description = "메세지" , example = "성공")
    private String message;
    @Schema (description = "상태" , example = "true")
    private Boolean status;
    @Schema (description = "코드" , example = ".OK")
    private HttpStatus code;
    private List<ScoreListBySubjectVO> scoreList;
}
