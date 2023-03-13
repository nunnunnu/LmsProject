package com.project.lms.vo.member;

import java.time.LocalDate;

import com.project.lms.entity.member.enumfile.Department;
import com.project.lms.entity.member.enumfile.Position;

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
public class MemberJoinVO {
    //공통
    @NotBlank(message="아이디를 입력해주세요")
    @Pattern(regexp="^[a-zA-Z][0-9a-zA-Z]{6,20}$", message = "아이디는 영문자로 시작하는 영문자 또는 숫자 6~20자")
    private String id;
    @NotBlank(message="비밀번호를 입력해주세요")
    @Pattern(regexp="^(?=.*\\d)(?=.*[~`!@#$%\\^&*()-])(?=.*[a-z]).{8,16}$", message = "비밀번호는 8 ~ 16자 영문, 숫자, 특수문자를 최소 한가지씩 조합")
    private String pwd;
    @NotBlank(message="이름을 입력해주세요.")
    private String name;
    @NotNull(message="생년월일을 입력해주세요.")
    private LocalDate birth;
    @NotBlank(message="이메일을 입력해주세요")
    @Email(message="이메일 형식이 아닙니다.")
    private String email;
    @NotNull(message="입사일을 입력해주세요.")
    private LocalDate regDt;

    //학생
    private Integer grade;
    private String shcool;
    //선생
    private Long subject;
    //직원
    private Position position;
    private Department department;
    //선생/직원
    private String exp;
}