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
@DiscriminatorValue("stu") //StudentInfo DB에 저장하면 자동으로 상위테이블인 member_info의 mi_dtype칼럼에 stu로 저장됨
public class StudentInfo extends MemberInfoEntity {
    @Column(name="si_grade") private Integer siGrade; //학년
    @Column(name="si_school") private String siShcool; //학교

    public StudentInfo(MemberJoinVO data){
        super(data, Role.STUDENT); //상속받은 MemberInfoEntity의 생성자를 사용한다는 의미
        this.siGrade = data.getGrade();
        this.siShcool = data.getShcool();
    }
}