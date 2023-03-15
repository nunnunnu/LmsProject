package com.project.lms.vo.response;

import java.util.List;

import org.springframework.http.HttpStatus;

import com.project.lms.vo.request.ScoreAvgBySubjectVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScoreAvgBySubjectResponseVO {
    @Schema(description = "메세지" , example = "성공")
    private String message;
    @Schema (description = "상태" , example = "true")
    private Boolean status;
    @Schema (description = "코드" , example = ".OK")
    private HttpStatus code;
    private List<ScoreAvgBySubjectVO> subjectAvgList; 
}
