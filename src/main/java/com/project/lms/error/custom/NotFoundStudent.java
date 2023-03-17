package com.project.lms.error.custom;

import com.project.lms.error.CustomException;
import com.project.lms.error.ErrorCode;

public class NotFoundStudent extends CustomException{
    public NotFoundStudent() {
        super(ErrorCode.STUDENT_NOT_FOUND);
    }
}