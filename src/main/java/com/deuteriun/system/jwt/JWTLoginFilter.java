package com.deuteriun.system.jwt;

import com.deuteriun.system.entity.LoginInfoDTO;
import com.deuteriun.system.security.conf.AuthenticateFailureImpl;
import com.deuteriun.system.security.conf.AuthenticateSuccessImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

    @Resource
    PasswordEncoder passwordEncoder;

    @Resource
    AuthenticateSuccessImpl authenticateSuccess;

    @Resource
    AuthenticateFailureImpl authenticateFailure;

    public JWTLoginFilter(String defaultFilterProcessesUrl, AuthenticationManager authenticationManager) {
        super(new AntPathRequestMatcher(defaultFilterProcessesUrl));
        setAuthenticationManager(authenticationManager);
    }

    @SneakyThrows
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        LoginInfoDTO loginInfoDTO = new ObjectMapper().readValue(request.getInputStream(), LoginInfoDTO.class);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                loginInfoDTO.getUsername(),
                passwordEncoder);
        return getAuthenticationManager().authenticate(usernamePasswordAuthenticationToken);
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        authenticateSuccess.onAuthenticationSuccess(request, response, chain, authResult);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        authenticateFailure.onAuthenticationFailure(request,response,failed);
    }
}

