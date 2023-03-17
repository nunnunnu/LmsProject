package com.project.lms.vo.response;

import java.util.List;

import org.springframework.http.HttpStatus;

import com.project.lms.vo.request.ScoreListBySubjectYearVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScoreListBySubjectYearResponseVO {
    @Schema(description = "메세지" , example = "성공")
    private String message;
     @Schema (description = "상태" , example = "true")
     private Boolean status;
     @Schema (description = "코드" , example = ".OK")
     private HttpStatus code;
    @Schema(description = "올해 과목 별 성적 리스트")
    private List<ScoreListBySubjectYearVO> scoreList;
    @Schema(description = "성적 통계 메세지 (전 달 시험과 비교)")
    private String explanation;
    private String weeknessSubject;
}
