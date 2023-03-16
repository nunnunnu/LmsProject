package com.project.lms.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.lms.service.ScoreStatsService;
import com.project.lms.vo.ScoreStatsByClassResponseVO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/stats")
@RequiredArgsConstructor
@Tag(name = "반별 통계 API (수정 중)", description = "반별 통계 조회 API")
public class ScoreStatsAPIController {
    private final ScoreStatsService scoreStatsService;

    @Operation(summary = "반 별 평균 조회", description = "반 별 평균을 조회할 수 있습니다.")
    @GetMapping("/avg/class")
    public ResponseEntity<ScoreStatsByClassResponseVO> getScoreStatsByClass(
        @Parameter(description = "조회하려는 반 식별 번호", example = "1") @PathVariable("class-seq") Long classSeq,
        @AuthenticationPrincipal UserDetails userDetails
    ){
        return new ResponseEntity<>(scoreStatsService.ClassScoreStats(classSeq, userDetails),HttpStatus.OK);
    }
}
