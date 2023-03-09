package com.project.lms.entity.feedback;

import com.project.lms.entity.share.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="employee_feedback")
// @SuperBuilder
public class EmployeeFeedback extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ef_seq") private Long efSeq;
    @Column(name="ef_mi_seq") private Long efMiSeq;
    @Column(name="ef_mi2_seq") private Long efMi2Seq;
    @Column(name="ef_content") private String efContent;
}