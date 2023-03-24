package com.project.lms.error.custom;

import com.project.lms.error.CustomException;
import com.project.lms.error.ErrorCode;

public class NotFoundStudentClass extends CustomException {
    public NotFoundStudentClass() {
        super(ErrorCode.STUDENT_CLASS_NOT_FOUND);
    }
}
