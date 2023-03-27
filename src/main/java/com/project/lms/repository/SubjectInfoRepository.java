package com.project.lms.repository;
import com.project.lms.entity.SubjectInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectInfoRepository extends JpaRepository<SubjectInfoEntity,Long> {
    SubjectInfoEntity findBySubSeq(Long subSeq);
    SubjectInfoEntity findBySubName(String subName);
}
