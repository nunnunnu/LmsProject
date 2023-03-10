package com.project.lms.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "class_student")
public class ClassStudentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cs_seq")
    private Long csSeq;

    @Column(name = "cs_ci_seq", nullable = false)
    private Long csCiSeq;

    @Column(name = "cs_mi_seq", nullable = false)
    private Long csMiSeq;

}