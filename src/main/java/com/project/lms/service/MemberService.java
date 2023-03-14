package com.project.lms.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.project.lms.entity.SubjectInfoEntity;
import com.project.lms.entity.member.EmployeeInfo;
import com.project.lms.entity.member.StudentInfo;
import com.project.lms.entity.member.TeacherInfo;
import com.project.lms.error.custom.NotFoundSubject;
import com.project.lms.repository.SubjectInfoRepository;
import com.project.lms.repository.member.EmployeeInfoRepository;
import com.project.lms.repository.member.MemberInfoRepository;
import com.project.lms.repository.member.StudentInfoRepository;
import com.project.lms.repository.member.TeacherInfoRepository;
import com.project.lms.security.config.WebSecurityConfig;
import com.project.lms.validator.SignUpFormValidator;
import com.project.lms.vo.MapVO;
import com.project.lms.vo.member.MemberJoinVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberInfoRepository mRepo;
    private final StudentInfoRepository sRepo;
    private final SignUpFormValidator signUpFormValidator;
    private final WebSecurityConfig securityConfig;
    private final SubjectInfoRepository subRepo;
    private final TeacherInfoRepository tRepo;
    private final EmployeeInfoRepository eRepo;

    public MapVO joinMember(MemberJoinVO data, String type, BindingResult bindingResult){
        signUpFormValidator.validate(data, bindingResult);
        data.setPwd(securityConfig.passwordEncoder().encode(data.getPwd()));
        if(type.equalsIgnoreCase("student")){
            StudentInfo entity = new StudentInfo(data);
            sRepo.save(entity);
        }else if(type.equalsIgnoreCase("teacher")){
            SubjectInfoEntity sub = subRepo.findById(data.getSubject()).orElseThrow(()->new NotFoundSubject());
            TeacherInfo entity = new TeacherInfo(data, sub);
            tRepo.save(entity);
        }else if(type.equalsIgnoreCase("employee")){
            EmployeeInfo entity = new EmployeeInfo(data);
            eRepo.save(entity);
        }
        return MapVO.builder().message("회원가입 성공").code(HttpStatus.OK).status(true).build();
    }
}
