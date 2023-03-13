package com.project.lms.entity.member;

import com.project.lms.entity.member.enumfile.Role;
import com.project.lms.vo.member.MemberJoinVO;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
// @Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="student_info")
@SuperBuilder
@DiscriminatorValue("stu")
public class StudentInfo extends MemberInfoEntity {
    @Column(name="si_grade") private Integer siGrade; //학년
    @Column(name="si_shcool") private String siShcool; //학교

    public StudentInfo(MemberJoinVO data){
        super(data, Role.ROLE_STUDENT);
        this.siGrade = data.getGrade();
        this.siShcool = data.getShcool();
    }
}