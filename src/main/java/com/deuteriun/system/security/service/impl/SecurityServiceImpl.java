package com.deuteriun.system.security.service.impl;

import com.deuteriun.system.entity.SysUser;
import com.deuteriun.system.entity.SecurityUser;
import com.deuteriun.system.security.service.SecurityService;
import com.deuteriun.system.service.SysUserRoleService;
import com.deuteriun.system.service.SysUserService;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SecurityServiceImpl implements SecurityService {

    @Resource
    SysUserService sysUserService;

    @Resource
    SysUserRoleService sysUserRoleService;


    @Override
    public SecurityUser getUserDetailByName(String userName) {
        SysUser sysUser = sysUserService.getUserByName(userName);
        if (sysUser==null){
            throw new UsernameNotFoundException("");
        }else if (sysUser.getBan()){
            throw new LockedException("");
        }else if (sysUser.getDel()){
            throw new DisabledException("");
        }
        SecurityUser securityUser = new SecurityUser();
        securityUser.setUsername(sysUser.getNameId());
        securityUser.setPassword(sysUser.getPassword());
        securityUser.setGrantedAuthorityList(sysUserRoleService.getGrantedAuthorityListById(sysUser.getId()));

        return securityUser;
    }



}
