package com.ytzl.gotrip.utils.exception;

public class GotripException extends Exception{
    //错误码
    private String errorCode;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public GotripException(String message,String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
