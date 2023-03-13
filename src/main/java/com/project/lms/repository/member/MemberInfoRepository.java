package com.project.lms.repository.member;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.lms.entity.member.MemberInfoEntity;

public interface MemberInfoRepository extends JpaRepository<MemberInfoEntity, Long>{
   MemberInfoEntity findByMiIdAndMiPwd (String miId, String miPwd);
    MemberInfoEntity findByMiId(String username);

    Boolean existsByMiId(String userId);

    MemberInfoEntity findByMiSeq(Long miSeq);
}
