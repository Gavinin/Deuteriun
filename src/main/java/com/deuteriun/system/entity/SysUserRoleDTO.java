package com.deuteriun.system.entity;

import lombok.Data;

import java.util.Date;

@Data
public class SysUserRoleDTO {

    /*
    ID from sys_user
     */
    private Long id;

    /*
    Role Code from sys_user_code
     */
    private String roleCode;

    /*
        Role Name from sys_user_code
         */
    private String roleName;

    /*
        ID from sys_user
         */
    private Long sysUserId;

    /*
        ID from sys_user
         */
    private Long createRoleUserId;

    /*
        ID from sys_user
         */
    private Date createDate;
}
