package com.deuteriun.common.enums;

public enum ReturnStatus {
    /*
     * 1***: Information
     */
    SERVICES_NORMAL(1000,"Service normal"),
    /*
     * 2***: Success
     * 21** System
     * 22** User
     * 23** Authority
     *
     */
    SUCCESS(2000,"成功"),

    SYSTEM_LOGIN_SUCCESS(2102,"注销成功"),
    SYSTEM_LOGOUT_SUCCESS(2102,"注销成功"),

    USER_CREATE_SUCCESSFUL(2211,"用户创建成功"),

    /*
     * 3***: Redirect
     */

    /*
     * 4***: Error
     * 41** System error
     * 42** User error
     * 43** Authority error
     */
    ERROR(4000, "操作失败"),
    SYSTEM_ERROR(4100,"系统错误"),
    SYSTEM_UNKNOWN_ERROR(4101,"未知错误"),
    SYSTEM_NULL_POINT(4102,"NULL POINT"),
    SYSTEM_IO_EXCEPTION(4103,"IO 错误"),
    SYSTEM_JSON_ERROR(4104,"JSON入参错误"),
    USER_NOT_LOGIN(4200, "用户未登录"),
    USER_PASSWORD_ERROR(4201, "用户密码错误"),
    USER_ACCOUNT_EXPIRED(4202, "用户账号过期"),
    USER_PASSWORD_EXPIRED(4203, "用户密码过期"),
    USER_ACCOUNT_DISABLE(4204, "用户账号不可用"),
    USER_ACCOUNT_LOCKED(4205, "用户账号锁定"),
    USER_ACCOUNT_NOT_EXIST(4206, "用户不存在"),
    USER_CREATE_FAIL(4211,"用户创建失败"),


    AUTHORITY_ERROR(4300,"权限错误"),
    AUTHORITY_UNAUTHORIZED(4301,"权限不足");
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
