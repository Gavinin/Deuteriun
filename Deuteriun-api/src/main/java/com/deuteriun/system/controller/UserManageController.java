package com.deuteriun.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deuteriun.common.enums.ReturnStatus;
import com.deuteriun.common.utils.Result;
import com.deuteriun.common.utils.StringUtils;
import com.deuteriun.system.cache.CacheService;
import com.deuteriun.system.entity.SysUser;
import com.deuteriun.system.entity.UserManageDTO;
import com.deuteriun.system.exception.SystemException;
import com.deuteriun.system.exception.UserException;
import com.deuteriun.system.service.SecurityPolicyService;
import com.deuteriun.system.service.SecurityService;
import com.deuteriun.system.service.SysUserService;
import com.deuteriun.system.utils.SecurityUtils;
import com.deuteriun.system.utils.ServletUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author gavin
 */
@RestController
@Api(tags = "User", value = "User manage controller")
@RequestMapping("apis/user")
public class UserManageController {

    @Value("${deuteriun.reg-ip-block-times}")
    private Integer ipBlockTimes;

    public static final String SERVICE_NAME = "REG";

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private SecurityPolicyService securityPolicyService;

    final CacheService cacheService;

    final SysUserService userService;

    final SecurityService securityService;

    public UserManageController(SysUserService userService, SecurityService securityService, @Qualifier("Redis1") CacheService cacheService) {
        this.cacheService = cacheService;
        this.userService = userService;
        this.securityService = securityService;
    }

    @ApiOperation("get current user details by current token")
    @GetMapping("/current-user")
    Result getCurrentUserInformation() {
        String name = Objects.requireNonNull(SecurityUtils.getAuthentication()).getName();
        if (StringUtils.isBlank(name)) {
            throw new SystemException("Can't verify current user.");
        }
        SysUser user = userService.getUserByName(name);
        userService.getUserWithRoles(user);
        return Result.success(user);
    }

    @ApiOperation("List users Input 3 parameter specific user name with String[] , page number and page limit")
    @GetMapping("/list")
    Result listUsers(@RequestBody UserManageDTO userManageDTO) {
        Page<SysUser> page = new Page<>(userManageDTO.getPage(), userManageDTO.getLimit());
        List<SysUser> allUser;
        if (userManageDTO.getUsers() != null) {
            List<String> users = Arrays.asList(userManageDTO.getUsers());
            allUser = userService.getAllUserWithRoles(users, page);
        } else {
            allUser = userService.getAllUserWithRoles(page);
        }
        return Result.success(allUser);
    }


    @ApiOperation("Register user,")
    @PostMapping("/register")
    Result createUser(HttpServletRequest request, @RequestBody UserManageDTO userManageDTO) {
        securityPolicyService.ipBlockor(SERVICE_NAME, request, ipBlockTimes);
        if (StringUtils.isNotBlank(userManageDTO.getUsername()) &&
                StringUtils.isNotBlank(userManageDTO.getUserNickname()) &&
                StringUtils.isNotBlank(userManageDTO.getPassword())) {
            SysUser user = new SysUser().setUserName(userManageDTO.getUsername())
                    .setUserNickName(userManageDTO.getUserNickname())
                    .setPassword(userManageDTO.getPassword());
            SysUser sysUser = userService.add(user);
            if (sysUser != null) {
                String jwtBySysUserName = securityService.generateJWTBySysUserName(sysUser.getUserName());

                cacheService.expire(sysUser.getUserName(), jwtBySysUserName);
                return Result.success(ReturnStatus.USER_CREATE_SUCCESSFUL, (Object) jwtBySysUserName);
            }
        }
        throw new UserException(ReturnStatus.USER_CREATE_FAIL.getStatusMessage());
    }


    @ApiOperation("Update user")
    @PutMapping("/update")
    Result updateUser(@RequestBody UserManageDTO userManageDTO) {
        String authUserName = Objects.requireNonNull(SecurityUtils.getAuthentication()).getName();
        String username = userManageDTO.getUsername();
        if (StringUtils.isNotBlank(username) && StringUtils.isNoneBlank(authUserName) && authUserName.equals(username)) {
            SysUser userByName = userService.getUserByName(authUserName);
            if (userByName != null) {
                if (!userByName.getUserNickName().equals(userManageDTO.getUserNickname())) {
                    userByName.setUserNickName(userManageDTO.getUserNickname());
                }
                if (!passwordEncoder.matches(userManageDTO.getPassword(), userByName.getPassword())) {
                    userByName.setPassword(passwordEncoder.encode(userManageDTO.getPassword()));
                }
                if (userService.update(userByName)) {
                    return Result.success(ReturnStatus.USER_UPDATE_SUCCESSFUL);
                }
            }
            throw new UserException(ReturnStatus.USER_ACCOUNT_NOT_EXIST.getStatusMessage());
        }
        throw new UserException(ReturnStatus.USER_UPDATE_FAIL.getStatusMessage());
    }

    @ApiOperation("Delete user, Long user_id, String username, String user_nickname")
    @DeleteMapping("/delete")
    Result deleteUser(HttpServletRequest request, @RequestBody UserManageDTO userManageDTO) {
        String authUserName = Objects.requireNonNull(SecurityUtils.getAuthentication()).getName();
        String username = userManageDTO.getUsername();
        if (StringUtils.isNotBlank(username) && StringUtils.isNoneBlank(authUserName) && authUserName.equals(username)) {
            SysUser user = new SysUser().setId(userManageDTO.getUserId())
                    .setUserName(userManageDTO.getUsername())
                    .setPassword(userManageDTO.getPassword());
            if (userService.del(user)) {
                String token = ServletUtil.getTokenFromHttpRequest(request);
                Boolean hasLogout = securityService.logoutNow(token);
                if (hasLogout) {
                    return Result.success(ReturnStatus.USER_DELETE_SUCCESSFUL);
                }
            }
        }
        throw new UserException(ReturnStatus.USER_DELETE_FAIL.getStatusMessage());
    }
}
