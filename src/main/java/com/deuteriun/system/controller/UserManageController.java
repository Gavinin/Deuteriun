package com.deuteriun.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deuteriun.common.enums.ReturnStatus;
import com.deuteriun.common.utils.Result;
import com.deuteriun.system.entity.SysUser;
import com.deuteriun.system.entity.UserManageDTO;
import com.deuteriun.system.service.SysUserService;
import com.deuteriun.system.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@Api(tags = "User", value = "User manage controller")
@RequestMapping("user")
public class UserManageController {

    @Autowired
    UserService userService;

    @ApiOperation("List users Input 3 parameter specific user name with String[] , page number and page limit")
    @GetMapping("/list")
    @PreAuthorize(value = "hasAuthority('SYS_USER')")
    Result list(@RequestBody UserManageDTO userManageDTO) {
        Page<SysUser> page = new Page<>(userManageDTO.getPage(), userManageDTO.getLimit());
        if (userManageDTO.getUsers() != null) {
            List<String> users = Arrays.asList(userManageDTO.getUsers());
            List<SysUser> allUser = userService.findAllUser(users, page);
            return Result.success(allUser);
        } else {
            List<SysUser> allUser = userService.findAllUser(page);
            return Result.success(allUser);
        }

    }

    @ApiOperation("Create user,")
    @PostMapping("/create")
    @PreAuthorize(value = "hasAuthority('SYS_USER') AND hasAuthority('USER_MANAGE')")
    Result create(@RequestBody UserManageDTO userManageDTO) {
        SysUser user = new SysUser().setUserName(userManageDTO.getUsername())
                .setUserNickName(userManageDTO.getUser_nickname())
                .setPassword(userManageDTO.getPassword());
        if (userService.add(user)) {
            return Result.success(ReturnStatus.USER_CREATE_SUCCESSFUL);
        }
        return Result.error(ReturnStatus.USER_CREATE_FAIL);

    }


}
