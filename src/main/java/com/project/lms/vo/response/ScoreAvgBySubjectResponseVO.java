package com.project.lms.vo.response;

import java.util.List;

import com.project.lms.vo.request.ScoreAvgBySubjectVO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScoreAvgBySubjectResponseVO {
    private String message;
    private Boolean code;
    private List<ScoreAvgBySubjectVO> subjectAvgList; 
}
