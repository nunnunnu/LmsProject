package com.project.lms.error.custom;

import com.project.lms.error.CustomException;
import com.project.lms.error.ErrorCode;

public class NotFoundTestException extends CustomException {

    public NotFoundTestException() {
        super(ErrorCode.TEST_NOT_FOUND);
    }
    
}
