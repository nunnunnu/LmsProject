package com.project.lms.api;

import java.time.YearMonth;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.lms.service.ScoreStatsService;
import com.project.lms.vo.ScoreAllListBySubjectVO;
import com.project.lms.vo.ScoreAvgStatsListByClassVO;
import com.project.lms.vo.ScoreListByYearMonthVO;
import com.project.lms.vo.response.ScoreRankBySubjectVO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/stats")
@RequiredArgsConstructor
@Tag(name = "성적 조회 및 통계 API", description = "학생의 성적을 조회하거나 통계(반별+과목별 평균 / 과목별 랭킹)")
public class ScoreStatsAPIController {
    private final ScoreStatsService scoreStatsService;
    

    @Operation(summary = "연월별 학생 성적 조회", description = "연월별(시험별)로 학생들의 성적을 조회합니다.")
    @GetMapping("/list")
    public ResponseEntity<List<ScoreAllListBySubjectVO>> getScoreListByYearMonth(
        @Parameter(description = "조회하려는 연월", example = "2023-03") @RequestParam("yearMonth") YearMonth yearMonth,
        @Parameter(hidden = true) @PageableDefault(size=10, sort="id", direction = Sort.Direction.DESC) Pageable pageable,
        @AuthenticationPrincipal UserDetails userDetails
    ){
        System.out.println(yearMonth);
        return new ResponseEntity<>(scoreStatsService.ScoreList(yearMonth, userDetails, pageable),HttpStatus.OK); //scoreStatsService의 ClassScoreStats를 호출한다.
    }


    @Operation(summary = "반 별 과목 평균 조회", description = "반 별로 과목별 평균을 조회합니다.")
    @GetMapping("/avg")
    public ResponseEntity<List<ScoreAvgStatsListByClassVO>> getScoreStatsByClass(
        @Parameter(description = "조회하려는 연월", example = "202303") @RequestParam("yearMonth") Integer yearMonth,
        @AuthenticationPrincipal UserDetails userDetails
    ){
        return new ResponseEntity<>(scoreStatsService.ClassScoreStats(yearMonth, userDetails),HttpStatus.OK); //scoreStatsService의 ClassScoreStats를 호출한다.
    }


    @Operation(summary = "과목별 점수 높은 순/낮은순 학생 조회", description = "과목별 점수 높은 순과 낮은 순으로 학생을 조회합니다.")
    @GetMapping("/rank")
    public ResponseEntity<List<ScoreRankBySubjectVO>> getScoreRankBySubject(
        @Parameter(description = "조회하려는 과목 식별 번호", example = "1") @RequestParam("subjectSeq") Long subjectSeq,
        @AuthenticationPrincipal UserDetails userDetails
    ){
        return new ResponseEntity<>(scoreStatsService.ScoreRank(subjectSeq, userDetails),HttpStatus.OK); //scoreStatsService의 ScoreRank를 호출한다.
    }
}
