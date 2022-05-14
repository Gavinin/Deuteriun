package com.deuteriun.common.enums;

/**
 * @ClassName RolesStatus
 * @Description TODO
 * @Author gavin
 * @Date 12/5/2022 21:25
 * @Version 1.0
 **/
public enum RolesStatus {
    SYS_ROOT("SYS_ROOT"),
    SYS_USER("SYS_USER");

    final String RoleName;

    public String getRoleName() {
        return RoleName;
    }

    @Override
    public String toString() {
        return getRoleName();
    }


    RolesStatus(String roleName) {
        RoleName = roleName;
    }
}
