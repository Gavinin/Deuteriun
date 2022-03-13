package com.deuteriun.system.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Resource
    PasswordEncoder passwordEncoder;

    @Resource
    AuthenticateSuccessImpl authenticateSuccess;

    @Resource
    AuthenticateFailureImpl authenticateFailure;



    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {    //auth.inMemoryAuthentication()
        auth.inMemoryAuthentication().withUser("User")
                .password(passwordEncoder.encode("user"))
                .roles("USER")
                .authorities("sys:select","sys:update","sys:del")
                .and().withUser("Admin")
                .password(passwordEncoder.encode("admin"))
                .roles("ADMIN")
                .authorities("sys:select","sys:read","sys:exec","sys:del","sys:admin");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        super.configure(http);
        http.formLogin()
                .successHandler(authenticateSuccess)
                .failureHandler(authenticateFailure.authenticationFailureHandler());

        http.authorizeRequests().anyRequest().authenticated();

    }
}
