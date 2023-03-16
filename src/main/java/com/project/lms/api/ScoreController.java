package com.project.lms.api;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.lms.service.ScoreBySubjectService;
import com.project.lms.vo.MapVO;
import com.project.lms.vo.grade.SameGraderesponse;
import com.project.lms.vo.response.ScoreListBySubjectResponseVO;
import com.project.lms.vo.response.ScoreListBySubjectYearResponseVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "이번 년 과목 별 시험 결과 조회 및 시험 통계 메세지 출력", description = "", security = @SecurityRequirement(name = "bearerAuth"))
    @Secured("ROLE_STUDENT")
    public ScoreListBySubjectYearResponseVO getYearScoreList(@AuthenticationPrincipal UserDetails userDetails) {
        ScoreListBySubjectYearResponseVO result = scoreBySubjectService.getSubjectList2(userDetails.getUsername());
        return result;
    }

    //입력받은 시험의 같은 점수인 학생의 과목점수 조회
    @ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "시험정보를 찾을 수 없음(시험번호 오류) or 회원을 찾을 수 없음(회원번호 오류)", content = @Content(schema = @Schema(implementation = MapVO.class))),
        @ApiResponse(responseCode = "403", description = "토큰에러. 토큰을 넣었는데도 403에러가 발생한다면 로그인한 회원의 등급을 확인해주세요", content = @Content(schema = @Schema(implementation = MapVO.class))),
        @ApiResponse(responseCode = "200", description = "조회성공", content = @Content(array=@ArraySchema(schema = @Schema(implementation = SameGraderesponse.class))))})
    @Operation(summary = "동일 점수 학생 조회(선생님/직원기능)", description ="입력받은 시험의 번호로 해당 시험에 총 합계가 동일한 회원을 조회. 각 회원의 과목별 점수가 조회됨", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/same/{test}")
    @Secured({"ROLE_TEACHER","ROLE_EMPLOYEE"})
    public ResponseEntity<List<SameGraderesponse>> sameGradeStudent(@PathVariable Long test){
        return new ResponseEntity<>(scoreBySubjectService.sameGradeStudent(test), HttpStatus.OK);

    // 선생님 또는 직원 권한으로 해당 학생 이번 달 과목별 성적 조회 
    @GetMapping("/now/{student}")
    @Operation(summary = "선생님 권한으로 해당 학생 이번 달 과목 별 시험 결과 조회", description = "", security = @SecurityRequirement(name = "bearerAuth"))
    @Secured({"ROLE_TEACHER","ROLE_EMPLOYEE"})
    public ScoreListBySubjectResponseVO getStudentScoreList1(@AuthenticationPrincipal UserDetails userDetails,
            @Parameter(description ="조회할 학생의 고유번호") @PathVariable Long student) {
        ScoreListBySubjectResponseVO result = scoreBySubjectService.getStuSubjectList(userDetails, student);
        return result;
    }
    // 선생님 또는 직원 권한으로 해당 학생 이번 년 시험 결과 및 성적 통계 메세지 조회 
    @GetMapping("/year/{student}")
    @Operation(summary = "이번 년 과목 별 시험 결과 조회 및 시험 통계 메세지 출력", description = "", security = @SecurityRequirement(name = "bearerAuth"))
    @Secured({"ROLE_TEACHER","ROLE_EMPLOYEE"})
    public ScoreListBySubjectYearResponseVO getYearScoreList2(@AuthenticationPrincipal UserDetails userDetails,
     @Parameter(description ="조회할 학생의 고유번호") @PathVariable Long student) {
        ScoreListBySubjectYearResponseVO result = scoreBySubjectService.getStuSubjectList2(userDetails, student);
        return result;
    }
}
