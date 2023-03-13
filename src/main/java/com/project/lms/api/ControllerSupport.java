package com.project.lms.api;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.project.lms.error.ErrorCode;
import com.project.lms.error.ErrorResponse;
import com.project.lms.error.NotValidExceptionResponse;
import com.project.lms.error.custom.JoinException;
import com.project.lms.error.custom.NotFoundSubject;

@RestControllerAdvice
public class ControllerSupport {
    
    @ExceptionHandler(value = JoinException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValid(JoinException ex) {

        return new ResponseEntity<>(
                NotValidExceptionResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .message("회원가입 실패")
                        .code(ErrorCode.JOIN_FAILED)
                        .status(false)
                        .err(ex.getErr())
                        .build(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = NotFoundSubject.class)
    public ResponseEntity<ErrorResponse> notFoundSubject(NotFoundSubject ex) {

        return new ResponseEntity<ErrorResponse>(ErrorResponse.builder()
                        .code(ErrorCode.SUBJECT_NOT_FOUND)
                        .message(ex.getMessage())
                        .status(false)
                        .build(), HttpStatus.BAD_REQUEST);
    }
}
