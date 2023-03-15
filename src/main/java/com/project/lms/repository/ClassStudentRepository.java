package com.project.lms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.project.lms.entity.ClassInfoEntity;
import com.project.lms.entity.ClassStudentEntity;
import com.project.lms.entity.member.StudentInfo;

public interface ClassStudentRepository extends JpaRepository<ClassStudentEntity, Long>{

    ClassStudentEntity findByStudent(StudentInfo s);

    @EntityGraph( attributePaths = {"student"}) //fetch join사용을 위해 붙인 어노테이션. student 테이블도 join해서 가져옴
    List<ClassStudentEntity> findByClassInfo(ClassInfoEntity classInfo);
}
