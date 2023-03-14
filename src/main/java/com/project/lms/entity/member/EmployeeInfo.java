package com.project.lms.entity.member;

import com.project.lms.entity.member.enumfile.Department;
import com.project.lms.entity.member.enumfile.Position;
import com.project.lms.entity.member.enumfile.Role;
import com.project.lms.vo.member.MemberJoinVO;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name="employee_info")
@SuperBuilder
@DiscriminatorValue("emp")
public class EmployeeInfo extends MemberInfoEntity{
    @Enumerated(value = EnumType.STRING)
    @Column(name="ei_position") private Position eiPosition;
    @Enumerated(value = EnumType.STRING)
    @Column(name="ei_department") private Department eiDepartment;
    @Column(name="ei_exp") private String eiExp;

    public EmployeeInfo(MemberJoinVO data){
        super(data, Role.EMPLOYEE);
        this.eiDepartment=data.getDepartment();
        this.eiExp = data.getExp();
        this.eiPosition = data.getPosition();
    }
}
