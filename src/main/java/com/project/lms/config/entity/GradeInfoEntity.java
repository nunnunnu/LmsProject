package com.project.lms.config.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "grade_info")
public class GradeInfoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gi_seq")                private Long seq;
    @ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "gi_sub_seq")            private Long sub_seq;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gi_mi_seq1", nullable = false)
    @Column(name = "gi_mi_seq1")            private Long mi_seq1;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gi_mi_seq2", nullable = false)
    @Column(name = "gi_mi_seq2")            private Long mi_seq2;
    @Column(name = "gi_grade")              private Integer grade;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gi_ci_seq", nullable = false)
    @Column(name = "gi_ci_seq")             private Long ci_seq;
}
