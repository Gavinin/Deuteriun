package com.deuteriun.system.controller;

import com.deuteriun.common.enums.ReturnStatus;
import com.deuteriun.common.utils.Result;
import com.deuteriun.common.utils.StringUtils;
import com.deuteriun.system.entity.AutorityManageDTO;
import com.deuteriun.system.entity.SysRole;
import com.deuteriun.system.service.SysRoleService;
import com.deuteriun.system.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Collection;

/**
 * @ClassName AuthorityManageController
 * @Description TODO
 * @Author gavin
 * @Date 9/5/2022 00:59
 * @Version 1.0
 **/
@RestController
@Api(tags = "Authority Manager", value = "Authority manage controller")
@RequestMapping("auth")
public class AuthorityManageController {

    @Resource
    SysRoleService sysRoleService;

    @GetMapping("/list")
    @PreAuthorize(value = "hasAuthority('SYS_USER') AND hasAuthority('AUTH_MANAGE')")
    Result list(@RequestBody AutorityManageDTO autorityManageDTO) {
        Collection<SimpleGrantedAuthority> authorities = SecurityUtils.getAllAuthorities();
        return Result.success(authorities);
    }


    @PostMapping("/add")
    @ApiOperation("String: role,Long: sys_user_id")
    @PreAuthorize(value = "hasAuthority('SYS_USER') AND hasAuthority('AUTH_MANAGE')")
    Result add(@RequestBody SysRole sysRole) {
        if (sysRole.getSysUserId() > 0 && StringUtils.isNotBlank(sysRole.getRole())) {
            if (sysRoleService.add(sysRole)) {
                return Result.success(ReturnStatus.ROLE_CREATE_SUCCESSFUL);
            }
        }
        return Result.error(ReturnStatus.ERROR);
    }

    @DeleteMapping("/delete")
    @ApiOperation("String: role,Long: sys_user_id")
    @PreAuthorize(value = "hasAuthority('SYS_USER') AND hasAuthority('AUTH_MANAGE')")
    Result delete(@RequestBody SysRole sysRole) {
        if (sysRole.getSysUserId() > 0 && StringUtils.isNotBlank(sysRole.getRole())) {
            if (sysRoleService.delete(sysRole)) {
                return Result.success(ReturnStatus.ROLE_DELETE_SUCCESSFUL);
            }
        }
        return Result.error(ReturnStatus.ERROR);
    }

}
