package com.project.lms.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.lms.entity.ClassInfoEntity;
import com.project.lms.entity.ClassStudentEntity;
import com.project.lms.entity.member.StudentInfo;

public interface ClassStudentRepository extends JpaRepository<ClassStudentEntity, Long>{

    @EntityGraph( attributePaths = {"classInfo"}) //fetch join사용을 위해 붙인 어노테이션. student 테이블도 join해서 가져옴
    ClassStudentEntity findByStudent(StudentInfo s);

    // @EntityGraph( attributePaths = {"student"}) //fetch join사용을 위해 붙인 어노테이션. student 테이블도 join해서 가져옴
    //paging과 fetch join을 함께쓰면 안되는데 간과하고 사용함. 코드 수정
    @Query("select cs from ClassStudentEntity cs join StudentInfo s on s.miSeq = cs.student.miSeq where s.miStatus = :status and cs.classInfo = :class")
    Page<ClassStudentEntity> findByClassInfo(@Param("class") ClassInfoEntity classInfo, @Param("status") boolean status, Pageable page);
}
