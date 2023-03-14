package com.project.lms.error.custom;

import com.project.lms.error.CustomException;
import com.project.lms.error.ErrorCode;

public class TypeDiscodeException extends CustomException{
    public TypeDiscodeException() {
        super(ErrorCode.TYPE_DISCODE);
    }
}
