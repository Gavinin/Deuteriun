package com.deuteriun.system.security;

import com.deuteriun.system.security.conf.JwtAuthenticationSecurityConfig;
import com.deuteriun.system.security.conf.RestLogoutSuccessHandlerImpl;
import com.deuteriun.system.security.filter.TokenAuthenticationSecurityFilter;
import com.deuteriun.system.security.service.impl.JwtUserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;

import javax.annotation.Resource;

@EnableWebSecurity
public class DeuteriunSecurityConfiguration extends WebSecurityConfigurerAdapter {

    public static final String SYS_USER_FLAG = "sys:user";
    public static final String OPEN_ADDRESS = "/open/**";
    public static final String USER_ADDRESS = "/api/**";
    public static final String LOGIN_ADDRESS = "/login/**";
    public static final String LOGOUT_ADDRESS = "/logout";


    @Resource
    PasswordEncoder passwordEncoder;

    @Resource
    RestLogoutSuccessHandlerImpl logoutSuccessHandler;

    @Resource
    JwtUserDetailsServiceImpl userDetailsService;

    @Resource
    JwtAuthenticationSecurityConfig jwtAuthenticationSecurityConfig;

    @Resource
    private TokenAuthenticationSecurityFilter tokenAuthenticationSecurityFilter;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    public void configure(WebSecurity web) {
        // allow Swagger
        String[] authWhiteList = {
                "/swagger-ui/**",
                "/swagger-resources/**",
                "/v3/**",
                "/csrf"
        };

        web.ignoring()
//                .antMatchers(HttpMethod.OPTIONS, "/**")
                .antMatchers(authWhiteList);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//                .ignoringAntMatchers("/login");

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
                .antMatchers(OPEN_ADDRESS).anonymous()
                .antMatchers(USER_ADDRESS).hasRole(SYS_USER_FLAG)
                .antMatchers(LOGIN_ADDRESS).permitAll()
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
    public TokenAuthenticationSecurityFilter authenticationSecurityFilterBean(){
        return new TokenAuthenticationSecurityFilter();
    }


}
