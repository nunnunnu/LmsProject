package com.project.lms.config.repository;

import com.project.lms.config.entity.GradeInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GradeInfoRepository extends JpaRepository<GradeInfoEntity, Long> {
}
