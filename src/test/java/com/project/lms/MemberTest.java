package com.project.lms;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.project.lms.entity.ClassInfoEntity;
import com.project.lms.entity.SubjectInfoEntity;
import com.project.lms.entity.member.EmployeeInfo;
import com.project.lms.entity.member.MemberInfoEntity;
import com.project.lms.entity.member.StudentInfo;
import com.project.lms.entity.member.TeacherInfo;
import com.project.lms.entity.member.enumfile.Department;
import com.project.lms.entity.member.enumfile.Position;
import com.project.lms.entity.member.enumfile.Role;
import com.project.lms.repository.ClassInfoRepository;
import com.project.lms.repository.SubjectInfoRepository;
import com.project.lms.repository.member.EmployeeInfoRepository;
import com.project.lms.repository.member.MemberInfoRepository;
import com.project.lms.repository.member.StudentInfoRepository;
import com.project.lms.repository.member.TeacherInfoRepository;
import com.project.lms.vo.member.MemberJoinVO;

import lombok.RequiredArgsConstructor;

@SpringBootTest
@RequiredArgsConstructor
@Transactional
public class MemberTest {
    
	@Autowired PasswordEncoder passwordEncoder;
    @Autowired MemberInfoRepository mRepo;
    @Autowired StudentInfoRepository sRepo;
    @Autowired EmployeeInfoRepository eRepo;
    @Autowired TeacherInfoRepository tRepo;
    @Autowired ClassInfoRepository cRepo;
    @Autowired SubjectInfoRepository subRepo;

    private ClassInfoEntity classInfo;
    private EmployeeInfo employee;
    private SubjectInfoEntity subject;

    @BeforeEach //class안의 test가 실행되기전에 실행되는 메소드라는 어노테이션
    public void beforeEach(){
        MemberJoinVO vo = MemberJoinVO.builder()
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
        employee = new EmployeeInfo(vo);
        eRepo.save(employee);
        
        classInfo = new ClassInfoEntity(null, "테스트용반", 20, employee, 0);
        cRepo.save(classInfo);

        subject = new SubjectInfoEntity(null, "테스트용 과목");
        subRepo.save(subject);
    }

    @Test
    public void 학생가입(){
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
        StudentInfo student = new StudentInfo(vo);
        sRepo.save(student);

        MemberInfoEntity member = mRepo.findById(student.getMiSeq()).orElse(null);
        
        assertThat(member).isNotNull();
        assertThat(member.getMiSeq()).isEqualTo(student.getMiSeq());
    }
    @Test
    public void 선생가입(){
        MemberJoinVO vo = MemberJoinVO.builder()
                        .id("testid2")
                        .birth(LocalDate.now())
                        .email("testemail@test.com")
                        .name("테스트")
                        .regDt(LocalDate.now())
                        .pwd("asdf123!")
                        .classroom(classInfo.getCiSeq())
                        .exp("5년차")
                        .build();
        TeacherInfo teacher = new TeacherInfo(vo, subject);
        tRepo.save(teacher);

        MemberInfoEntity member = mRepo.findById(teacher.getMiSeq()).orElse(null);
        
        assertThat(member).isNotNull();
        assertThat(member.getMiSeq()).isEqualTo(teacher.getMiSeq());
    }
    @Test
    public void 직원가입(){
        MemberJoinVO vo = MemberJoinVO.builder()
                        .id("testid3")
                        .birth(LocalDate.now())
                        .email("testemail@test.com")
                        .name("테스트")
                        .regDt(LocalDate.now())
                        .pwd("asdf123!")
                        .exp("5년차")
                        .department(Department.교무행정)
                        .position(Position.과장)
                        .build();
        EmployeeInfo employeeTest = new EmployeeInfo(vo);
        eRepo.save(employeeTest);

        MemberInfoEntity member = mRepo.findById(employeeTest.getMiSeq()).orElse(null);
        
        assertThat(member).isNotNull();
        assertThat(member.getMiSeq()).isEqualTo(employeeTest.getMiSeq());
    }
    @Test
    public void 마스터가입(){
        MemberJoinVO vo = MemberJoinVO.builder()
                        .id("testid4")
                        .birth(LocalDate.now())
                        .email("testemail@test.com")
                        .name("테스트")
                        .regDt(LocalDate.now())
                        .pwd("asdf123!")
                        .build();
        MemberInfoEntity member = new MemberInfoEntity(vo, Role.MASTER);
        mRepo.save(member);

        MemberInfoEntity findMember = mRepo.findById(member.getMiSeq()).orElse(null);
        
        assertThat(findMember).isNotNull();
        assertThat(findMember.getMiSeq()).isEqualTo(member.getMiSeq());
    }
    @Test
    public void 중복아이디검사_성공_가입된아이디없음(){
        String id = "test5";

        if(mRepo.existsByMiId(id)){
            fail();
        }
    }
    @Test
    public void 중복아이디검사_실패_가입된아이디있음(){
        String id = "testempl123";

        if(!mRepo.existsByMiId(id)){
            fail();
        }
    }

    	@Test
	void testLogin() {
		String id = "hyuk1";
		String pwd  = "asdf123!";
		
		MemberInfoEntity loginUser = mRepo.findByMiId(id);

		Boolean login = null;
		if(loginUser == null ||!passwordEncoder.matches(pwd, loginUser.getMiPwd())) {
			login = false;
		}
		else{
			login = true;	
		}

			Assertions.assertEquals(login, true, "login은 false가 아닙니다.");
			
		}
		@Test
		void findid() {
			String name = "차경준";
			LocalDate birth = LocalDate.of(1995, 1, 1);
			String email = "say052@naver.com";

			MemberInfoEntity member = mRepo.findByMiNameAndMiBirthAndMiEmail(name, birth, email);

			Assertions.assertNotEquals(member, null);
	
		}
        @Test
		void 오류아이디() {
			String name = "차경준";
			LocalDate birth = LocalDate.of(1995, 1, 2);
			String email = "say052@naver.com";

			MemberInfoEntity member = mRepo.findByMiNameAndMiBirthAndMiEmail(name, birth, email);

			Assertions.assertEquals(member, null);
	
		}
		@Test
		void findpwd() {
			String id = "user012";
			String name = "차경준";
			String email = "say052@naver.com";

			MemberInfoEntity member = mRepo.findByMiIdAndMiNameAndMiEmail(id, name, email);

			Assertions.assertNotEquals(member, null);
	
		}
}
