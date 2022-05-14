package com.deuteriun.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deuteriun.common.enums.ReturnStatus;
import com.deuteriun.common.utils.Result;
import com.deuteriun.common.utils.StringUtils;
import com.deuteriun.system.entity.SysUser;
import com.deuteriun.system.entity.UserManageDTO;
import com.deuteriun.system.exception.UserException;
import com.deuteriun.system.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@Api(tags = "User", value = "User manage controller")
@RequestMapping("user")
public class UserManageController {

    final
    UserService userService;

    public UserManageController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation("List users Input 3 parameter specific user name with String[] , page number and page limit")
    @GetMapping("/list")
    @PreAuthorize(value = "hasAuthority('SYS_USER')")
    Result listUsers(@RequestBody UserManageDTO userManageDTO) {
        Page<SysUser> page = new Page<>(userManageDTO.getPage(), userManageDTO.getLimit());
        List<SysUser> allUser;
        if (userManageDTO.getUsers() != null) {
            List<String> users = Arrays.asList(userManageDTO.getUsers());
            allUser = userService.findAllUser(users, page);
        } else {
            allUser = userService.findAllUser(page);
        }
        return Result.success(allUser);
    }


    @ApiOperation("Create user,")
    @PostMapping("/create")
    @PreAuthorize(value = "hasAuthority('SYS_USER') AND hasAuthority('USER_MANAGE')")
    Result createUser(@RequestBody UserManageDTO userManageDTO) {
        if (StringUtils.isNotBlank(userManageDTO.getUsername()) &&
                StringUtils.isNotBlank(userManageDTO.getUser_nickname()) &&
                StringUtils.isNotBlank(userManageDTO.getPassword())) {
            SysUser user = new SysUser().setUserName(userManageDTO.getUsername())
                    .setUserNickName(userManageDTO.getUser_nickname())
                    .setPassword(userManageDTO.getPassword());
            if (userService.add(user)) {
                return Result.success(ReturnStatus.USER_CREATE_SUCCESSFUL);
            }
        }
        throw new UserException(ReturnStatus.USER_CREATE_FAIL.getStatusMessage());
    }

    @ApiOperation("Update user")
    @PutMapping("/update")
    @PreAuthorize(value = "hasAuthority('SYS_USER') AND hasAuthority('USER_MANAGE')")
    Result updateUser(@RequestBody UserManageDTO userManageDTO) {
        String username = userManageDTO.getUsername();
        if (StringUtils.isNotBlank(username)) {
            SysUser user = new SysUser().setUserName(username)
                    .setUserNickName(userManageDTO.getUser_nickname())
                    .setPassword(userManageDTO.getPassword());
            if (userService.update(user)) {
                return Result.success(ReturnStatus.USER_UPDATE_SUCCESSFUL);
            }
        }
        throw new UserException(ReturnStatus.USER_UPDATE_FAIL.getStatusMessage());
    }

    @ApiOperation("Delete user,Long user_id,String username,String user_nickname,")
    @DeleteMapping("/delete")
    @PreAuthorize(value = "hasAuthority('SYS_USER') AND hasAuthority('USER_MANAGE')")
    Result deleteUser(@RequestBody UserManageDTO userManageDTO) {
        SysUser user = new SysUser().setId(userManageDTO.getUser_id())
                .setUserName(userManageDTO.getUsername())
                .setPassword(userManageDTO.getPassword());
        if (userService.del(user)) {
            return Result.success(ReturnStatus.USER_DELETE_SUCCESSFUL);
        }
        throw new UserException(ReturnStatus.USER_DELETE_FAIL.getStatusMessage());

    }


}
