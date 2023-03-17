package com.project.lms.error.custom;

import com.project.lms.error.CustomException;
import com.project.lms.error.ErrorCode;

public class NotFoundTest extends CustomException{
    public NotFoundTest() {
        super(ErrorCode.TEST_NOT_FOUND);
    }
}