package com.deuteriun.system.security;

import com.deuteriun.system.security.conf.RestAuthenticateFailureImpl;
import com.deuteriun.system.security.conf.RestAuthenticateSuccessImpl;
import com.deuteriun.system.security.conf.RestLogoutSuccessHandlerImpl;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;

import javax.annotation.Resource;

@EnableWebSecurity
public class DeuteriunBaseConfiguration extends WebSecurityConfigurerAdapter {

    @Resource
    PasswordEncoder passwordEncoder;

    @Resource
    RestLogoutSuccessHandlerImpl logoutSuccessHandler;

    @Resource
    UserDetailsServiceImpl userDetailsService;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {    //auth.inMemoryAuthentication()
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .ignoringAntMatchers("/login");

//        http.formLogin()
//                .successHandler(authenticateSuccess)
//                .failureHandler(authenticateFailure.authenticationFailureHandler());

        http.logout().logoutRequestMatcher(new OrRequestMatcher(
                        new AntPathRequestMatcher("/logout", "GET")
                ))
                .invalidateHttpSession(true)
                .clearAuthentication(true).logoutSuccessHandler(logoutSuccessHandler);

        http.authorizeRequests().anyRequest().authenticated();

    }
}
