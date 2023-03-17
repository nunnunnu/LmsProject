package com.project.lms.api;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.project.lms.service.ScoreBySubjectService;
import com.project.lms.vo.response.ScoreListBySubjectResponseVO;
import com.project.lms.vo.response.ScoreListBySubjectYearResponseVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
@Tag(name = "과목 별 점수 정보 관리", description = "과목 별 점수 조회 API")
@RestController
@RequestMapping("/api/score/list")
@RequiredArgsConstructor
public class ScoreController {
    private final ScoreBySubjectService scoreBySubjectService;
    // 이번 달 시험 결과 조회
    @GetMapping("/now")
    @Operation(summary = "이번 달 과목 별 시험 결과 조회", description = "", security = @SecurityRequirement(name = "bearerAuth"))
    @Secured("ROLE_STUDENT")
    public ScoreListBySubjectResponseVO getMonthScoreList(@AuthenticationPrincipal UserDetails userDetails) {
        ScoreListBySubjectResponseVO result = scoreBySubjectService.getSubjectList(userDetails.getUsername());
        return result;
    }
    // 이번 년 시험 결과 및 성적 통계 메세지 조회
    @GetMapping("/year")
    @Operation(summary = "올해 과목 별 시험 결과 조회 및 시험 통계 메세지 출력", description = "", security = @SecurityRequirement(name = "bearerAuth"))
    @Secured("ROLE_STUDENT")
    public ScoreListBySubjectYearResponseVO getYearScoreList(@AuthenticationPrincipal UserDetails userDetails) {
        ScoreListBySubjectYearResponseVO result = scoreBySubjectService.getSubjectList2(userDetails.getUsername());
        return result;
    }
    // 선생님 또는 직원 권한으로 해당 학생 이번 달 과목별 성적 조회
    @GetMapping("/now/{student}")
    @Operation(summary = "선생님/ 직원 권한으로 해당 학생 이번 달 과목 별 시험 결과 조회", description = "", security = @SecurityRequirement(name = "bearerAuth"))
    @Secured({"ROLE_TEACHER","ROLE_EMPLOYEE"})
    public ScoreListBySubjectResponseVO getStudentScoreList1(@AuthenticationPrincipal UserDetails userDetails,
            @Parameter(description ="조회할 학생의 고유번호") @PathVariable Long student) {
        ScoreListBySubjectResponseVO result = scoreBySubjectService.getStuSubjectList(userDetails, student);
        return result;
    }
    // 선생님 또는 직원 권한으로 해당 학생 이번 년 시험 결과 및 성적 통계 메세지 조회
    @GetMapping("/year/{student}")
    @Operation(summary = "선생님/ 직원 권한으로 올해 과목 별 시험 결과 조회 및 시험 통계 메세지 출력", description = "", security = @SecurityRequirement(name = "bearerAuth"))
    @Secured({"ROLE_TEACHER","ROLE_EMPLOYEE"})
    public ScoreListBySubjectYearResponseVO getYearScoreList2(@AuthenticationPrincipal UserDetails userDetails,
            @Parameter(description = "조회할 학생의 고유번호") @PathVariable Long student) {
        ScoreListBySubjectYearResponseVO result = scoreBySubjectService.getStuSubjectList2(userDetails, student);
        return result;
    }

    
}