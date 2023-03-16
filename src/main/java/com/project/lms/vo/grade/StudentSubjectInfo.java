package com.project.lms.vo.grade;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.project.lms.entity.GradeInfoEntity;
import com.project.lms.entity.member.StudentInfo;

import lombok.Data;

@Data
public class StudentSubjectInfo {
    private String name;
    private Map<String, Integer> info = new LinkedHashMap<>();

    public StudentSubjectInfo(List<GradeInfoEntity> list, StudentInfo studetn){
        for(GradeInfoEntity g : list){
            this.info.put(g.getSubject().getSubName(), g.getGrade());
        }
        this.name = studetn.getMiName();
    }
}
