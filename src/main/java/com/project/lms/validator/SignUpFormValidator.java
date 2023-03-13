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
        if (mRepo.existsByMiId(join.getId())) {
            errors.rejectValue("id", "duplicate.id", "이미 사용중인 아이디입니다.");
        }
        if (errors.hasErrors()) {
            List<String> errorMessage = new ArrayList<>();
            errors.getFieldErrors().forEach(error -> {
                errorMessage.add(error.getDefaultMessage());
            });
            throw new JoinException(errorMessage);
        }
    }
}
