package com.project.lms.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.project.lms.entity.ClassTeacherEntity;
import com.project.lms.entity.member.TeacherInfo;

public interface ClassTeacherRepository extends JpaRepository<ClassTeacherEntity, Long> {
    @EntityGraph( attributePaths = {"classInfo"})
    ClassTeacherEntity findByTeacher(TeacherInfo entity);
}
