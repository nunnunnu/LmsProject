package com.project.lms.error.custom;

import com.project.lms.error.CustomException;
import com.project.lms.error.ErrorCode;

public class NotFoundClassException extends CustomException {

    public NotFoundClassException() {
        super(ErrorCode.CLASS_NOT_FOUND);
    }
    
}
