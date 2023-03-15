package com.project.lms.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.lms.entity.ClassTeacherEntity;
import com.project.lms.entity.member.MemberInfoEntity;
import com.project.lms.repository.ClassTeacherRepository;
import com.project.lms.repository.member.MemberInfoRepository;
import com.project.lms.repository.member.TeacherInfoRepository;
import com.project.lms.vo.ScoreStatsByClassResponseVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ScoreStatsService {
    private final MemberInfoRepository memberInfoRepositoryRepo;
    private final ClassTeacherRepository classTeacherRepository;
    private final TeacherInfoRepository teacherInfoRepository;

    public ScoreStatsByClassResponseVO ClassScoreStats(Long classSeq, UserDetails userDetails) {
        MemberInfoEntity member = memberInfoRepositoryRepo.findByMiId(userDetails.getUsername());
        ClassTeacherEntity classInfo = classTeacherRepository.findByTeacher(teacherInfoRepository.findById(member.getMiSeq()).get());
        System.out.println(classInfo);
        return null;
    }
}
