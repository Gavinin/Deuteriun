package com.deuteriun.system.controller;

import com.deuteriun.common.enums.ReturnStatus;
import com.deuteriun.common.utils.Result;
import com.deuteriun.common.utils.StringUtils;
import com.deuteriun.system.entity.SysFilterRole;
import com.deuteriun.system.entity.SysRole;
import com.deuteriun.system.entity.SysUserRole;
import com.deuteriun.system.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName AuthorityManageController
 * @Description Authority is a List ,
 * the authority information is store in database with 1 to 1
 * Example: A user hava 2 authority ,which have TWO information was stored in database
 * @Author gavin
 * @Date 9/5/2022 00:59
 * @Version 1.0
 **/
@RestController
@Api(tags = "Authority Manager", value = "Authority manage controller")
@RequestMapping("apis/auth")
public class AuthorityManageController {

    @Resource
    SysUserRoleService sysUserRoleService;

    @Resource
    SysRoleService sysRoleService;

    @Resource
    SysFilterRoleService sysFilterRoleService;


    @PostMapping("/ur/add")
    @ApiOperation("Sys_user_role.  String: role,Long: sys_user_id")
    Result userRoleAdd(@RequestBody SysUserRole sysUserRole) {
        if (sysUserRole.getSysUserId() > 0 && StringUtils.isNotBlank(sysUserRole.getRoleCode())) {
            if (sysUserRoleService.add(sysUserRole)) {
                return Result.success(ReturnStatus.ROLE_CREATE_SUCCESSFUL);
            }
        }
        return Result.error(ReturnStatus.ERROR);
    }

    @DeleteMapping("/ur/delete")
    @ApiOperation("Sys_user_role.  String: role,Long: sys_user_id")
    Result userRoleDelete(@RequestBody SysUserRole sysUserRole) {
        if (sysUserRole.getId() != null) {
            if (sysUserRoleService.deleteById(sysUserRole)) {
                return Result.success(ReturnStatus.ROLE_DELETE_SUCCESSFUL);
            }
        }
        return Result.error(ReturnStatus.ERROR);
    }


    @GetMapping("/role/list")
    @ApiOperation("Sys_role.  List all roles")
    Result roleList(@RequestBody SysRole sysRole) {
        List<SysRole> sysRoles = sysRoleService.list();
        return Result.success(sysRoles);
    }


    @PostMapping("/role/add")
    @ApiOperation("Sys_user_role.  String: role,Long: sys_user_id")
    Result roleAdd(@RequestBody SysRole sysRole) {
        if (StringUtils.isNotBlank(sysRole.getRoleCode()) &&
                StringUtils.isNotBlank(sysRole.getRoleName()) &&
                sysRole.getCreateUser() != null) {
            if (sysRoleService.add(sysRole)) {
                return Result.success(ReturnStatus.ROLE_CREATE_SUCCESSFUL);
            }
        }
        return Result.error(ReturnStatus.ERROR);
    }

    @DeleteMapping("/role/delete")
    @ApiOperation("Sys_user_role.  String: role,Long: sys_user_id")
    Result roleDelete(@RequestBody SysRole sysRole) {
        Integer sysRoleId = sysRole.getId();
        if (sysRoleId != null) {
            if (sysRoleService.delete(sysRole)) {
                return Result.success(ReturnStatus.ROLE_DELETE_SUCCESSFUL);
            }
        }
        return Result.error(ReturnStatus.ERROR);
    }


    @GetMapping("/ar/list")
    @ApiOperation("Sys_role.  List all roles")
    Result filterRoleList(@RequestBody SysFilterRole sysFilterRole) {
        List<SysFilterRole> sysRoles = sysFilterRoleService.mixList(sysFilterRole);
        return Result.success(sysRoles);
    }


    @PostMapping("/ar/add")
    @ApiOperation("Sys_user_role.  String: role,Long: sys_user_id")
    Result filterRoleAdd(@RequestBody SysFilterRole sysFilterRole) {
        if (sysFilterRole.getSysRoleId() != null && StringUtils.isNotBlank(sysFilterRole.getFilter())) {
            SysFilterRole role = new SysFilterRole()
                    .setSysRoleId(sysFilterRole.getSysRoleId())
                    .setFilter(sysFilterRole.getFilter());
            if (sysFilterRoleService.add(role)) {
                return Result.success(ReturnStatus.FILTER_CREATE_SUCCESSFUL);
            }
        }
        return Result.error(ReturnStatus.ERROR);
    }

    @DeleteMapping("/ar/delete")
    @ApiOperation("Sys_user_role.  String: role,Long: sys_user_id")
    Result filterRoleDelete(@RequestBody SysFilterRole sysFilterRole) {
        if (sysFilterRole.getId() != null) {
            if (sysFilterRoleService.deleteById(sysFilterRole)) {
                return Result.success(ReturnStatus.FILTER_DELETE_SUCCESSFUL);
            }
        }
        return Result.error(ReturnStatus.ERROR);
    }


}
