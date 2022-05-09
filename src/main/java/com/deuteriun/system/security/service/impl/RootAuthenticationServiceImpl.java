package com.deuteriun.system.security.service.impl;

import com.deuteriun.system.security.service.RoleAuthenticationAbstractService;
import com.deuteriun.system.security.service.RoleAuthenticationService;
import com.deuteriun.system.utils.SecurityUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * @ClassName RootAuthenticationServiceImpl
 * @Description TODO
 * @Author gavin
 * @Date 10/5/2022 00:47
 * @Version 1.0
 **/

@Service
public class RootAuthenticationServiceImpl extends RoleAuthenticationAbstractService implements RoleAuthenticationService {

    @Override
    protected String getRoleFlag() {
        return "SYS_ROOT";
    }

    @Override
    protected Collection<GrantedAuthority> getAllGranted() {
        Collection<GrantedAuthority> allAuthorities = SecurityUtils.getAllAuthorities();
        return allAuthorities;
    }
}
