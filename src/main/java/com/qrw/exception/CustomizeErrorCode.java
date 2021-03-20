package com.qrw.exception;

/**
 * @author qrw
 * @create 2021-03-20 16:14
 */
public enum CustomizeErrorCode implements ICustomizeErrorCode {

    QUESTION_NOT_FOUND(2001,"你找的问题不在了，要不要换个试试！"),
    TARGET_PARAM_NOT_FOUND(2002,"未选中任何问题和评论进行回复"),
    N0_LOGIN(2003,"当前操作需要登录，请登录后操作"),
    SYS_ERROR(2004,"服务宕机了，要不然你稍后再试一下"),
    TYPE_PARAM_WRONG(2005,"评论类型错误或者不存在"),
    COMMENT_NOT_FOUND(2006,"回复的评论不存在了，换一个试试"),
    ;

    private String message;
    private Integer code;

    CustomizeErrorCode(Integer code,String message){
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode(){
        return code;
    }

    @Override
    public String getMessage(){
        return message;
    }


}
