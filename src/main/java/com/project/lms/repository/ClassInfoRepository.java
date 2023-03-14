package com.project.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.lms.entity.ClassInfoEntity;

public interface ClassInfoRepository extends JpaRepository<ClassInfoEntity, Long>{
    
}
