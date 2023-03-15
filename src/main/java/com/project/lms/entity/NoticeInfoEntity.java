package com.project.lms.entity;

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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="notice_info")
public class NoticeInfoEntity extends BaseTimeEntity{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ni_seq") private Long niSeq;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "ni_mi_seq") private MemberInfoEntity member;
    // @Column(name = "create_dt") private LocalDate createDt;
    // @Column(name = "update_dt") private LocalDate updateDt; //baseTimeEntity에서 상속받은 칼럼이 있어서 주석함
    @Column(name = "ni_tittle") private String niTittle;
    @Column(name = "ni_detail") private String niDetail;
    
}
