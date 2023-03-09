package com.project.lms.entity.member;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="teacher_info")
@SuperBuilder
@DiscriminatorValue("tea")
public class TeacherInfo extends MemberInfoEntity {
    @Column(name="ti_sub_seq") private Long tiSubSeq;
    @Column(name="ti_exp") private String tiExp;
}