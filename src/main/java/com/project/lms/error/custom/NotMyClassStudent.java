package com.project.lms.error.custom;

import com.project.lms.error.CustomException;
import com.project.lms.error.ErrorCode;

public class NotMyClassStudent extends CustomException {
    public NotMyClassStudent() {
        super(ErrorCode.STUDENT_NOT_MYCLASS);
    }
    
}
