package com.project.lms.vo.grade;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentClassGradeVO {
    private Long seq;
    private String name;
    private LocalDate birth;
    private int total;
    private String originClass;
    private String changeClass;
    private String status;

    public void changeClassAndStatusSetting(String name){
        this.changeClass = name;
        if(originClass.equals(name)){
            this.status = "유지";
        }else{
            this.status = "변경";
        }
    }

}
