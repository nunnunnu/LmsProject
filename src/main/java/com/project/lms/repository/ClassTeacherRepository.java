package com.project.lms.repository;

import com.project.lms.entity.ClassInfoEntity;
import com.project.lms.entity.ClassTeacherEntity;
import com.project.lms.entity.member.TeacherInfo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;

public interface ClassTeacherRepository extends JpaRepository<ClassTeacherEntity, Long> {
    @EntityGraph( attributePaths = {"classInfo"}) //fetch join사용을 위해 붙인 어노테이션. 반정보 테이블도 join해서 가져옴
    ClassTeacherEntity findByTeacher(TeacherInfo entity);   
    List<ClassTeacherEntity> findByClassInfo(ClassInfoEntity entity);
}
