package com.project.lms.entity;

import com.project.lms.entity.member.MemberInfoEntity;
import com.project.lms.entity.member.TeacherInfo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.*;

@Entity
@Table(name="class_teacher")
public class ClassTeacherEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ct_seq") private Long ctSeq;
    @OneToOne
    @JoinColumn(name = "ct_ci_seq") private ClassStudentEntity classStudentEntity;
    @OneToOne
    @JoinColumn(name = "ct_mi_seq") private TeacherInfo teacher;
}
