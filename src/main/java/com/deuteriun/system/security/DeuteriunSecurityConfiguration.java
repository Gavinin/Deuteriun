package com.deuteriun.system.security;

import com.deuteriun.system.exception.RestAuthenticationEntryPoint;
import com.deuteriun.system.security.conf.JwtAuthenticationSecurityConfig;
import com.deuteriun.system.security.filter.TokenAuthenticationSecurityFilter;
import com.deuteriun.system.security.impl.RestLogoutSuccessHandlerImpl;
import com.deuteriun.system.security.impl.RootAccessDecisionProcessor;
import com.deuteriun.system.security.service.impl.JwtUserDetailsServiceImpl;
import com.deuteriun.system.utils.DeuteriunConfigurationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.ConsensusBased;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;

import java.util.ArrayList;
import java.util.List;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class DeuteriunSecurityConfiguration extends WebSecurityConfigurerAdapter {

    public static final String LOGOUT_ADDRESS = "/logout";

    final PasswordEncoder passwordEncoder;

    final RestLogoutSuccessHandlerImpl logoutSuccessHandler;

    final JwtUserDetailsServiceImpl userDetailsService;

    final JwtAuthenticationSecurityConfig jwtAuthenticationSecurityConfig;

    final RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    private DeuteriunConfigurationUtils deuteriunConfigurationUtils;


    @Bean
    protected AccessDecisionManager accessDecisionManager() {
        List<AccessDecisionVoter<? extends Object>> decisionVoters = new ArrayList<>();
        decisionVoters.add(new WebExpressionVoter());
        decisionVoters.add(new RootAccessDecisionProcessor());
//        decisionVoters.add(accessDecisionProcessor());
        return new ConsensusBased(decisionVoters);
    }


    @Autowired
    public void setHttpAllowAddressList(DeuteriunConfigurationUtils deuteriunConfigurationUtils) {
        this.deuteriunConfigurationUtils = deuteriunConfigurationUtils;
    }

    @Autowired
    public DeuteriunSecurityConfiguration(PasswordEncoder passwordEncoder, RestLogoutSuccessHandlerImpl logoutSuccessHandler, JwtUserDetailsServiceImpl userDetailsService, JwtAuthenticationSecurityConfig jwtAuthenticationSecurityConfig, RestAuthenticationEntryPoint restAuthenticationEntryPoint) {
        this.passwordEncoder = passwordEncoder;
        this.logoutSuccessHandler = logoutSuccessHandler;
        this.userDetailsService = userDetailsService;
        this.jwtAuthenticationSecurityConfig = jwtAuthenticationSecurityConfig;
        this.restAuthenticationEntryPoint = restAuthenticationEntryPoint;
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
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .logout()
                .logoutSuccessHandler(logoutSuccessHandler)
                .and()
                .authorizeRequests()
                .anyRequest().authenticated()
                .and().exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint)
                .and()
                .addFilterBefore(authenticationSecurityFilterBean(), UsernamePasswordAuthenticationFilter.class)
                .csrf().disable();

    }

    @Bean
    public TokenAuthenticationSecurityFilter authenticationSecurityFilterBean() {
        return new TokenAuthenticationSecurityFilter();
    }


}
