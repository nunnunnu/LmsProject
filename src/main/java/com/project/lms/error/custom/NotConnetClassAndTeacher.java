package com.project.lms.error.custom;

import com.project.lms.error.CustomException;
import com.project.lms.error.ErrorCode;

public class NotConnetClassAndTeacher extends CustomException{

    public NotConnetClassAndTeacher() {
        super(ErrorCode.NOT_CONNECT_CLASS_AND_TEACHER);
    }
    
}
