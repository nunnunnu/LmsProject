package com.project.lms.entity;
import com.project.lms.entity.member.MemberInfoEntity;
import com.project.lms.entity.member.StudentInfo;
import com.project.lms.entity.member.TeacherInfo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "grade_info")
public class GradeInfoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gi_seq")                private Long seq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gi_sub_seq")            private SubjectInfoEntity subject;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gi_mi_seq1", nullable = false)    private StudentInfo student;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gi_mi_seq2", nullable = false) private TeacherInfo teacher;

    @Column(name = "gi_grade")              private Integer grade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gi_test_seq", nullable = false)private TestInfoEntity test;
}
