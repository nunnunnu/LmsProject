package com.project.lms.repository.feedback;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.lms.entity.feedback.FeedbackInfo;
import com.project.lms.entity.member.TeacherInfo;

public interface FeedbackInfoRepository extends JpaRepository<FeedbackInfo, Long> {
    List<FeedbackInfo> findByTeacher(TeacherInfo entity);
    FeedbackInfo findByTeacher(TeacherInfo entity);
}
