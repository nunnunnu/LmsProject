package com.project.lms;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.project.lms.entity.ClassInfoEntity;
import com.project.lms.entity.ClassStudentEntity;
import com.project.lms.entity.ClassTeacherEntity;
import com.project.lms.entity.SubjectInfoEntity;
import com.project.lms.entity.member.EmployeeInfo;
import com.project.lms.entity.member.StudentInfo;
import com.project.lms.entity.member.TeacherInfo;
import com.project.lms.entity.member.enumfile.Department;
import com.project.lms.entity.member.enumfile.Position;
import com.project.lms.repository.ClassInfoRepository;
import com.project.lms.repository.ClassStudentRepository;
import com.project.lms.repository.ClassTeacherRepository;
import com.project.lms.repository.SubjectInfoRepository;
import com.project.lms.repository.member.EmployeeInfoRepository;
import com.project.lms.repository.member.StudentInfoRepository;
import com.project.lms.repository.member.TeacherInfoRepository;
import com.project.lms.vo.member.MemberJoinVO;

@SpringBootTest
@Transactional
public class ClassTest {
    @Autowired StudentInfoRepository sRepo;
    @Autowired TeacherInfoRepository tRepo;
    @Autowired ClassInfoRepository cRepo;
    @Autowired SubjectInfoRepository subRepo;
    @Autowired EmployeeInfoRepository eRepo;
    @Autowired ClassStudentRepository csRepo;
    @Autowired ClassTeacherRepository ctRepo;
    
    private SubjectInfoEntity subject;
    private TeacherInfo teacher;
    private ClassInfoEntity classInfo;
    private EmployeeInfo employee;
    private StudentInfo student;
    
    
    @BeforeEach //class안의 test가 실행되기전에 실행되는 메소드라는 어노테이션
    public void beforeEach(){
        MemberJoinVO voE = MemberJoinVO.builder()
                        .id("testempl123")
                        .birth(LocalDate.now())
                        .email("testemail@test.com")
                        .name("테스트")
                        .regDt(LocalDate.now())
                        .pwd("asdf123!")
                        .department(Department.교무행정)
                        .position(Position.과장)
                        .exp("8년차")
                        .build();
        employee = new EmployeeInfo(voE);
        eRepo.save(employee);

        classInfo = new ClassInfoEntity(null, "테스트용반", 20, employee);
        cRepo.save(classInfo);
    
        subject = new SubjectInfoEntity(null, "테스트용 과목");
        subRepo.save(subject);

        MemberJoinVO voT = MemberJoinVO.builder()
                        .id("testtea123")
                        .birth(LocalDate.now())
                        .email("testemail@test.com")
                        .name("테스트")
                        .regDt(LocalDate.now())
                        .pwd("asdf123!")
                        .department(Department.교무행정)
                        .position(Position.과장)
                        .exp("8년차")
                        .build();
        teacher = new TeacherInfo(voT, subject);
        tRepo.save(teacher);


        
        MemberJoinVO vo = MemberJoinVO.builder()
                        .id("testid1")
                        .birth(LocalDate.now())
                        .email("testemail@test.com")
                        .grade(1)
                        .shcool("ㅇㅇ고")
                        .name("테스트")
                        .regDt(LocalDate.now())
                        .pwd("asdf123!")
                        .classroom(classInfo.getCiSeq())
                        .build();
        student = new StudentInfo(vo);
        sRepo.save(student);

        ClassStudentEntity studentClass = new ClassStudentEntity(null, classInfo, student); //학생-반 연결테이블 엔티티 생성
        csRepo.save(studentClass);
        ClassTeacherEntity teacherClass = new ClassTeacherEntity(null, classInfo, teacher);
        ctRepo.save(teacherClass);
    }

    @Test
    public void 선생님이소속된반의학생조회(){
        ClassTeacherEntity classTeacher = ctRepo.findByTeacher(teacher);
        ClassInfoEntity classEntity = classTeacher.getClassInfo();

        List<ClassStudentEntity> result = csRepo.findByClassInfo(classEntity);

        assertThat(result.size()).isNotEqualTo(0);
    }
}
