package com.project.lms.controller;

import com.project.lms.service.ScoreBySubjectService;
import com.project.lms.vo.response.ScoreListBySubjectResponseVO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/score")
@RequiredArgsConstructor
public class ScoreController {
    private ScoreBySubjectService scoreBySubjectService;
    @GetMapping("/{student}")
    public ScoreListBySubjectResponseVO getScoreList(@AuthenticationPrincipal UserDetails userDetails){
        ScoreListBySubjectResponseVO result = scoreBySubjectService.getSubjectList(userDetails.getUsername());
        return result;
    }
}
