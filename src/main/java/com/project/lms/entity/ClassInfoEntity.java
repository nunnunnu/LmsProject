package com.project.lms.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "class_info")
public class ClassInfoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ci_seq")
    private Long ciSeq;

    @Column(name = "ci_name", nullable = false, length = 20)
    private String ciName;

    @Column(name = "ci_limit", nullable = false)
    private Integer ciLimit;

    @Column(name = "ci_mi_seq", nullable = false)
    private Long ciMiSeq;

}
