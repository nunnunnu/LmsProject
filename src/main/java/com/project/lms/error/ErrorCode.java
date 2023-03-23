package com.project.lms.error;

public enum ErrorCode {

    MEMBER_NOT_FOUND(400, "M001", "회원정보를 찾을 수 없음.(토큰에러)"),
    NOT_CONNECT_CLASS_AND_TEACHER(400, "M002", "회원정보를 찾을 수 없음.(토큰에러)"),
    NO_CONTENTS(400, "A001", "조회할 데이터가 없습니다."),
    SUBJECT_NOT_FOUND(400, "S001", "연결된 반이 없는 선생님계정입니다."),
    TYPE_DISCODE(400, "T001", "타입을 잘못 입력하셨습니다."),
    CLASS_NOT_FOUND(400, "C001", "반정보를 찾을 수 없습니다.(반 번호 오류)"),
    TEST_NOT_FOUND(400, "TEST001", "시험정보를 찾을 수 없습니다.(시험 번호 오류)"),
    JOIN_FAILED(400, "M003", "회원가입오류"),
    STUDENT_NOT_FOUND(400,"ST001","학생을 찾을 수 없음"),
    FEEDBACK_NOT_FOUND(400,"FN001","게시글이 존재하지 않습니다"),
    EMPLOYEE_CLASS_NOT_FOUND(400, "E001", "직원의 담당 반이 없습니다."),
    STUDENT_CLASS_NOT_FOUND(400, "SC001", "학생의 반 정보가 없습니다."),
    STUDENT_NOT_MYCLASS(400, "SN001", "담당한 반의 학생이 아닙니다. (해당 학생을 조회할 권한이 없음)"),
    TEACHER_CLASS_NOT_FOUND(400, "TC001", "선생의 담당 반이 없습니다.");

    private final String code;
    private final String message;
    private final int status;

    public String getCode(){
        return code;
    }

    public String getMessage(){
        return message;
    }

    public int getStatus(){
        return status;
    }

    ErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }
}
