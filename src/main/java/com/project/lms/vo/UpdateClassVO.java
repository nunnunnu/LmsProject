package com.project.lms.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UpdateClassVO {
    @Schema(description = "반 번호", example = "1")
    private Long ciSeq;
    @Schema(description = "변경된 반 번호", example = "2")
    private Long changeCiSeq;
}
