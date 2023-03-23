package com.project.lms.repository.member;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.lms.entity.SubjectInfoEntity;
import com.project.lms.entity.member.TeacherInfo;

public interface TeacherInfoRepository extends JpaRepository <TeacherInfo, Long> {
    TeacherInfo findByMiId(String id);

    List<TeacherInfo> findBySubjectAndMiSeqIn(SubjectInfoEntity sub, List<TeacherInfo> teacher);
}
