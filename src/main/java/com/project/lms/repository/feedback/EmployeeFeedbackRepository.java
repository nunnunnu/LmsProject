package com.project.lms.repository.feedback;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.lms.entity.feedback.EmployeeFeedback;

public interface EmployeeFeedbackRepository extends JpaRepository<EmployeeFeedback, Long>{
    
}
