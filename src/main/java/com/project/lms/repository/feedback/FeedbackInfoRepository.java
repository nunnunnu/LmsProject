package com.project.lms.repository.feedback;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.project.lms.entity.feedback.FeedbackInfo;
import com.project.lms.entity.member.TeacherInfo;

public interface FeedbackInfoRepository extends JpaRepository<FeedbackInfo, Long> {
    List<FeedbackInfo> findByTeacher(TeacherInfo entity);
    Page<FeedbackInfo> findByTeacher(TeacherInfo teacher, Pageable page);
    // FeedbackInfo finByTeacher(TeacherInfo teacher);
    // FeedbackInfo findByTeacher(TeacherInfo entity);
}
