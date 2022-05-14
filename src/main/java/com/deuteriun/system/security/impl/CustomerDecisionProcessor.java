package com.deuteriun.system.security.impl;

import com.deuteriun.common.enums.RolesStatus;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;

import java.util.Collection;

/**
 * @ClassName CustomerDecisionProcessor
 * @Description TODO
 * @Author gavin
 * @Date 13/5/2022 02:30
 * @Version 1.0
 **/
@Configuration
public class CustomerDecisionProcessor implements AccessDecisionVoter<FilterInvocation> {

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    public int vote(Authentication authentication, FilterInvocation object, Collection<ConfigAttribute> attributes) {
        assert authentication != null;
        assert object != null;

        // 拿到当前请求uri
        String requestUrl = object.getRequestUrl();
        String method = object.getRequest().getMethod();

        boolean hasCache = false;
        String[] split = requestUrl.split("/");
        // 如果没有缓存中没有此权限也就是未保护此API，弃权
        // PermissionDO permissionDO = caffeineCache.get(key, PermissionDO.class);
        if (!hasCache) {
            return ACCESS_ABSTAIN;
        }
        Collection<GrantedAuthority> roles = (Collection<GrantedAuthority>) authentication.getAuthorities();
        for (GrantedAuthority role : roles) {
            if (role.getAuthority().equals(RolesStatus.SYS_ROOT.getRoleName())) {
                return ACCESS_GRANTED;
            }
        }
        return ACCESS_DENIED;
    }

}
