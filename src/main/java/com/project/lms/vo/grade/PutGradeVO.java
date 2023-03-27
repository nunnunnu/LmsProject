package com.project.lms.vo.grade;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PutGradeVO {
  private Long seq; 
    private Integer listening;
    private Integer reading;
    private Integer grammar;
    private Integer vocabulary;
   
}
