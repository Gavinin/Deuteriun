package com.deuteriun.system.security.impl;

import com.deuteriun.common.enums.DefaultRolesStatus;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;

import java.util.Collection;


/**
 * @ClassName RootAccessDecisionVoter
 * @Description TODO
 * @Author gavin
 * @Date 12/5/2022 21:16
 * @Version 1.0
 **/
@Configuration
public class RootAccessDecisionVoter implements AccessDecisionVoter<FilterInvocation> {
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

        int result = ACCESS_ABSTAIN;
        Collection<GrantedAuthority> roles = (Collection<GrantedAuthority>) authentication.getAuthorities();
        for (ConfigAttribute attribute : attributes) {
            if (attribute.getAttribute() == null) {
                continue;
            }
            if (this.supports(attribute)) {
                for (GrantedAuthority role : roles) {
                    if (role.getAuthority().equals(DefaultRolesStatus.SYS_ROOT.getRoleCode())) {
                        result = ACCESS_GRANTED;
                    }
                }
            }
        }
        return result;
    }
}
