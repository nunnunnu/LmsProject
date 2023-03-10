package com.project.lms.repository.feedback;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.lms.entity.feedback.FeedbackInfo;

public interface TeacherFeedbackRepository extends JpaRepository<FeedbackInfo, Long> {
    
}
