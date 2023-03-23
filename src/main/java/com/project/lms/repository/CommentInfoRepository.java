package com.project.lms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.lms.entity.CommentInfoEntity;
import com.project.lms.entity.feedback.FeedbackInfo;

public  interface CommentInfoRepository extends JpaRepository <CommentInfoEntity ,Long> {
    List<CommentInfoEntity> findByFeedback (FeedbackInfo feedback);
    
}
