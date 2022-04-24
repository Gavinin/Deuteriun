package com.deuteriun.system.security.filter;

import com.deuteriun.system.entity.LoginInfoDTO;
import com.deuteriun.system.security.conf.RestAuthenticateFailureImpl;
import com.deuteriun.system.security.conf.RestAuthenticateSuccessImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

    private static final boolean POST_ONLY = true;

    public JWTLoginFilter(AuthenticationManager authenticationManager) {
        super(new AntPathRequestMatcher("/login/jwt", "POST"));
        setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException {

        if (POST_ONLY &&  !request.getMethod().equals("POST")){
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }

        LoginInfoDTO loginInfo = new ObjectMapper().readValue(request.getInputStream(), LoginInfoDTO.class);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginInfo.getUsername(), loginInfo.getPassword());
        return getAuthenticationManager().authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {
        new RestAuthenticateSuccessImpl().onAuthenticationSuccess(request, response, authResult);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        new RestAuthenticateFailureImpl().onAuthenticationFailure(request, response, failed);
    }
}
