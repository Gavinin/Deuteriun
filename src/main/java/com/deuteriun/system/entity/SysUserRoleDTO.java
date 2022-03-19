package com.deuteriun.system.entity;

import lombok.Data;

import java.util.Date;

@Data
public class SysUserRoleDTO {

    private Long id;


    private String roleCode;


    private String roleName;


    private Long sysUserId;


    private Long createRoleUserId;


    private Date createDate;
}
