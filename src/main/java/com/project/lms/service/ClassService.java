package com.project.lms.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.project.lms.entity.ClassInfoEntity;
import com.project.lms.entity.ClassStudentEntity;
import com.project.lms.entity.ClassTeacherEntity;
import com.project.lms.entity.member.StudentInfo;
import com.project.lms.error.custom.NotFoundClassException;
import com.project.lms.error.custom.NotFoundMemberException;
import com.project.lms.repository.ClassInfoRepository;
import com.project.lms.repository.ClassStudentRepository;
import com.project.lms.repository.ClassTeacherRepository;
import com.project.lms.repository.member.StudentInfoRepository;
import com.project.lms.repository.member.TeacherInfoRepository;
import com.project.lms.vo.response.ClassResponseVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClassService {
    private final ClassTeacherRepository ctRepo;
    private final ClassStudentRepository csRepo;
    private final ClassInfoRepository cRepo;
    private final TeacherInfoRepository tRepo;
    private final StudentInfoRepository sRepo;


    public ClassResponseVO updateClass(Long stuSeq, Long classSeq) {
        StudentInfo s = sRepo.findById(stuSeq).orElse(null);
        if(s==null){
            throw new NotFoundMemberException();
        }
        ClassInfoEntity clas = cRepo.findById(classSeq).orElse(null);
        if(clas == null){
            throw new NotFoundClassException();
        }
        ClassStudentEntity studentClass = csRepo.findByStudent(s);
        studentClass.changeClass(clas);
        csRepo.save(studentClass);

        return ClassResponseVO.builder().code(HttpStatus.OK).message("반변경이 완료되었습니다.").status(true).build();
    }
}
