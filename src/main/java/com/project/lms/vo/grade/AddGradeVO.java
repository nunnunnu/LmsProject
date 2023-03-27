package com.project.lms.vo.grade;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddGradeVO {
    private Long seq;
    private Integer listening;
    private Integer reading;
    private Integer grammar;
    private Integer vocabulary;
}
