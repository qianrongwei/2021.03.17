package com.qrw.enums;

/**
 * @author qrw
 * @create 2021-03-22 15:37
 */
public enum NotificationStatusEnum {

    UNREAD(0),READ(1);
    private int status;

    public int getStatus() {
        return status;
    }
    NotificationStatusEnum(int status) {
        this.status = status;
    }
}
