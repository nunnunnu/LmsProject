package com.project.lms.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.lms.service.GradeService;
import com.project.lms.vo.GradeVO;
import com.project.lms.vo.MapVO;
import com.project.lms.vo.grade.AddGradeVO;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/grade")
@RequiredArgsConstructor
@Tag(description = "성적 관련 API입니다.", name = "성적")
public class GradeAPIController {
    private final GradeService gService;
    @PutMapping("")
    public ResponseEntity<MapVO> addGradeInfo(
        // @Parameter(description = "점수", example = "100") @RequestParam @Nullable Long grade,
        // @Parameter(description = "과목번호", example = "1") @RequestParam @Nullable Long subject,
        // @Parameter(description = "학생번호", example = "1") @RequestParam @Nullable Long student,
        // @Parameter(description = "선생님번호", example = "1") @RequestParam @Nullable Long teacher,
        // @Parameter(description = "시험번호", example = "1") @RequestParam @Nullable Long test) {
        @RequestBody GradeVO data){
        return new ResponseEntity<>(gService.addGradeInfo(data), HttpStatus.OK);
    }
    @PutMapping("/put")
    @Secured("ROLE_TEACHER")
    public ResponseEntity<MapVO> putGradeInfo(
        @RequestBody AddGradeVO data, @AuthenticationPrincipal UserDetails user){
        return new ResponseEntity<>(gService.putGradeInfo(data, user), HttpStatus.OK);
    }
}
