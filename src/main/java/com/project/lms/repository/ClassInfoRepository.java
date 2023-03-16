package com.project.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.lms.entity.ClassInfoEntity;
import com.project.lms.entity.member.MemberInfoEntity;

public interface ClassInfoRepository extends JpaRepository<ClassInfoEntity, Long>{
    ClassInfoEntity findByEmployee(MemberInfoEntity entity);
}
