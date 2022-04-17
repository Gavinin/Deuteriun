package com.deuteriun.system.security.conf;

import com.deuteriun.system.common.utils.ServletUtil;
import com.deuteriun.system.jwt.JwtTokenService;
import com.deuteriun.system.security.entity.SecurityUser;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
public class RestAuthenticateSuccessImpl implements AuthenticationSuccessHandler {

    @Resource
    JwtTokenService jwtTokenUtils;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        SecurityUser principal = (SecurityUser) authentication.getPrincipal();
        SecurityUser securityUser = new SecurityUser();
        securityUser.setGrantedAuthorityList(principal.getGrantedAuthorityList());
        securityUser.setUsername(principal.getUsername());
        String token = jwtTokenUtils.generateToken(authentication);
        try {
            ServletUtil.render(response, token);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}