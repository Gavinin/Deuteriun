package com.deuteriun.system.security.service.impl;

import com.deuteriun.system.security.service.RoleAuthenticationAbstractService;
import com.deuteriun.system.security.service.RoleAuthenticationService;
import com.deuteriun.system.utils.SecurityUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * @ClassName AdminAuthenticationServiceImpl
 * @Description TODO
 * @Author gavin
 * @Date 10/5/2022 01:00
 * @Version 1.0
 **/
@Service
public class AdminAuthenticationServiceImpl extends RoleAuthenticationAbstractService implements RoleAuthenticationService {

    @Override
    protected String getRoleFlag() {
        return "SYS_ADMIN";
    }

    @Override
    protected Collection<GrantedAuthority> getAllGranted() {
        return SecurityUtils.getAllAuthorities();
    }
}
