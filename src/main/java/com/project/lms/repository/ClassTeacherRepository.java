package com.project.lms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.project.lms.entity.ClassInfoEntity;
import com.project.lms.entity.ClassTeacherEntity;
import com.project.lms.entity.SubjectInfoEntity;
import com.project.lms.entity.member.TeacherInfo;

public interface ClassTeacherRepository extends JpaRepository<ClassTeacherEntity, Long> {
    @EntityGraph( attributePaths = {"classInfo"}) //fetch join사용을 위해 붙인 어노테이션. 반정보 테이블도 join해서 가져옴
    ClassTeacherEntity findByTeacher(TeacherInfo entity);   
    @EntityGraph( attributePaths = {"teacher"}) //fetch join사용을 위해 붙인 어노테이션. 반정보 테이블도 join해서 가져옴
    List<ClassTeacherEntity> findByClassInfo(ClassInfoEntity entity);
}
