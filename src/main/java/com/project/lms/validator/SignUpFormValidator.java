package com.project.lms.validator;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.project.lms.error.custom.JoinException;
import com.project.lms.repository.member.MemberInfoRepository;
import com.project.lms.vo.member.MemberJoinVO;

import lombok.RequiredArgsConstructor;

//회원가입 유효성 검사
@Component
@RequiredArgsConstructor
public class SignUpFormValidator implements Validator { 
    private final MemberInfoRepository mRepo;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(MemberJoinVO.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MemberJoinVO join = (MemberJoinVO) target;
        if (mRepo.existsByMiId(join.getId())) { //이미 가입된 아이디인지 확인
            errors.rejectValue("id", "duplicate.id", "이미 사용중인 아이디입니다."); //사용중인 아이디라면 에러 추가
        }
        if (errors.hasErrors()) { //에러가 하나라도 있으면 
            List<String> errorMessage = new ArrayList<>(); //메세지를 담을 list를 생성
            errors.getFieldErrors().forEach(error -> {
                errorMessage.add(error.getDefaultMessage());
            }); //에러에서 for문을 돌면서 위에서 생성한 list에 에러 메세지를 담음
            throw new JoinException(errorMessage); //JoinException을 발생시키면서 생성한 에러메세지 리스트를 생성자에 넣어서 세팅해줌.
            //처리는 controllerSupport에서 처리됨
        }
    }
}
