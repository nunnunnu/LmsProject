package com.project.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.lms.entity.TestInfoEntity;

public interface TestInfoRepository extends JpaRepository<TestInfoEntity, Long>{
    
}
