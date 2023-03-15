package com.project.lms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Entity
@Table(name = "subject_info")
@AllArgsConstructor
@Getter
public class SubjectInfoEntity {
    @Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sub_seq") private Long subSeq;
    @Column(name = "sub_name") private String subName;

    public String getSubName() {
        return subName;
    }
}
