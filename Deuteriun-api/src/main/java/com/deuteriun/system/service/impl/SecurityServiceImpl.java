package com.deuteriun.system.service.impl;

import com.deuteriun.common.utils.StringUtils;
import com.deuteriun.system.cache.CacheService;
import com.deuteriun.system.entity.SysLoginJwtBlacklist;
import com.deuteriun.system.entity.SysUser;
import com.deuteriun.system.exception.AuthorException;
import com.deuteriun.system.security.entity.SecurityUser;
import com.deuteriun.system.service.SecurityService;
import com.deuteriun.system.service.SysLoginJwtBlacklistService;
import com.deuteriun.system.service.SysUserRoleService;
import com.deuteriun.system.service.SysUserService;
import com.deuteriun.system.utils.JwtUtils;
import com.deuteriun.system.utils.SecurityUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;

/**
 * @author gavin
 */
@Service
public class SecurityServiceImpl implements SecurityService {

    @Resource
    SysUserService userService;

    @Resource(name = "Redis1")
    CacheService cacheService;

    @Resource
    SysUserRoleService sysUserRoleService;

    @Resource
    SysLoginJwtBlacklistService sysLoginJwtBlacklistService;

    @Override
    public String generateJWTBySysUserName(String username) {
        if (StringUtils.isBlank(username)) {
            throw new RuntimeException("User name must not null");
        }
        SecurityUser userDetailByName = getSecurityUserByName(username);
        return JwtUtils.generateJwt(userDetailByName);
    }

    @Override
    public SecurityUser getSecurityUserByName(String userName) {
        SysUser sysUser = userService.getUserByName(userName);
        if (sysUser == null) {
            throw new AuthorException("User or Password is wrong");
        } else if (sysUser.getBan()) {
            throw new AuthorException("This user has been locked");
        } else if (sysUser.getDel()) {
            throw new AuthorException("This user has been deleted");
        }
        SecurityUser securityUser = new SecurityUser();
        securityUser.setId(sysUser.getId());
        securityUser.setUsername(sysUser.getUserName());
        securityUser.setPassword(sysUser.getPassword());
        securityUser.setGrantedAuthorityList(sysUserRoleService.getGrantedAuthorityListById(sysUser.getId()));

        return securityUser;
    }

    @Override
    public SecurityUser getCurrentUser() {
        String name = Objects.requireNonNull(SecurityUtils.getAuthentication()).getName();
        if (StringUtils.isBlank(name)) {
            throw new AuthorException("Can't verify current user");
        }
        return getSecurityUserByName(name);
    }

    @Override
    public Boolean logoutNow(String token) {
        String username = JwtUtils.getUsername(token);
        cacheService.delete(username);

        //Save this token to sys_login_jwt_blacklist
        SysLoginJwtBlacklist sysLoginJwtBlacklist = new SysLoginJwtBlacklist();
        sysLoginJwtBlacklist.setCreateDate(new Date());
        sysLoginJwtBlacklist.setUserName(username);
        sysLoginJwtBlacklist.setUserJwt(token);
        return sysLoginJwtBlacklistService.save(sysLoginJwtBlacklist);
    }


}
