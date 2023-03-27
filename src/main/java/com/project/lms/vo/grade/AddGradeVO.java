package com.project.lms.vo.grade;

import java.time.Year;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddGradeVO {
    @JsonFormat(pattern = "yyyyMM")
    private YearMonth yearmonth;
    private List<PutGradeVO> AddGradeVO = new ArrayList<>();
}
