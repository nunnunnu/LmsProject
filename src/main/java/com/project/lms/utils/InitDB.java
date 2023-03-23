package com.project.lms.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.project.lms.entity.ClassStudentEntity;
import com.project.lms.entity.ClassTeacherEntity;
import com.project.lms.entity.GradeInfoEntity;
import com.project.lms.entity.SubjectInfoEntity;
import com.project.lms.entity.TestInfoEntity;
import com.project.lms.entity.member.StudentInfo;
import com.project.lms.entity.member.TeacherInfo;
import com.project.lms.repository.ClassStudentRepository;
import com.project.lms.repository.ClassTeacherRepository;
import com.project.lms.repository.GradeInfoRepository;
import com.project.lms.repository.SubjectInfoRepository;
import com.project.lms.repository.TestInfoRepository;
import com.project.lms.repository.member.StudentInfoRepository;
import com.project.lms.repository.member.TeacherInfoRepository;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component //직접 작성한 Class를 Bean으로 등록하기 위한 어노테이션
@RequiredArgsConstructor
public class InitDB {

    private final InitService initService;

    @PostConstruct //서버 실행시 최초로 실행되는 메소드. 
    public void init(){
        // initService.dbInit1(); //아래 class의 dbInit1메소드 실행. 더미데이터생성을 위해 작성한거라서 더 실행되면안되니까 주석해놨음. 주석풀면안됨
    }
    
    @Component //직접 작성한 Class를 Bean으로 등록하기 위한 어노테이션
    @Transactional 
    @RequiredArgsConstructor
    static class InitService {

        private final StudentInfoRepository sRepo;
        private final SubjectInfoRepository subRepo;
        private final TestInfoRepository testRepo;
        private final ClassTeacherRepository ctRepo;
        private final ClassStudentRepository csRepo;
        private final GradeInfoRepository gRepo;
    

        public void dbInit1() { //DB에 성적을 입력하기 위한 메소드
            List<StudentInfo> students = sRepo.findAll(); //모든 학생 조회(기본 더미데이터이기때문에 사용불가 계정은 고려안함)
            List<SubjectInfoEntity> subjects = subRepo.findAll(); //모든 과목 조회
            List<TestInfoEntity> tests = testRepo.findAll(); //모든 시험 조회
            List<GradeInfoEntity> grades = new ArrayList<>(); //save할 entity를 담은 list
            for(TestInfoEntity t : tests){ //각 시험별로
                for(StudentInfo s : students){ //각 학생마다
                    for(SubjectInfoEntity sub : subjects){ //과목별 성적을 입력
                        ClassStudentEntity cs = csRepo.findByStudent(s); //학생으로 소속 반을 찾아옴.
                        List<ClassTeacherEntity> ct = ctRepo.findByClassInfo(cs.getClassInfo()); //소속 반으로 소속된 선생님을 찾아옴
                        List<TeacherInfo> teachers = ct.stream().map((tea)->tea.getTeacher()).toList(); //선생님 list를 만듦
                        for(TeacherInfo teacher : teachers){ //선생님으로 for문을 돌려서
                            if(teacher != null && teacher.getSubject().getSubSeq() == sub.getSubSeq()){ //입력하려는 과목점수의 담당 선생님을 찾음
                                Integer random = (int)(Math.random()*100)+1; //랜덤으로 입력할 점수
                                GradeInfoEntity grade = new GradeInfoEntity(null, sub, s, teacher, random, t); //찾은 정보를 gradeInfoEntity에 세팅
                                grades.add(grade); //최종적으로 저장할 list에 추가
                            }
                        }
                    }
                }
            }
            gRepo.saveAll(grades); //list에 추가된 성적데이터 모두 DB저장
        }
    }
}