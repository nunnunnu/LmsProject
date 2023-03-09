package com.project.lms.entity.member;

import java.time.LocalDate;

import com.project.lms.entity.member.enumfile.Role;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
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
@Table(name="member_info")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name="mi_dtype")//기본값이 DTYPE
@SuperBuilder
public class MemberInfoEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="mi_seq") private Long miSeq;
    @Column(name="mi_id") private String miId;
    @Column(name="mi_pwd") private String miPwd;
    @Enumerated(value = EnumType.STRING)
    @Column(name="mi_role") private Role miRole;
    @Column(name="mi_name") private String miName;
    @Column(name="mi_birth") private LocalDate miBirth;
    @Column(name="mi_email") private String miEmail;
    @Column(name="mi_reg_dt") private LocalDate miRegDt;
    @Column(name="mi_status") private Boolean miStatus;
    // @Column(name="mi_dtype") private String miDtype;

}
