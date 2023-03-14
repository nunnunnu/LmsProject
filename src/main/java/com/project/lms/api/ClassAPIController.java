package com.project.lms.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.lms.service.ClassService;
import com.project.lms.vo.response.ClassResponseVO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/class")
@RequiredArgsConstructor
@Tag(description = "반 변경 API입니다.", name = "반")
public class ClassAPIController {
    private final ClassService cService;
    @Operation(summary = "반 변경")
    @PostMapping(value = "/{stuSeq}/{classSeq}")
    public ResponseEntity<ClassResponseVO> updateClass(@PathVariable Long stuSeq, @PathVariable Long classSeq) {
        return new ResponseEntity<>(cService.updateClass(stuSeq,classSeq), HttpStatus.OK);
    }
    
}
