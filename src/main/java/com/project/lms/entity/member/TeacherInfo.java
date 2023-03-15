package com.project.lms.entity.member;

import com.project.lms.entity.SubjectInfoEntity;
import com.project.lms.entity.member.enumfile.Role;
import com.project.lms.vo.member.MemberJoinVO;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
// import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
// @Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="teacher_info")
@SuperBuilder
@DiscriminatorValue("tea") //TeacherInfo DB에 저장하면 자동으로 상위테이블인 member_info의 mi_dtype칼럼에 tea로 저장됨
public class TeacherInfo extends MemberInfoEntity {
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name="ti_sub_seq") private SubjectInfoEntity subject;
    @Column(name="ti_exp") private String tiExp;

    public TeacherInfo(MemberJoinVO data, SubjectInfoEntity sub){
        super(data, Role.TEACHER); //상속받은 MemberInfoEntity의 생성자를 사용한다는 의미
        this.tiExp = data.getExp();
        this.subject = sub;
    }
}