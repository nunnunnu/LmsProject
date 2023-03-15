package com.project.lms.entity;

import com.project.lms.entity.member.StudentInfo;

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

@Entity
@Table(name = "class_student")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ClassStudentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cs_seq")
    private Long csSeq;

    @OneToOne(fetch = FetchType.LAZY) //onetoone은 양방향 매핑을 사용하면 연관관계 주인이 아닌곳에서 LAZY가 동작하지않으나 단방향 매핑이라서 LAZY설정해줌
    @JoinColumn(name = "cs_ci_seq", nullable = false) private ClassInfoEntity classInfo;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cs_mi_seq", nullable = false) private StudentInfo student;

    public void changeClass(ClassInfoEntity classInfo){
        this.classInfo = classInfo;
    }
}