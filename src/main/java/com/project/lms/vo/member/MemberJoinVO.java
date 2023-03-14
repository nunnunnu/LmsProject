package com.project.lms.vo.member;

import java.time.LocalDate;

import com.project.lms.entity.member.enumfile.Department;
import com.project.lms.entity.member.enumfile.Position;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "회원 가입 VO")
public class MemberJoinVO {
    //공통
    @NotBlank(message="아이디를 입력해주세요")
    @Pattern(regexp="^[a-zA-Z][0-9a-zA-Z]{6,20}$", message = "아이디는 영문자로 시작하는 영문자 또는 숫자 6~20자")
    @Schema(description = "아이디. 영문자로 시작하는 영문자 또는 숫자 6~20자")
    private String id;
    @NotBlank(message="비밀번호를 입력해주세요")
    @Schema(description = "비밀번호. 8 ~ 16자 영문, 숫자, 특수문자를 최소 한가지씩 조합")
    @Pattern(regexp="^(?=.*\\d)(?=.*[~`!@#$%\\^&*()-])(?=.*[a-z]).{8,16}$", message = "비밀번호는 8 ~ 16자 영문, 숫자, 특수문자를 최소 한가지씩 조합")
    private String pwd;
    @NotBlank(message="이름을 입력해주세요.")
    @Schema(description = "이름. 공백이 들어올 수 없음.")
    private String name;
    @NotNull(message="생년월일을 입력해주세요.")
    @Schema(description = "생년월일. 공백이 들어올 수 없음.")
    private LocalDate birth;
    @NotBlank(message="이메일을 입력해주세요")
    @Email(message="이메일 형식이 아닙니다.")
    @Schema(description = "이메일. 공백이 들어올 수 없고 이메일 형식이 아니면 에러가 반환됨.")
    private String email;
    @NotNull(message="입사일을 입력해주세요.")
    @Schema(description = "학생일 경우 학원 등록일, 직원/선생/마스터일경우 입사일")
    private LocalDate regDt;
    
    //학생
    @Schema(description = "학생의 학년. null가능")
    private Integer grade;
    @Schema(description = "학생의 학교. null가능")
    private String shcool;
    @Schema(description = "학생의 학원 반 번호. null불가")
    private Long classroom;
    //선생
    @Schema(description = "선생님의 담당 과목. Long타입")
    private Long subject;
    //직원
    @Schema(description = "직급. 공백 불가")
    private Position position;
    @Schema(description = "부서. 공백 불가")
    private Department department;
    //선생/직원
    @Schema(description = "선생님/직원 전용, 공백가능")
    private String exp;
}