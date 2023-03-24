package com.project.lms.error.custom;

import com.project.lms.error.CustomException;
import com.project.lms.error.ErrorCode;

public class NotFoundTeacherClass extends CustomException{
    public NotFoundTeacherClass() {
        super(ErrorCode.TEACHER_CLASS_NOT_FOUND);
    }
}
