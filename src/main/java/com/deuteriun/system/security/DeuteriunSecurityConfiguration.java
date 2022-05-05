package com.deuteriun.system.security;

import com.deuteriun.system.security.conf.JwtAuthenticationSecurityConfig;
import com.deuteriun.system.security.conf.RestLogoutSuccessHandlerImpl;
import com.deuteriun.system.common.utils.DeuteriunConfigurationUtils;
import com.deuteriun.system.security.filter.TokenAuthenticationSecurityFilter;
import com.deuteriun.system.security.service.impl.JwtUserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;

@EnableWebSecurity
public class DeuteriunSecurityConfiguration extends WebSecurityConfigurerAdapter {

    public static final String LOGOUT_ADDRESS = "/logout";

    final PasswordEncoder passwordEncoder;

    final RestLogoutSuccessHandlerImpl logoutSuccessHandler;

    final JwtUserDetailsServiceImpl userDetailsService;

    final JwtAuthenticationSecurityConfig jwtAuthenticationSecurityConfig;

    private DeuteriunConfigurationUtils deuteriunConfigurationUtils;

    @Autowired
    public void setHttpAllowAddressList(DeuteriunConfigurationUtils deuteriunConfigurationUtils) {
        this.deuteriunConfigurationUtils = deuteriunConfigurationUtils;
    }

    @Autowired
    public DeuteriunSecurityConfiguration(PasswordEncoder passwordEncoder, RestLogoutSuccessHandlerImpl logoutSuccessHandler, JwtUserDetailsServiceImpl userDetailsService, JwtAuthenticationSecurityConfig jwtAuthenticationSecurityConfig) {
        this.passwordEncoder = passwordEncoder;
        this.logoutSuccessHandler = logoutSuccessHandler;
        this.userDetailsService = userDetailsService;
        this.jwtAuthenticationSecurityConfig = jwtAuthenticationSecurityConfig;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers(deuteriunConfigurationUtils.getHttpPermitAllAddressList().toArray(new String[0])).permitAll();

        http.formLogin()
                .disable()
                .apply(jwtAuthenticationSecurityConfig)
                .and().
                logout().logoutRequestMatcher(new OrRequestMatcher(
                        new AntPathRequestMatcher(LOGOUT_ADDRESS, "GET")
                ))
                .invalidateHttpSession(true)
                .clearAuthentication(true).logoutSuccessHandler(logoutSuccessHandler)
                .and()
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .logout()
                .logoutSuccessHandler(logoutSuccessHandler)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(authenticationSecurityFilterBean(), UsernamePasswordAuthenticationFilter.class)
                .csrf().disable();

    }

    @Bean
    public TokenAuthenticationSecurityFilter authenticationSecurityFilterBean() {
        return new TokenAuthenticationSecurityFilter();
    }


}
