package com.project.lms.api;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.lms.service.ScoreBySubjectService;
import com.project.lms.vo.response.ScoreListBySubjectResponseVO;
import com.project.lms.vo.response.ScoreListBySubjectYearResponseVO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/score/list")
@RequiredArgsConstructor
public class ScoreController {
    private final ScoreBySubjectService scoreBySubjectService;
    // 이번 달 시험 결과 조회
    @GetMapping("/now")
    @Operation(summary = "", description ="", security = @SecurityRequirement(name = "bearerAuth"))
    @Secured("ROLE_STUDENT")
    public ScoreListBySubjectResponseVO getScoreList(@AuthenticationPrincipal UserDetails userDetails) {
        // System.out.println(userDetails.getUsername());  // 해당 id 값을 불러올 수 있는 test  
        ScoreListBySubjectResponseVO result = scoreBySubjectService.getSubjectList(userDetails.getUsername());
        return result;
    }

    // 이번 년 시험 결과 조회
     @GetMapping("/year")
    public ScoreListBySubjectYearResponseVO getScoreList2(@AuthenticationPrincipal UserDetails userDetails) {
        ScoreListBySubjectYearResponseVO result = scoreBySubjectService.getSubjectList2(userDetails.getUsername());
        return result;
    }
}
