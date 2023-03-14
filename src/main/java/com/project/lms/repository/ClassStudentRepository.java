package com.project.lms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.lms.entity.ClassInfoEntity;
import com.project.lms.entity.ClassStudentEntity;

public interface ClassStudentRepository extends JpaRepository<ClassStudentEntity, Long>{
    List<ClassStudentEntity> findByClassInfo(ClassInfoEntity classInfo);
}
