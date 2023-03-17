package com.project.lms.api;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.spring.security.api.authentication.JwtAuthentication;
import com.project.lms.security.provider.JwtTokenProvider;
import com.project.lms.service.GradeService;
import com.project.lms.service.ScoreBySubjectService;
import com.project.lms.vo.GradeVO;

import io.micrometer.common.lang.Nullable;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/grade")
@RequiredArgsConstructor
@Tag(description = "성적 관련 API입니다.", name = "성적")
public class GradeAPIController {
    private final GradeService gService;
    @PutMapping("")
    public ResponseEntity<Object> postCompanyAdd(
        // @Parameter(description = "점수", example = "100") @RequestParam @Nullable Long grade,
        // @Parameter(description = "과목번호", example = "1") @RequestParam @Nullable Long subject,
        // @Parameter(description = "학생번호", example = "1") @RequestParam @Nullable Long student,
        // @Parameter(description = "선생님번호", example = "1") @RequestParam @Nullable Long teacher,
        // @Parameter(description = "시험번호", example = "1") @RequestParam @Nullable Long test) {
        @RequestBody GradeVO data){
        Map<String, Object> map = gService.addGradeInfo(data);
        return new ResponseEntity<>(map, HttpStatus.ACCEPTED);
    }
}