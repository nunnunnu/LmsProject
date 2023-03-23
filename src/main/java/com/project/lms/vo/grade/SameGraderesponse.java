package com.project.lms.vo.grade;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class SameGraderesponse {
    private Integer samaGrade;
    private List<StudentSubjectInfo> studentList= new ArrayList<>();

    public SameGraderesponse(Integer score){
        this.samaGrade = score;
    }

    public void addStudentInfo(StudentSubjectInfo student){
        studentList.add(student);
    }
    
}
