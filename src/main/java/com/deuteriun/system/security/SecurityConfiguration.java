package com.deuteriun.system.security;

import com.deuteriun.system.security.conf.AuthenticateFailureImpl;
import com.deuteriun.system.security.conf.AuthenticateSuccessImpl;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import javax.annotation.Resource;

@EnableWebSecurity
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
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        super.configure(http);
        http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                        .ignoringAntMatchers("/login");
//        http.formLogin()
//                .successHandler(authenticateSuccess)
//                .failureHandler(authenticateFailure.authenticationFailureHandler());

        http.authorizeRequests().anyRequest().authenticated();

    }
}
