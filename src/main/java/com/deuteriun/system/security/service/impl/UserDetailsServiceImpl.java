package com.deuteriun.system.security.service.impl;

import com.deuteriun.system.common.utils.StringUtils;
import com.deuteriun.system.security.entity.SecurityUser;
import com.deuteriun.system.security.service.SecurityService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    SecurityService securityService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Get username for front-end ,and check it into database
        //illgale input cheek
        if (StringUtils.isBlank(username)) {
            throw new UsernameNotFoundException("User name not found!");
        }
        SecurityUser securityUser = securityService.getUserDetailByName(username);
        if (securityUser.getUsername() == null){
            throw new UsernameNotFoundException("User name not found!");
        }
        return new SecurityUser(securityUser.getUsername(), securityUser.getPassword(), securityUser.getGrantedAuthorityList());
    }
}

