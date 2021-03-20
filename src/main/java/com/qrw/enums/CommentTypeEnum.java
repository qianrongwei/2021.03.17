package com.qrw.enums;

/**
 * @author qrw
 * @create 2021-03-20 22:13
 */
public enum CommentTypeEnum {
    QUESTION(1),
    COMMENT(2),
    ;
    private Integer type;

    CommentTypeEnum(Integer type){
        this.type = type;
    }

    public Integer getType(){
        return type;
    }

    public static boolean isExist(Integer type){
        for(CommentTypeEnum commentTypeEnum : CommentTypeEnum.values()){
            if(commentTypeEnum.getType() == type){
                return true;
            }
        }
        return false;
    }
}
