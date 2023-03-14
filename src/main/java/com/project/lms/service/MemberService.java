package com.project.lms.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.project.lms.entity.ClassInfoEntity;
import com.project.lms.entity.ClassStudentEntity;
import com.project.lms.entity.SubjectInfoEntity;
import com.project.lms.entity.member.EmployeeInfo;
import com.project.lms.entity.member.MemberInfoEntity;
import com.project.lms.entity.member.StudentInfo;
import com.project.lms.entity.member.TeacherInfo;
import com.project.lms.entity.member.enumfile.Role;
import com.project.lms.error.custom.NotFoundClassException;
import com.project.lms.error.custom.NotFoundSubject;
import com.project.lms.error.custom.TypeDiscodeException;
import com.project.lms.repository.ClassInfoRepository;
import com.project.lms.repository.ClassStudentRepository;
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
    private final ClassInfoRepository cRepo;
    private final ClassStudentRepository csRepo;

    public MapVO joinMember(MemberJoinVO data, String type, BindingResult bindingResult){
        signUpFormValidator.validate(data, bindingResult); //MemberJoinVO 설정해놓은 유효성 검사로 인해 오류가 있는지 검사.
        //오류가 있다면 바로 JoinException발생, 아래 로직은 실행되지않고 ControllerSupport에서 처리됨

        data.setPwd(securityConfig.passwordEncoder().encode(data.getPwd())); //입력받은 비밀번호 암호화

        if(type.equalsIgnoreCase("student")){ //학생 회원가입
            if(data.getClassroom()==null){
                return MapVO.builder().message("반 번호 누락").code(HttpStatus.FAILED_DEPENDENCY).status(false).build(); 
            }
            ClassInfoEntity classEntity = cRepo.findById(data.getClassroom()).orElseThrow(()->new NotFoundClassException());
            StudentInfo entity = new StudentInfo(data); //생성자를 통해 entity 정보 세팅
            sRepo.save(entity); //DB저장
            ClassStudentEntity studentClass = new ClassStudentEntity(null, classEntity, entity); //학생-반 연결테이블 엔티티 생성
            csRepo.save(studentClass); //DB저장
        }else if(type.equalsIgnoreCase("teacher")){ //선생님 회원가입
            if(data.getSubject()!=null){ //과목 정보 누락 시 종료
                return MapVO.builder().message("과목 번호 누락").code(HttpStatus.FAILED_DEPENDENCY).status(false).build(); 
            }
            //과목 entity찾기. 만약 실패한다면 NotFoundSubject Exception발생. ControllerSupport에서 처리됨
            SubjectInfoEntity sub = subRepo.findById(data.getSubject()).orElseThrow(()->new NotFoundSubject()); 

            TeacherInfo entity = new TeacherInfo(data, sub); //생성자를 통해 entity정보 세팅
            tRepo.save(entity); //DB저장
        }else if(type.equalsIgnoreCase("employee")){ //직원 회원가입
            if(data.getDepartment()==null || data.getPosition()==null){ //필수 정보 누락인지 확인. 누락됐다면 종료
                return MapVO.builder().message("부서나 직급 정보가 누락되었습니다.(오타 확인부탁드립니다.)").code(HttpStatus.FAILED_DEPENDENCY).status(false).build();
            }
            EmployeeInfo entity = new EmployeeInfo(data); //생성자를 통해 entity정보 세팅
            eRepo.save(entity); //DB저장
        }else if(type.equalsIgnoreCase("master")){ //마스터 회원가입
            MemberInfoEntity entity = new MemberInfoEntity(data, Role.MASTER); //생성자를 통해 entity정보 세팅
            mRepo.save(entity); //DB저장
        }else{
            throw new TypeDiscodeException(); //type이 잘못들어왔다면 TypeDiscodeException 발생. ControllerSupport에서 처리됨
        }
        return MapVO.builder().message("회원가입 성공").code(HttpStatus.OK).status(true).build(); //성공 메세지
    }
}
