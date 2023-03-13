package com.project.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.lms.entity.CommentInfoEntity;

public  interface CommentInfoRepository extends JpaRepository <CommentInfoEntity ,Long> {
    
}
