// package com.project.lms.utils;

// import java.util.ArrayList;
// import java.util.List;

// import org.springframework.stereotype.Component;

// import com.project.lms.entity.ClassStudentEntity;
// import com.project.lms.entity.ClassTeacherEntity;
// import com.project.lms.entity.GradeInfoEntity;
// import com.project.lms.entity.SubjectInfoEntity;
// import com.project.lms.entity.TestInfoEntity;
// import com.project.lms.entity.member.StudentInfo;
// import com.project.lms.entity.member.TeacherInfo;
// import com.project.lms.repository.ClassStudentRepository;
// import com.project.lms.repository.ClassTeacherRepository;
// import com.project.lms.repository.GradeInfoRepository;
// import com.project.lms.repository.SubjectInfoRepository;
// import com.project.lms.repository.TestInfoRepository;
// import com.project.lms.repository.member.StudentInfoRepository;
// import com.project.lms.repository.member.TeacherInfoRepository;

// import jakarta.annotation.PostConstruct;
// import jakarta.persistence.EntityManager;
// import jakarta.transaction.Transactional;
// import lombok.RequiredArgsConstructor;

// @Component 
// @RequiredArgsConstructor
// public class InitDB {

//     private final InitService initService;

//     @PostConstruct
//     public void init(){
//         initService.dbInit1();
//     }
    

//     @Component
//     @Transactional
//     @RequiredArgsConstructor
//     static class InitService {

//         private final EntityManager em;
//         private final StudentInfoRepository sRepo;
//         private final TeacherInfoRepository tRepo;
//         private final SubjectInfoRepository subRepo;
//         private final TestInfoRepository testRepo;
//         private final ClassTeacherRepository ctRepo;
//         private final ClassStudentRepository csRepo;
//         private final GradeInfoRepository gRepo;
    

//         public void dbInit1() {
//             List<StudentInfo> students = sRepo.findAll();
//             List<SubjectInfoEntity> subjects = subRepo.findAll();
//             List<TestInfoEntity> tests = testRepo.findAll();
//             List<GradeInfoEntity> grades = new ArrayList<>();
//             for(TestInfoEntity t : tests){
//                 System.out.println(students.size());
//                 for(StudentInfo s : students){
//                     for(SubjectInfoEntity sub : subjects){
//                         System.out.println("aaaa");
//                         ClassStudentEntity cs = csRepo.findByStudent(s);
//                         List<ClassTeacherEntity> ct = ctRepo.findByClassInfo(cs.getClassInfo());
//                         System.out.println("vvvvv");
//                         List<TeacherInfo> teachers = ct.stream().map((tea)->tea.getTeacher()).toList();
//                         for(TeacherInfo teacher : teachers){
//                             if(teacher != null && teacher.getSubject().getSubSeq() == sub.getSubSeq()){
//                                 Integer random = (int)(Math.random()*100)+1;
//                                 GradeInfoEntity grade = new GradeInfoEntity(null, sub, s, teacher, random, t);
//                                 grades.add(grade);
//                             }
//                         }
//                     }
//                 }
//             }
//             // em.persist(grades);
//             gRepo.saveAll(grades);
//         }
//     }
// }