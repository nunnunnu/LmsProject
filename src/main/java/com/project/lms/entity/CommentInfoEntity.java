package com.project.lms.entity;

import java.time.LocalDate;

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
@Table(name="comment_info")
public class CommentInfoEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cmt_seq") private Long cmtSeq;
    @Column(name = "cmt_mi_seq") private Long cmtMiSeq;
    @Column(name = "create_dt") private LocalDate createDt;
    @Column(name = "update_dt") private LocalDate updateDt;
    @Column(name = "cmt_title") private String cmtTitle;
    @Column(name = "cmt_detail") private String cmtDetail;
    
}
