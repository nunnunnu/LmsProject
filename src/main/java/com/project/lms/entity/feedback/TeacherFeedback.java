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
@Table(name="teacher_feedback")
// @SuperBuilder
public class TeacherFeedback extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="tf_seq") private Long tfSeq;
    @Column(name="tf_mi_seq") private Long tfMiSeq;
    @Column(name="tf_mi2_seq") private Long tfMi2Seq;
    @Column(name="tf_content") private String tfContent;
}