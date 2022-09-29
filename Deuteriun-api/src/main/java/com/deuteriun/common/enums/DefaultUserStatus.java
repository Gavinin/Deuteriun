package com.deuteriun.common.enums;

/**
 * @ClassName DefaultRolesStatus
 * @Description TODO
 * @Author gavin
 * @Date 12/5/2022 21:25
 * @Version 1.0
 **/
public enum DefaultUserStatus {
    /**
     * System have default users in database,should NOT be deleted!
     */
    ROOT("root"),
    USER("user");

    final String userName;

    public String getUserName() {
        return userName;
    }

    @Override
    public String toString() {
        return getUserName();
    }

    DefaultUserStatus(String userName) {
        this.userName = userName;
    }
}
