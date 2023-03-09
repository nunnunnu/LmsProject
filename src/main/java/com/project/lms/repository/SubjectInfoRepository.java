package com.project.lms.repository;

import com.project.lms.entity.SubjectInfoEntity;
import com.project.lms.entity.member.StudentInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectInfoRepository extends JpaRepository<SubjectInfoEntity,Long> {
}
