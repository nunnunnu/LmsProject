package com.project.lms.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.project.lms.entity.ClassInfoEntity;
import com.project.lms.entity.ClassStudentEntity;
import com.project.lms.entity.ClassTeacherEntity;
import com.project.lms.entity.member.StudentInfo;
import com.project.lms.entity.member.TeacherInfo;
import com.project.lms.error.custom.NoContentsException;
import com.project.lms.error.custom.NotConnetClassAndTeacher;
import com.project.lms.error.custom.NotFoundClassException;
import com.project.lms.error.custom.NotFoundMemberException;
import com.project.lms.repository.ClassInfoRepository;
import com.project.lms.repository.ClassStudentRepository;
import com.project.lms.repository.ClassTeacherRepository;
import com.project.lms.repository.member.StudentInfoRepository;
import com.project.lms.repository.member.TeacherInfoRepository;
import com.project.lms.vo.member.ClassStudentListVO;
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

    public Page<ClassStudentListVO> classMemberFind(UserDetails userDetails, Pageable page){
        TeacherInfo teacher = tRepo.findByMiId(userDetails.getUsername()); //토큰정보로 회원을 찾음
        
        if(teacher==null){
            throw new NotFoundMemberException(); //회원이 없다면 MemberNotFoundException 발생, ControllerSupport에서 처리됨
        }
        ClassTeacherEntity classTeacher = ctRepo.findByTeacher(teacher); //찾은 선생님으로 연결 테이블 조회
        
        if(classTeacher==null){
            throw new NotConnetClassAndTeacher(); //연결 테이블에 정보가 없다면 담당 반이 없는 선생님임. NotConnetClassAndTeacher발생. ControllerSupport에서 처리됨
        }
        
        Page<ClassStudentEntity> students = csRepo.findByClassInfo(classTeacher.getClassInfo(), page); //연결테이블에서 가져온 반으로 학생-반 연결테이블 조회. 모든 연결 테이블 조회(fetch join)
        
        if(students.getContent().size()==0){
            throw new NoContentsException(); //데이터가 존재하지 않을 경우. NoContentsException. ControllerSupport에서 처리됨
        }

        Page<ClassStudentListVO> result = //가져온 연결 테이블에서 학생 entity를 가져와서 VO로 변환
                students.map(s->new ClassStudentListVO(s.getStudent())); //for문을 사용해서 변환하는 것과 같음. 람다식으로 표현

        return result; //결과 반환
    }
}
