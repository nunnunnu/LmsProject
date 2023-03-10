package com.project.lms.repository.member;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.lms.entity.member.MemberInfoEntity;

public interface MemberInfoRepository extends JpaRepository<MemberInfoEntity, Long>{

    MemberInfoEntity findByMiId(String username);
    
}
