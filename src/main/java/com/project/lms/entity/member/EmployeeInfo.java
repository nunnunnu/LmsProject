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
@DiscriminatorValue("emp") //EmployeeInfo를 DB에 저장하면 자동으로 상위테이블인 member_info의 mi_dtype칼럼에 emp로 저장됨
public class EmployeeInfo extends MemberInfoEntity{
    @Enumerated(value = EnumType.STRING) //설정안하면 enum이 저장된 위치로 DB에 저장됨. enum순서가 틀어지면 DB가 꼬일 가능성이 있어서 순서로 저장하는것은 권장하는 방법이 아님
    @Column(name="ei_position") private Position eiPosition;
    @Enumerated(value = EnumType.STRING) //설정안하면 enum이 저장된 위치로 DB에 저장됨. enum순서가 틀어지면 DB가 꼬일 가능성이 있어서 순서로 저장하는것은 권장하는 방법이 아님
    @Column(name="ei_department") private Department eiDepartment;
    @Column(name="ei_exp") private String eiExp;

    public EmployeeInfo(MemberJoinVO data){
        super(data, Role.EMPLOYEE); //상속받은 MemberInfoEntity의 생성자를 사용한다는 의미
        this.eiDepartment=data.getDepartment();
        this.eiExp = data.getExp();
        this.eiPosition = data.getPosition();
    }
}
