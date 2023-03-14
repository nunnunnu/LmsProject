package com.project.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.lms.entity.ClassStudentEntity;

public interface ClassStudentRepository extends JpaRepository<ClassStudentEntity, Long>{
    
}
