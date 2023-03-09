package com.project.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.lms.entity.member.TeacherInfo;

public interface TeacherInfoRepository extends JpaRepository <TeacherInfo, Long> {
    
}
