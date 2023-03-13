package com.project.lms.error.custom;

import com.project.lms.error.CustomException;
import com.project.lms.error.ErrorCode;

public class NotFoundSubject extends CustomException {

    public NotFoundSubject() {
        super(ErrorCode.SUBJECT_NOT_FOUND);
    }
}
