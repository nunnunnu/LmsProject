package com.project.lms.api;

import java.util.List;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.lms.service.ScoreAvgTotalService;
import com.project.lms.vo.grade.ScoreTop10Response;
import com.project.lms.vo.response.AvgListBuSubjectResponseVO;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/total")
@RequiredArgsConstructor
@Tag(name = "가장 최신 과목별 전체 평균", description = "과목 별 평균 조회 API")
public class ScoreAvgTotalControllaer {
    private final ScoreAvgTotalService sTotalService;
    //과목별 전체 평균 조회
    @GetMapping("/avg")
    @Secured("ROLE_TEACHER")
    public AvgListBuSubjectResponseVO getAvgList(@AuthenticationPrincipal UserDetails userDetails){
        AvgListBuSubjectResponseVO result = sTotalService.getSubjectTotalList(userDetails);
        return result;
    }
    @GetMapping("/top10")
    @Secured("ROLE_TEACHER")
    public List<ScoreTop10Response> getScoreTestTop10() {
        return sTotalService.getScoreTestTop10();
    }

}
