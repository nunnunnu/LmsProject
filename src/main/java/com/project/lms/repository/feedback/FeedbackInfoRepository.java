package com.project.lms.repository.feedback;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.lms.entity.feedback.FeedbackInfo;

public interface FeedbackInfoRepository extends JpaRepository<FeedbackInfo, Long> {
    FeedbackInfo findByfiMi2Id(String id);
}
