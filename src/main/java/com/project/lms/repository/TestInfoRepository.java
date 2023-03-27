package com.project.lms.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.lms.entity.TestInfoEntity;

public interface TestInfoRepository extends JpaRepository<TestInfoEntity, Long>{
    TestInfoEntity findTop1ByOrderByTestDateDesc();

    @Query("SELECT ti FROM TestInfoEntity ti where ti.testDate  BETWEEN :start and :end")
    TestInfoEntity findbyStartAndEnd(@Param("start") LocalDate start, @Param("end") LocalDate end );
    
}
