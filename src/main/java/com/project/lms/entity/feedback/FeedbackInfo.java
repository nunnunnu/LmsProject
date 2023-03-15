package com.project.lms.entity.feedback;

import java.time.LocalDate;

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
@Table(name="feedback_info")
// @SuperBuilder
public class FeedbackInfo extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="fi_seq") private Long fiSeq;
    @Column(name="fi_mi_seq") private Long fiMiSeq;
    @Column(name="fi_mi2_seq") private Long fiMi2Seq;
    @Column(name="fi_title") private String fiTitle;
    @Column(name="fi_content") private String fiContent;
    @Column(name="create_dt") private LocalDate createDt;
    @Column(name="modify_dt") private LocalDate modifyDt;
    @Column(name="fi_cate") private Integer fiCate;
}