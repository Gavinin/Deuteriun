package com.deuteriun.system.security.filter;

import com.deuteriun.system.entity.LoginInfoDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationSecurityFilter extends AbstractAuthenticationProcessingFilter {

    private static final boolean POST_ONLY = true;
    private static final String ADDR = "/login/jwt";
    private static final String HTTP_METHOD_POST = "POST";


    public JwtAuthenticationSecurityFilter(AuthenticationManager authenticationManager) {
        super(new AntPathRequestMatcher(ADDR, HTTP_METHOD_POST));
        setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException {

        if (POST_ONLY &&  !request.getMethod().equals(HTTP_METHOD_POST)){
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }

        LoginInfoDTO loginInfo = new ObjectMapper().readValue(request.getInputStream(), LoginInfoDTO.class);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginInfo.getUsername(), loginInfo.getPassword());
        return getAuthenticationManager().authenticate(authenticationToken);
    }

}
