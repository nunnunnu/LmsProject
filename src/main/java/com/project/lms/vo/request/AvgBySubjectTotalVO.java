package com.project.lms.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;

public interface AvgBySubjectTotalVO {
    @Schema(description = "과목이름")
    String getSubjectName();
    @Schema(description = "과목 전체 평균")
    Double getTotalAvg();
}
