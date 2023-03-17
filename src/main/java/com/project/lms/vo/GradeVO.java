package com.project.lms.vo;

import lombok.Data;

@Data
public class GradeVO {
    private Long subject;
    private Long student;
    private Long teacher;
    private Long test;
    private Integer grade;
    
}
