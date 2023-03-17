package com.project.lms.error.custom;

import com.project.lms.error.CustomException;
import com.project.lms.error.ErrorCode;

public class NoContentsException extends CustomException{
    private static final long serialVersionUID = -2116671122895194101L;
    public NoContentsException() {
        super(ErrorCode.NO_CONTENTS);
        //TODO Auto-generated constructor stub
    }
    
}
