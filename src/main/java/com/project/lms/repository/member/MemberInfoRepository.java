package com.project.lms.repository.member;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.project.lms.entity.member.MemberInfoEntity;
import com.project.lms.entity.member.enumfile.Role;

public interface MemberInfoRepository extends JpaRepository<MemberInfoEntity, Long>{
    MemberInfoEntity findByMiIdAndMiPwd (String miId, String miPwd);
    MemberInfoEntity findByMiId(String username);
    MemberInfoEntity findByMiNameAndMiBirthAndMiEmail (String name,LocalDate birth, String email);
    MemberInfoEntity findByMiIdAndMiNameAndMiEmail (String id,String name, String email);
    Boolean existsByMiId(String userId); //DB에 아이디가 있는지 검사. count보다 성능이좋음
    MemberInfoEntity findByMiSeq(Long miSeq);

    Page<MemberInfoEntity> findByMiRoleNotAndMiStatus(Role mi_role, Boolean status, Pageable pageable);

    Page<MemberInfoEntity> findByMiNameContainingAndMiStatus(String name, Boolean status,  Pageable pageable);
}
