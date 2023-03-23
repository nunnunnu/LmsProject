package com.project.lms.error.custom;

import com.project.lms.error.CustomException;
import com.project.lms.error.ErrorCode;

public class NotFoundEmployeeClass extends CustomException{
    public NotFoundEmployeeClass() {
        super(ErrorCode.EMPLOYEE_CLASS_NOT_FOUND);
    }
}
