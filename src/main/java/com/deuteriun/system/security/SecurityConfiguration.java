package com.deuteriun.system.security;

import com.deuteriun.system.security.conf.AuthenticateFailureImpl;
import com.deuteriun.system.security.conf.AuthenticateSuccessImpl;
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

    @Resource
    UserDetailsServiceImpl userDetailsService;



    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {    //auth.inMemoryAuthentication()
        auth.userDetailsService(userDetailsService);
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
