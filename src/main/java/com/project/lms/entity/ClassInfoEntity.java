package com.project.lms.entity;

import com.project.lms.entity.member.EmployeeInfo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Entity
@Table(name = "class_info")
@AllArgsConstructor
@Getter
public class ClassInfoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ci_seq")
    private Long ciSeq;

    @Column(name = "ci_name", nullable = false, length = 20)
    private String ciName;

    @Column(name = "ci_limit", nullable = false)
    private Integer ciLimit;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "ci_mi_seq", nullable = false)
    private EmployeeInfo employee;

}
