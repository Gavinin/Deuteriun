package com.deuteriun.system.security.filter;

import com.deuteriun.common.enums.HttpMethodsStatus;
import com.deuteriun.system.entity.LoginDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author gavin
 */
public class JwtAuthenticationSecurityFilter extends AbstractAuthenticationProcessingFilter {

    private static final boolean POST_ONLY = true;
    private static final String REQUEST_ADD = "/apis/user/login";


    public JwtAuthenticationSecurityFilter(AuthenticationManager authenticationManager) {
        super(new AntPathRequestMatcher(REQUEST_ADD, HttpMethodsStatus.POST));
        setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException {

        if (POST_ONLY && !request.getMethod().equals(HttpMethodsStatus.POST )) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        try {
            LoginDTO loginInfo = new ObjectMapper().readValue(request.getInputStream(), LoginDTO.class);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginInfo.getUsername(), loginInfo.getPassword());
            return getAuthenticationManager().authenticate(authenticationToken);
        } catch (UnrecognizedPropertyException unrecognizedPropertyException) {
            return null;
        }
    }

}
