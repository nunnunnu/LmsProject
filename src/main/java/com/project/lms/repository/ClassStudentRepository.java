package com.project.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.lms.entity.ClassStudentEntity;
import com.project.lms.entity.member.StudentInfo;

public interface ClassStudentRepository extends JpaRepository<ClassStudentEntity, Long>{

    ClassStudentEntity findByStudent(StudentInfo s);
    
}
