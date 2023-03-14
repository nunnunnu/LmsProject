package com.project.lms.repository;

import com.project.lms.entity.GradeInfoEntity;
import com.project.lms.entity.TestInfoEntity;
import com.project.lms.entity.member.StudentInfo;

import org.hibernate.boot.jaxb.hbm.spi.SubEntityInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GradeInfoRepository extends JpaRepository<GradeInfoEntity, Long> {
    List<GradeInfoEntity> findByStudent(StudentInfo student);
    GradeInfoEntity findByTest(TestInfoEntity test);

    // GradeInfoEntity findBySubject(SubEntityInfo subject);
}
