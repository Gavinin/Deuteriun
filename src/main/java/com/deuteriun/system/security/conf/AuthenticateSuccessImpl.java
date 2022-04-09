package com.deuteriun.system.security.conf;

import com.deuteriun.system.common.enums.WebStatus;
import com.deuteriun.system.entity.Result;
import com.deuteriun.system.entity.SecurityUser;
import com.deuteriun.system.jwt.JwtTokenUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
public class AuthenticateSuccessImpl implements AuthenticationSuccessHandler {

    @Resource
    JwtTokenUtils jwtTokenUtils;

    /**
     *
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        // Create JSON
        SecurityUser principal = (SecurityUser) authentication.getPrincipal();
        SecurityUser securityUser = new SecurityUser();
        securityUser.setGrantedAuthorityList(principal.getGrantedAuthorityList());
        securityUser.setUsername(principal.getUsername());
        String token = jwtTokenUtils.generateToken(authentication);
        try {
            //登录成功時，返回json格式进行提示
            response.setContentType(WebStatus.CONTENT_TYPE);
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(response.getWriter(), Result.success(token));
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}