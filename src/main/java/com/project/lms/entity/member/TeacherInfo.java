package com.project.lms.entity.member;

import com.project.lms.entity.SubjectInfoEntity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
// import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
// @Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="teacher_info")
@SuperBuilder
@DiscriminatorValue("tea")
public class TeacherInfo extends MemberInfoEntity {
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name="ti_sub_seq") private SubjectInfoEntity subject;
    @Column(name="ti_exp") private String tiExp;
}