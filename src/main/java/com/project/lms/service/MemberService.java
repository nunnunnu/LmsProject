package com.project.lms.service;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;

import com.project.lms.entity.ClassInfoEntity;
import com.project.lms.entity.ClassStudentEntity;
import com.project.lms.entity.ClassTeacherEntity;
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
import com.project.lms.repository.ClassTeacherRepository;
import com.project.lms.repository.SubjectInfoRepository;
import com.project.lms.repository.member.EmployeeInfoRepository;
import com.project.lms.repository.member.MemberInfoRepository;
import com.project.lms.repository.member.StudentInfoRepository;
import com.project.lms.repository.member.TeacherInfoRepository;
import com.project.lms.security.config.WebSecurityConfig;
import com.project.lms.security.provider.JwtTokenProvider;
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
    private final ClassTeacherRepository ctRepo;
    private final RedisService redisService;
    private final JwtTokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authBulider;

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
            if(data.getSubject()==null || data.getClassroom()==null){ //과목 정보 or 반번호 누락 시 종료
                return MapVO.builder().message("과목 번호 누락or 반번호 누락").code(HttpStatus.FAILED_DEPENDENCY).status(false).build(); 
            }
            ClassInfoEntity classEntity = cRepo.findById(data.getClassroom()).orElseThrow(()->new NotFoundClassException());
            //과목 entity찾기. 만약 실패한다면 NotFoundSubject Exception발생. ControllerSupport에서 처리됨
            SubjectInfoEntity sub = subRepo.findById(data.getSubject()).orElseThrow(()->new NotFoundSubject()); 

            TeacherInfo entity = new TeacherInfo(data, sub); //생성자를 통해 entity정보 세팅
            tRepo.save(entity); //DB저장
            ClassTeacherEntity classTeacher = new ClassTeacherEntity(null, classEntity, entity); //생성자를 통해 entity정보 세틍
            ctRepo.save(classTeacher); //DB저장
            
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

    public Map<String, Object> accessToken(String refresh){
        Map<String, Object> map = new LinkedHashMap<>();
        String id = redisService.getValues(refresh); //redis에 저장된 리프레쉬토큰이 있는지 확인
        System.out.println(id);

        if(!StringUtils.hasText(id)){ //반환타입이 없을 경우 redis에 저장되지 않은 토큰이라는 의미
            map.put("message","해당 해원은 로그인 한적 없는 회원입니다.");
            map.put("code",HttpStatus.BAD_REQUEST);
            map.put("status",false);
            return map;
        }
        
        if(tokenProvider.isRefreshTokenExpired(refresh)){ //입력받은 redis토큰이 유효한지 확인. 만료됐다면 return
            redisService.delValues(refresh); //만료된 토큰이면 redis에서 삭제
            map.put("message","만료된 토큰");
            map.put("code",HttpStatus.BAD_REQUEST);
            map.put("status",false);
            return map;
        }
        MemberInfoEntity member = mRepo.findByMiId(id); //refresh토큰으로 redis에서 꺼내온 아이디로 memberentity 조회

        UsernamePasswordAuthenticationToken authenticationToken =
        new UsernamePasswordAuthenticationToken(member.getMiId(), member.getMiPwd()); //토큰 재발급
        
        Authentication authentication =
        authBulider.getObject().authenticate(authenticationToken); //토큰 검사
        
        String accessToken = tokenProvider.generateToken(authentication).getAccessToken(); //엑세스 토큰 변수에 담음
        
        map.put("status", true);
        map.put("message", "재발급 완료");
        map.put("code", HttpStatus.OK);
        map.put("token", accessToken);
        return map;
    }
}
