package com.project.lms.entity;

import com.project.lms.entity.member.StudentInfo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "class_student")
@AllArgsConstructor
@NoArgsConstructor
public class ClassStudentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cs_seq")
    private Long csSeq;

    @OneToOne
    @JoinColumn(name = "cs_ci_seq", nullable = false) private ClassInfoEntity classInfo;
    @OneToOne
    @JoinColumn(name = "cs_mi_seq", nullable = false) private StudentInfo student;
}