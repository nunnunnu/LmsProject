package com.project.lms.entity;

import com.project.lms.entity.member.TeacherInfo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="class_teacher")
@Getter
public class ClassTeacherEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ct_seq") private Long ctSeq;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ct_ci_seq") private ClassInfoEntity classInfo;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ct_mi_seq") private TeacherInfo teacher;
}
