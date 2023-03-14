package com.project.lms.repository;

import com.project.lms.entity.ClassTeacherEntity;
import com.project.lms.entity.member.TeacherInfo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassTeacherRepository extends JpaRepository<ClassTeacherEntity, Long> {
    ClassTeacherEntity findByTeacher(TeacherInfo tInfo);
}
