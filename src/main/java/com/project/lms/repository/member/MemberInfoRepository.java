package com.project.lms.repository.member;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.lms.entity.member.MemberInfoEntity;

public interface MemberInfoRepository extends JpaRepository<MemberInfoEntity, Long>{
    MemberInfoEntity findByMiIdAndMiPwd (String miId, String miPwd);
    MemberInfoEntity findByMiId(String username);
    MemberInfoEntity findByMiNameAndMiBirthAndMiEmail (String name,LocalDate birth, String email);
    MemberInfoEntity findByMiIdAndMiNameAndMiEmail (String id,String name, String email);
    Boolean existsByMiId(String userId);
    MemberInfoEntity findByMiSeq(Long miSeq);
}
