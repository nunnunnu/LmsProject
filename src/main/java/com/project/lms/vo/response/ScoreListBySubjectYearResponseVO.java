package com.project.lms.vo.response;

import java.util.List;

import com.project.lms.vo.request.ScoreListBySubjectYearVO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScoreListBySubjectYearResponseVO {
    private String message;
    private Boolean status;
    private List<ScoreListBySubjectYearVO> scoreList;
}
