package com.project.lms.vo.response;

import java.util.List;

import org.springframework.http.HttpStatus;

import com.project.lms.vo.request.AvgBySubjectTotalVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AvgListBuSubjectResponseVO {
    
    @Schema(description = "message", example = "성공")
    private String message;
    @Schema(description = "status", example = "true")
    private Boolean status;
    @Schema(description = "code", example = "OK")
    private HttpStatus code;
    private List<AvgBySubjectTotalVO> avgList;
    }

