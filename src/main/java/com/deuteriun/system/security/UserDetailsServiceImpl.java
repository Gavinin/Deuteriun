package com.deuteriun.system.security;

import com.deuteriun.common.utils.StringUtils;
import com.deuteriun.system.security.entity.UserDo;
import com.deuteriun.system.security.service.SecurityService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    SecurityService securityService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //1. get username for front-end ,and check it into database
        if (StringUtils.isBlank(username)) {
            throw new UsernameNotFoundException("User name not found!");
        }

        UserDo user = securityService.getUserDetailByName(username);

        //2.Compare password between front and db using match


    return new User(user.getName(),user.getPassword(),user.getGrantedAuthorityList());
//        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
//        grantedAuthorityList.add(new SimpleGrantedAuthority("sys:select"));
//    return  new User("admin","admin", grantedAuthorityList);
    }
}
