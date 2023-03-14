package com.project.lms.api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.lms.service.ScoreBySubjectService;
import com.project.lms.vo.response.ScoreListBySubjectResponseVO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/score")
@RequiredArgsConstructor
public class ScoreController {
    private ScoreBySubjectService scoreBySubjectService;
    @GetMapping("")
    public ScoreListBySubjectResponseVO getScoreList(@PathVariable String userID) {
        // System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaa"+userDetails);
        ScoreListBySubjectResponseVO result = scoreBySubjectService.getSubjectList(userID);
        return result;
    }
}
