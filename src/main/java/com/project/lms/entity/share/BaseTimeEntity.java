package com.project.lms.entity.share;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@EntityListeners(AuditingEntityListener.class)
@Getter
@MappedSuperclass 
public class BaseTimeEntity { //모든 entity에서 공통적으로 자주사용되는 작성일, 수정일을 상속할 entity

    @CreatedDate
    @Column(updatable = false, name="create_dt")
    private LocalDateTime createdDate;
    
    @LastModifiedDate
    @Column(name="modify_dt")
    private LocalDateTime updatedDate;
}