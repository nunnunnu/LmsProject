package com.project.lms.repository.member;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.lms.entity.member.EmployeeInfo;

public interface EmployeeInfoRepository extends JpaRepository <EmployeeInfo ,Long> {
    
}
