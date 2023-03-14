package com.project.lms.vo.response;

import com.project.lms.vo.request.ScoreListBySubjectVO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScoreListBySubjectResponseVO {
    private String message;
    private Boolean status;
    private List<ScoreListBySubjectVO> scoreList;
}
