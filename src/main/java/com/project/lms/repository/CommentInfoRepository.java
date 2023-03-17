package com.project.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.lms.entity.CommentInfoEntity;
import com.project.lms.entity.feedback.FeedbackInfo;

public  interface CommentInfoRepository extends JpaRepository <CommentInfoEntity ,Long> {
    CommentInfoEntity findByFeedback (FeedbackInfo feedback);
    
}
