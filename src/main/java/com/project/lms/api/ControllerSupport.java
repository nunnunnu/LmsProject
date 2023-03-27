package com.project.lms.api;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.project.lms.error.ErrorCode;
import com.project.lms.error.ErrorResponse;
import com.project.lms.error.NotValidExceptionResponse;
import com.project.lms.error.custom.JoinException;
import com.project.lms.error.custom.NoContentsException;
import com.project.lms.error.custom.NotConnetClassAndTeacher;
import com.project.lms.error.custom.NotFoundClassException;
import com.project.lms.error.custom.NotFoundFeedback;
import com.project.lms.error.custom.NotFoundMemberException;
import com.project.lms.error.custom.NotFoundSubject;
import com.project.lms.error.custom.NotFoundTestException;
import com.project.lms.error.custom.TypeDiscodeException;

@RestControllerAdvice //RestController를 보조해주는 역할
public class ControllerSupport {
    
    @ExceptionHandler(value = JoinException.class) //다른 restController 에서 JoinException이 발생했을때
    public ResponseEntity<Object> handleMethodArgumentNotValid(JoinException ex) {
        return new ResponseEntity<>(  //ResponseEntity를 세팅해준 후 반환함
                NotValidExceptionResponse.builder()
                        .timestamp(LocalDateTime.now()) //현재시간
                        .message("회원가입 실패") //에러 메세지 세팅
                        .code(ErrorCode.JOIN_FAILED) //에러코드 정보 세팅(enum)
                        .status(false) //성공여부 세팅
                        .err(ex.getErr()) //모든 유효성검사 메세지를 반환하기위해 List<String>형태의 메세지 멤버변수를 추가했음
                        .build(), HttpStatus.NOT_ACCEPTABLE); //에러코드는 406이 발생
    }
    @ExceptionHandler(value = TypeDiscodeException.class)//다른 restController 에서 TypeDiscodeException 발생했을때
    public ResponseEntity<ErrorResponse> typeDiscodeException(TypeDiscodeException ex) {
        return new ResponseEntity<>(  //ResponseEntity를 세팅해준 후 반환함
            ErrorResponse.builder()
                        .timestamp(LocalDateTime.now()) //현재시간
                        .message(ex.getMessage()) //에러 메세지 세팅. enum타입에 적어놓은 에러메세지가 출력됨
                        .code(ErrorCode.TYPE_DISCODE)
                        .status(false) //성공여부 세팅
                        .build(), HttpStatus.BAD_REQUEST); //에러코드는 400 발생
    }
    @ExceptionHandler(value = NotFoundClassException.class) //다른 restController 에서 NotFoundClassException 발생했을때
    public ResponseEntity<ErrorResponse> notFoundClassException(NotFoundClassException ex) {
        return new ResponseEntity<>(
            ErrorResponse.builder()
                        .timestamp(LocalDateTime.now()) //현재시간
                        .message(ex.getMessage()) //에러 메세지 세팅. enum타입에 적어놓은 에러메세지가 출력됨
                        .code(ErrorCode.CLASS_NOT_FOUND) //에러 메세지 세팅. enum타입에 적어놓은 에러메세지가 출력됨
                        .status(false) //성공여부 세팅
                        .build(), HttpStatus.BAD_REQUEST); //에러코드는 400 발생
    }
    @ExceptionHandler(value = NotFoundMemberException.class) //다른 restController 에서 NotFoundMemberException 발생했을때
    public ResponseEntity<ErrorResponse> notFoundMemberException(NotFoundMemberException ex) {
        return new ResponseEntity<>(
            ErrorResponse.builder()
                        .timestamp(LocalDateTime.now()) //현재시간
                        .message(ex.getMessage()) //에러 메세지 세팅. enum타입에 적어놓은 에러메세지가 출력됨
                        .code(ErrorCode.MEMBER_NOT_FOUND) //에러 메세지 세팅. enum타입에 적어놓은 에러메세지가 출력됨
                        .status(false) //성공여부 세팅
                        .build(), HttpStatus.BAD_REQUEST); //에러코드는 400 발생
    }
    @ExceptionHandler(value = NotFoundSubject.class) //다른 restController 에서 NotFoundSubject 발생했을때
    public ResponseEntity<ErrorResponse> notFoundSubject(NotFoundSubject ex) {

        return new ResponseEntity<ErrorResponse>(ErrorResponse.builder()
                        .timestamp(LocalDateTime.now()) //현재시간
                        .code(ErrorCode.SUBJECT_NOT_FOUND) //에러 메세지 세팅. enum타입에 적어놓은 에러메세지가 출력됨
                        .message(ex.getMessage()) //에러 메세지 세팅. enum타입에 적어놓은 에러메세지가 출력됨
                        .status(false) //성공여부 세팅
                        .build(), HttpStatus.BAD_REQUEST); //에러코드는 400 발생
    }
    @ExceptionHandler(value = NotFoundTestException.class) //다른 restController 에서 NotFoundSubject 발생했을때
    public ResponseEntity<ErrorResponse> notFoundTest(NotFoundTestException ex) {

        return new ResponseEntity<ErrorResponse>(ErrorResponse.builder()
                        .timestamp(LocalDateTime.now()) //현재시간
                        .code(ErrorCode.TEST_NOT_FOUND) //에러 메세지 세팅. enum타입에 적어놓은 에러메세지가 출력됨
                        .message(ex.getMessage()) //에러 메세지 세팅. enum타입에 적어놓은 에러메세지가 출력됨
                        .status(false) //성공여부 세팅
                        .build(), HttpStatus.BAD_REQUEST); //에러코드는 400 발생
    }
    @ExceptionHandler(value = NotConnetClassAndTeacher.class) //다른 restController 에서 NotConnetClassAndTeacher 발생했을때
    public ResponseEntity<ErrorResponse> notConnect(NotConnetClassAndTeacher ex) {
        return new ResponseEntity<ErrorResponse>(ErrorResponse.builder()
                        .timestamp(LocalDateTime.now()) //현재시간
                        .code(ErrorCode.NOT_CONNECT_CLASS_AND_TEACHER) //에러 메세지 세팅. enum타입에 적어놓은 에러메세지가 출력됨
                        .message(ex.getMessage()) //에러 메세지 세팅. enum타입에 적어놓은 에러메세지가 출력됨
                        .status(false) //성공여부 세팅
                        .build(), HttpStatus.BAD_REQUEST); //에러코드는 400 발생
    }
    @ExceptionHandler(value = NoContentsException.class) //다른 restController 에서 NotConnetClassAndTeacher 발생했을때
    public ResponseEntity<ErrorResponse> notContents(NoContentsException ex) {
        return new ResponseEntity<ErrorResponse>(ErrorResponse.builder()
                        .timestamp(LocalDateTime.now()) //현재시간
                        .code(ErrorCode.NO_CONTENTS) //에러 메세지 세팅. enum타입에 적어놓은 에러메세지가 출력됨
                        .message(ex.getMessage()) //에러 메세지 세팅. enum타입에 적어놓은 에러메세지가 출력됨
                        .status(false) //성공여부 세팅
                        .build(), HttpStatus.BAD_REQUEST); //에러코드는 400 발생
    }
    @ExceptionHandler(value = NotFoundFeedback.class) //다른 restController 에서 NotFoundFeedback 발생했을때
    public ResponseEntity<ErrorResponse> notFeedback(NotFoundFeedback ex) {
        return new ResponseEntity<ErrorResponse>(ErrorResponse.builder()
                        .timestamp(LocalDateTime.now()) //현재시간
                        .code(ErrorCode.FEEDBACK_NOT_FOUND) //에러 메세지 세팅. enum타입에 적어놓은 에러메세지가 출력됨
                        .message(ex.getMessage()) //에러 메세지 세팅. enum타입에 적어놓은 에러메세지가 출력됨
                        .status(false) //성공여부 세팅
                        .build(), HttpStatus.BAD_REQUEST); //에러코드는 400 발생
    }
}
