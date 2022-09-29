package com.deuteriun.system.security.impl;

import com.deuteriun.common.enums.DefaultRolesStatus;
import com.deuteriun.system.utils.DeuteriunConfigurationUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;

import javax.annotation.Resource;
import java.util.Collection;


/**
 * @ClassName CustomAccessDecisionVoter
 * @Description TODO
 * @Author gavin
 * @Date 13/5/2022 02:30
 * @Version 1.0
 **/
@Configuration
public class CustomAccessDecisionVoter implements AccessDecisionVoter<FilterInvocation> {
    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    /**
     * This voter decide the allow rule
     *
     * @param authentication the caller making the invocation
     * @param object         the secured object being invoked
     * @param attributes     the configuration attributes associated with the secured object
     * @return
     */
    @Override
    public int vote(Authentication authentication, FilterInvocation object, Collection<ConfigAttribute> attributes) {
        assert authentication != null;
        assert object != null;

        Collection<GrantedAuthority> roles = (Collection<GrantedAuthority>) authentication.getAuthorities();
        for (ConfigAttribute attribute : attributes) {
            if (attribute.getAttribute() == null) {
                continue;
            }

            if (this.supports(attribute)) {
                for (GrantedAuthority authority : roles) {
                    if (attribute.getAttribute().equals(authority.getAuthority())) {
                        return ACCESS_GRANTED;
                    }
                }
            }
            if (attribute.getAttribute().equals(DefaultRolesStatus.ANYONE.getRoleCode())) {
                return DeuteriunConfigurationUtils.ANYONE_PERMISSION ? ACCESS_GRANTED : ACCESS_DENIED;
            }
        }
        return ACCESS_ABSTAIN;
    }


}
