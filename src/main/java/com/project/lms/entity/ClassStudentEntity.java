package com.project.lms.entity;

import com.project.lms.entity.member.StudentInfo;

import jakarta.persistence.*;

@Entity
@Table(name = "class_student")
public class ClassStudentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cs_seq") private Long csSeq;
    @OneToOne
    @JoinColumn(name = "cs_ci_seq", nullable = false) private ClassInfoEntity classInfo;
    @OneToOne
    @JoinColumn(name = "cs_mi_seq", nullable = false) private StudentInfo student;
}