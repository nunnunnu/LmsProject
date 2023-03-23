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
import com.project.lms.entity.TestInfoEntity;
import com.project.lms.entity.member.StudentInfo;
import com.project.lms.entity.member.TeacherInfo;
import com.project.lms.error.custom.NoContentsException;
import com.project.lms.error.custom.NotConnetClassAndTeacher;
import com.project.lms.error.custom.NotFoundClassException;
import com.project.lms.error.custom.NotFoundMemberException;
import com.project.lms.error.custom.NotFoundTestException;
import com.project.lms.repository.ClassInfoRepository;
import com.project.lms.repository.ClassStudentRepository;
import com.project.lms.repository.ClassTeacherRepository;
import com.project.lms.repository.GradeInfoRepository;
import com.project.lms.repository.TestInfoRepository;
import com.project.lms.repository.member.StudentInfoRepository;
import com.project.lms.repository.member.TeacherInfoRepository;
import com.project.lms.vo.grade.StudentClassGradeVO;
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
    private final TestInfoRepository tesRepo;
    private final GradeInfoRepository graRepo;


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
    public List<StudentClassGradeVO> changeClassList(){
		TestInfoEntity test = tesRepo.findTop1ByOrderByTestDateDesc(); //가장 최신 시험 조회
		List<StudentClassGradeVO> list = graRepo.studentClassChange(test); //학생 별 총점, 기존 반 정보 가져옴
        Integer totalStudent = sRepo.countByMiStatus(true); //재학중인 학생의 수를 구함. 총점순으로 정렬되어있어서 순위 별 정렬돼있음
        
        List<ClassInfoEntity> classInfo = cRepo.findAllByOrderByCiRating(); //전체 반 정보를 구함. 등급순으로 정렬.
        Integer percent = (int) Math.ceil((double)totalStudent/classInfo.size()); //각 반별로 들어갈 인원 수. 현재는 반이 4반이라서 25%씩
        for(int i=1;i<=list.size();i++){
            StudentClassGradeVO s = list.get(i-1); //학생 정보를 하나 꺼내서 변수에 저장
            for(int j=1;j<=classInfo.size();j++){ //학생이 어느 구간에 위치하는지 알아보기위해 각 반별로 조회(순위비교). 등급순으로 정렬되어있어서 앞쪽 인덱스일수록 등급이 높은 반임
                System.out.println(percent*j);
                System.out.println(percent*(j-1));
                if(i<=percent*j && i>=percent*(j-1)){ //해당 학생의 순위가 해당 구간과 일치한다면
                    s.changeClassAndStatusSetting(classInfo.get(j-1).getCiName()); //해당 반의 정보를 세팅해줌
                }
            }
        }
		return list; //반환
    }
}
