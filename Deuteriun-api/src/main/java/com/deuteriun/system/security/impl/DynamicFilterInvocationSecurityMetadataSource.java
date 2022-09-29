package com.deuteriun.system.security.impl;

import com.deuteriun.common.enums.DefaultRolesStatus;
import com.deuteriun.system.cache.FilterRoleCacheConf;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.*;
/**
 * @ClassName DynamicFilterInvocationSecurityMetadataSource
 * @Description TODO
 * @Author gavin
 * @Date 15/5/2022 19:10
 * @Version 1.0
 **/
public class DynamicFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        FilterInvocation filterInvocation = (FilterInvocation) object;
        String url = filterInvocation.getRequestUrl();
        for (Map.Entry<String, List<String>> entry : FilterRoleCacheConf.getUrlRoleCache().entrySet()) {
            if (antPathMatcher.match(entry.getKey(), url)) {
                String[] array = entry.getValue().toArray(new String[0]);
                return SecurityConfig.createList(array);
            }
        }
        return List.of(new SecurityConfig(DefaultRolesStatus.ANYONE.getRoleCode()));
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}
