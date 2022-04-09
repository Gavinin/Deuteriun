package com.deuteriun.system.common.enums;

public enum ReturnStatus {
    /*
     * 1***: Information
     */
    SERVICES_NORMAL(1000,"Service normal"),
    /*
     * 2***: Success
     */
    SUCCESS(2000,"成功"),
    LOGOUT_SUCCESS(2001,"注销成功"),
    /*
     * 3***: Redirect
     */

    /*
     * 4***: Error
     */
    USER_AUTH_DENY(4000, "用户权限不足"),
    USER_PASSWORD_ERROR(4001, "用户密码错误"),
    USER_ACCOUNT_EXPIRED(4002, "用户账号过期"),
    USER_PASSWORD_EXPIRED(4003, "用户密码过期"),
    USER_ACCOUNT_DISABLE(4004, "用户账号不可用"),
    USER_ACCOUNT_LOCKED(4005, "用户账号锁定"),
    USER_ACCOUNT_NOT_EXIST(4006, "用户不存在"),
    USER_NOT_LOGIN(4007, "用户未登录"),
    USER_UNKNOWN_ERROR(4010,"Unknown error");
    /*
     * 5***: Server Error
     */







    private int statusCode;
    private String statusMessage;

    public String getMessageByCode(Integer statusCode) {
        for (ReturnStatus value : ReturnStatus.values()) {
            if (value.getStatusCode() == statusCode) {
                return value.statusMessage;
            }
        }
        return "Unknown error";
    }


    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    ReturnStatus(int statusCode, String statusMessage) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
    }
}
