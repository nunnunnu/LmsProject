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
@Table(name="notice_info")
public class NoticeInfoEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ni_seq") private Long niSeq;
    @Column(name = "ni_mi_seq") private Long niMiSeq;
    @Column(name = "create_dt") private LocalDate createDt;
    @Column(name = "update_dt") private LocalDate updateDt;
    @Column(name = "ni_tittle") private String niTittle;
    @Column(name = "ni_detail") private String niDetail;
    
}
