package com.project.lms.repository.feedback;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.lms.entity.feedback.TeacherFeedback;

public interface TeacherFeedbackRepository extends JpaRepository<TeacherFeedback, Long> {
    
}
