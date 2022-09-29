package com.deuteriun.system.security.impl;

import com.deuteriun.common.utils.StringUtils;
import com.deuteriun.system.security.entity.SecurityUser;
import com.deuteriun.system.service.SecurityService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * @author gavin
 */
@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

    @Resource
    SecurityService securityService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Get username for front-end ,and check it into database
        //illgale input cheek
        if (StringUtils.isBlank(username)) {
            throw new UsernameNotFoundException("User name not found!");
        }
        SecurityUser securityUser = securityService.getSecurityUserByName(username);
        if (securityUser.getUsername() == null){
            throw new UsernameNotFoundException("User name not found!");
        }
        return new SecurityUser(securityUser.getId(),securityUser.getUsername(), securityUser.getPassword(), securityUser.getGrantedAuthorityList());
    }
}

