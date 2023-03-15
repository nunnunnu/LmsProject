package com.project.lms.repository;

import com.project.lms.entity.ClassTeacherEntity;
import com.project.lms.entity.member.TeacherInfo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;

public interface ClassTeacherRepository extends JpaRepository<ClassTeacherEntity, Long> {
    @EntityGraph( attributePaths = {"classInfo"})
    ClassTeacherEntity findByTeacher(TeacherInfo entity);

}
