package com.deuteriun.common.enums;

/**
 * @ClassName DefaultRolesStatus
 * @Description TODO
 * @Author gavin
 * @Date 12/5/2022 21:25
 * @Version 1.0
 **/
public enum DefaultRolesStatus {
    /**
     * System have default user roles ,should NOT be deleted!
     */
    SYS_ROOT("SYS_ROOT"),
    SYS_USER("SYS_USER"),
    ANYONE("ROLE_ANONYMOUS");

    final String RoleCode;

    public String getRoleCode() {
        return RoleCode;
    }

    @Override
    public String toString() {
        return getRoleCode();
    }

    DefaultRolesStatus(String roleCode) {
        RoleCode = roleCode;
    }
}
