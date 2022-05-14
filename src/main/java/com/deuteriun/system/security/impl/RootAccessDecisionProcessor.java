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
 * @ClassName RootAccessDecisionProcessor
 * @Description TODO
 * @Author gavin
 * @Date 12/5/2022 21:16
 * @Version 1.0
 **/
@Configuration
public class RootAccessDecisionProcessor implements AccessDecisionVoter<FilterInvocation> {
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

        Collection<GrantedAuthority> roles = (Collection<GrantedAuthority>) authentication.getAuthorities();
        for (GrantedAuthority role : roles) {
            if (role.getAuthority().equals(RolesStatus.SYS_ROOT.getRoleName())) {
                return ACCESS_GRANTED;
            }
        }
        return ACCESS_ABSTAIN;
    }
}
