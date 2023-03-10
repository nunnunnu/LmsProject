package com.project.lms.error;

public enum ErrorCode {

    MEMBER_NOT_FOUND(400, "M001", "회원정보를 찾을 수 없음.(토큰에러)");

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
