package com.project.lms.repository.member;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.lms.entity.member.StudentInfo;

public interface StudentInfoRepository extends JpaRepository<StudentInfo ,Long> {
    
}
