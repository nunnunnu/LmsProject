package com.project.lms.entity;

import com.project.lms.entity.feedback.FeedbackInfo;
import com.project.lms.entity.member.MemberInfoEntity;
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
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="comment_info")
public class CommentInfoEntity extends BaseTimeEntity{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cmt_seq") private Long cmtSeq;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "cmt_mi_seq") private MemberInfoEntity member;
    // @Column(name = "create_dt") private LocalDate createDt;
    // @Column(name = "update_dt") private LocalDate updateDt;
    @Column(name = "cmt_title") private String cmtTitle;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "cmt_fi_seq") private FeedbackInfo feedback;
    
    
}
