package com.project.lms.repository.member;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.lms.entity.member.StudentInfo;

public interface StudentInfoRepository extends JpaRepository<StudentInfo ,Long> {
    StudentInfo findByMiId(String id);

    Integer countByMiStatus(Boolean status);

    List<StudentInfo> findByMiSeqIn(long[] seqs);
    
}
