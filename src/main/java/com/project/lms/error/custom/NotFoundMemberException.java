package com.project.lms.error.custom;

import com.project.lms.error.CustomException;
import com.project.lms.error.ErrorCode;

public class NotFoundMemberException extends CustomException {

    private static final long serialVersionUID = -2116671122895194101L;

    public NotFoundMemberException() {
        super(ErrorCode.MEMBER_NOT_FOUND);
    }
}
