package com.deuteriun.system.security.conf;

import com.deuteriun.system.security.filter.JwtAuthenticationSecurityFilter;
import com.deuteriun.system.security.handler.RestAuthenticateFailureImpl;
import com.deuteriun.system.security.handler.RestAuthenticateSuccessImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

/**
 * @author gavin
 */
@Configuration
public class JwtAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    public static final String SERVICE_NAME = "LOGIN";

    @Resource
    PasswordEncoder passwordEncoder;

    @Resource
    RestAuthenticateSuccessImpl restAuthenticateSuccess;

    @Resource
    RestAuthenticateFailureImpl restAuthenticateFailure;

    @Resource
    UserDetailsService userDetailsService;


    @Override
    public void configure(HttpSecurity builder) throws Exception {
        JwtAuthenticationSecurityFilter jwtAuthenticationSecurityFilter = new JwtAuthenticationSecurityFilter(builder.getSharedObject(AuthenticationManager.class));
        jwtAuthenticationSecurityFilter.setAuthenticationSuccessHandler(restAuthenticateSuccess);
        jwtAuthenticationSecurityFilter.setAuthenticationFailureHandler(restAuthenticateFailure);
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        builder.authenticationProvider(provider);
        builder.addFilterBefore(jwtAuthenticationSecurityFilter, UsernamePasswordAuthenticationFilter.class);
    }



}
