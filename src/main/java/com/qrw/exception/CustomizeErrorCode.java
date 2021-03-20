package com.qrw.exception;

/**
 * @author qrw
 * @create 2021-03-20 16:14
 */
public enum CustomizeErrorCode implements ICustomizeErrorCode {

    QUESTION_NOT_FOUND("你找的问题不在了，要不要换个试试！");

    private String message;

    CustomizeErrorCode(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }


}
