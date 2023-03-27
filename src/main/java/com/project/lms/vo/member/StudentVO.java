package com.project.lms.vo.member;

import java.time.LocalDate;

import com.project.lms.entity.ClassStudentEntity;
import com.project.lms.entity.member.StudentInfo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentVO {
    @Schema(description = "학생 고유번호") 
    Long seq;
    @Schema(description = "학생 명") 
    String name;
    @Schema(description = "학생 반")
    String className;
    @Schema(description = "학생 생년월일") 
    LocalDate birth;
    @Schema(description = " 학생 이메일") 
    String email;
    @Schema(description = "학생 학원 등록일") 
    LocalDate regDt;


    public StudentVO(StudentInfo entity, ClassStudentEntity entity1) {
        this.seq = entity.getMiSeq();
        this.name = entity.getMiName();
        this.email = entity.getMiEmail();
        this.className = entity1.getClassInfo().getCiName();
    }
}
