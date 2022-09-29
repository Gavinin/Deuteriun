package com.deuteriun.system.security;

import com.deuteriun.common.enums.HttpMethodsStatus;
import com.deuteriun.system.exception.RestAuthenticationEntryPoint;
import com.deuteriun.system.security.conf.JwtAuthenticationSecurityConfig;
import com.deuteriun.system.security.filter.TokenAuthenticationSecurityFilter;
import com.deuteriun.system.security.handler.RestAccessDeniedHandler;
import com.deuteriun.system.security.handler.RestLogoutSuccessHandlerImpl;
import com.deuteriun.system.security.impl.CustomAccessDecisionVoter;
import com.deuteriun.system.security.impl.DynamicFilterInvocationSecurityMetadataSource;
import com.deuteriun.system.security.impl.RootAccessDecisionVoter;
import com.deuteriun.system.security.impl.JwtUserDetailsServiceImpl;
import com.deuteriun.system.utils.DeuteriunConfigurationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.ConsensusBased;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gavin
 */
@EnableWebSecurity
public class DeuteriunSecurityConfiguration extends WebSecurityConfigurerAdapter {

    public static final String LOGOUT_ADDRESS = "/apis/logout";

    final PasswordEncoder passwordEncoder;
    final RestLogoutSuccessHandlerImpl logoutSuccessHandler;
    final RestAuthenticationEntryPoint restAuthenticationEntryPoint;
    final RestAccessDeniedHandler restAccessDeniedHandler;
    final JwtUserDetailsServiceImpl userDetailsService;
    final DeuteriunConfigurationUtils deuteriunConfigurationUtils;
    final TokenAuthenticationSecurityFilter tokenAuthenticationSecurityFilter;
    final JwtAuthenticationSecurityConfig jwtAuthenticationSecurityConfig;

    @Autowired
    public DeuteriunSecurityConfiguration(PasswordEncoder passwordEncoder,
                                          RestLogoutSuccessHandlerImpl logoutSuccessHandler,
                                          JwtUserDetailsServiceImpl userDetailsService,
                                          RestAuthenticationEntryPoint restAuthenticationEntryPoint,
                                          DeuteriunConfigurationUtils deuteriunConfigurationUtils,
                                          TokenAuthenticationSecurityFilter tokenAuthenticationSecurityFilter,
                                          JwtAuthenticationSecurityConfig jwtAuthenticationSecurityConfig,
                                          RestAccessDeniedHandler restAccessDeniedHandler) {
        this.passwordEncoder = passwordEncoder;
        this.logoutSuccessHandler = logoutSuccessHandler;
        this.userDetailsService = userDetailsService;
        this.restAuthenticationEntryPoint = restAuthenticationEntryPoint;
        this.deuteriunConfigurationUtils = deuteriunConfigurationUtils;
        this.tokenAuthenticationSecurityFilter = tokenAuthenticationSecurityFilter;
        this.jwtAuthenticationSecurityConfig = jwtAuthenticationSecurityConfig;
        this.restAccessDeniedHandler = restAccessDeniedHandler;
    }

    DynamicFilterInvocationSecurityMetadataSource getDynamicFilterInvocationSecurityMetadataSource() {
        return new DynamicFilterInvocationSecurityMetadataSource();
    }


    @Bean
    AccessDecisionManager accessDecisionManager() {
        List<AccessDecisionVoter<?>> decisionVoters = new ArrayList<>();
        decisionVoters.add(new WebExpressionVoter());
        decisionVoters.add(new RootAccessDecisionVoter());
        decisionVoters.add(new CustomAccessDecisionVoter());
        return new ConsensusBased(decisionVoters);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers(deuteriunConfigurationUtils.getHttpPermitAllAddressList().toArray(new String[0])).permitAll();

        http
// —————————————————Disable form login
                .formLogin().disable()

// —————————————————
                .apply(jwtAuthenticationSecurityConfig)

// —————————————————Logout process
                .and()
                .logout().logoutSuccessHandler(logoutSuccessHandler)
                .logoutRequestMatcher(new OrRequestMatcher(
                        new AntPathRequestMatcher(LOGOUT_ADDRESS, HttpMethodsStatus.GET)
                )).invalidateHttpSession(true)
                .clearAuthentication(true).logoutSuccessHandler(logoutSuccessHandler)

// —————————————————
                .and().authorizeRequests()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O object) {
                        object.setAccessDecisionManager(accessDecisionManager());
                        object.setSecurityMetadataSource(getDynamicFilterInvocationSecurityMetadataSource());
                        return object;
                    }
                }).anyRequest().authenticated()

// —————————————————Disable session
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

// —————————————————Exception Handler
                .and()
                .exceptionHandling()
                .accessDeniedHandler(restAccessDeniedHandler)
                .authenticationEntryPoint(restAuthenticationEntryPoint)

// —————————————————Custom Filter
                .and().addFilterBefore(tokenAuthenticationSecurityFilter, BasicAuthenticationFilter.class)
                .cors().and()
// —————————————————Disable CSRF
                .csrf().disable();

    }
}
