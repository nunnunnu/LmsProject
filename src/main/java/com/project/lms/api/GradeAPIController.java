package com.project.lms.api;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.lms.service.GradeService;
import com.project.lms.vo.GradeVO;
import com.project.lms.vo.MapVO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/grade")
@RequiredArgsConstructor
@Tag(description = "성적 입력 API입니다.", name = "성적 입력")
public class GradeAPIController {
    private final GradeService gService;
    @Operation(summary = "성적 입력", description ="과목번호, 학생번호, 선생님번호, 시험번호, 점수를 통해 성적을 입력합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "403", description = "토큰이 들어오지 않았거나, 회원의 등급이 선생님이 아닐때 접근이 차단됩니다. 토큰을 넣었는데 403 에러가뜨면 로그인한 회원의 분류를 확인해주세요", content = @Content(schema = @Schema(implementation = MapVO.class)))})
    @PutMapping("")
    @Secured("ROLE_TEACHER")
    public ResponseEntity<MapVO> addGradeInfo(
        @RequestBody GradeVO data){
        return new ResponseEntity<>(gService.addGradeInfo(data), HttpStatus.OK);
    }
}