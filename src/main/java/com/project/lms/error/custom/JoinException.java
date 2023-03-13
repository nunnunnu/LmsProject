package com.project.lms.error.custom;

import java.util.ArrayList;
import java.util.List;

import com.project.lms.error.CustomException;
import com.project.lms.error.ErrorCode;

import lombok.Getter;
@Getter
public class JoinException extends CustomException{
    private List<String> err = new ArrayList<>();
    
    public JoinException(List<String> errs) {
        super(ErrorCode.JOIN_FAILED);
        err.addAll(errs);
    }
}
