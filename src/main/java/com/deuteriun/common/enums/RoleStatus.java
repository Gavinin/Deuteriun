package com.deuteriun.common.enums;

/**
 * @ClassName RoleStatus
 * @Description TODO
 * @Author gavin
 * @Date 9/5/2022 02:49
 * @Version 1.0
 **/
public enum RoleStatus {
    SYS_USER("SYS_USER", "System user");


    String roleCode;
    String roleAnnotation;

    public String getRoleAnnotateByRoleCode(String roleCode) {
        for (RoleStatus value : RoleStatus.values()) {
            if (value.getRoleCode().equals(roleCode)) {
                return value.roleAnnotation;
            }
        }
        return "Unknown error";
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleAnnotation() {
        return roleAnnotation;
    }

    public void setRoleAnnotation(String roleAnnotation) {
        this.roleAnnotation = roleAnnotation;
    }

    RoleStatus(String roleCode, String roleAnnotation) {
        this.roleCode = roleCode;
        this.roleAnnotation = roleAnnotation;
    }
}
