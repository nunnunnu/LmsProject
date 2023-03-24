package com.project.lms.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "subject_info")
public class SubjectInfoEntity {

    @Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sub_seq") 
    private Long subSeq;
    
    @Column(name = "sub_name") 
    private String subName;

    public SubjectInfoEntity(String subName) {
        this.subName = subName;
    }
}
