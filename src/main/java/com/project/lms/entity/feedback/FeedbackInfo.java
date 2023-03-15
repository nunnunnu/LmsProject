package com.project.lms.entity.feedback;

import com.project.lms.entity.member.StudentInfo;
import com.project.lms.entity.member.TeacherInfo;
import com.project.lms.entity.share.BaseTimeEntity;

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
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name="fi_mi_seq") private StudentInfo student;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name="fi_mi2_seq") private TeacherInfo teacher;
    @Column(name="fi_content") private String fiContent;
    // @Column(name="create_dt") private LocalDate createDt;
    // @Column(name="modify_dt") private LocalDate modifyDt; //baseTimeEntity에서 상속받은 칼럼이 있어서 주석함
    @Column(name="fi_cate") private Integer fiCate;
}