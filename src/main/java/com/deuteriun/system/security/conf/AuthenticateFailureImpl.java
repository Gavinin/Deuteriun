package com.deuteriun.system.security.conf;

import com.deuteriun.system.common.enums.ReturnStatus;
import com.deuteriun.system.entity.Result;
import com.deuteriun.system.common.enums.WebStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class AuthenticateFailureImpl implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        response.setContentType(WebStatus.CONTENT_TYPE);

        Result result;

        if (exception.getCause() instanceof LockedException) {
            result = new Result(ReturnStatus.USER_ACCOUNT_LOCKED);

        } else if (exception.getCause() instanceof BadCredentialsException) {
            result = new Result(ReturnStatus.USER_PASSWORD_ERROR);
        } else if (exception.getCause() instanceof DisabledException) {
            result = new Result(ReturnStatus.USER_ACCOUNT_DISABLE);
        } else if (exception.getCause() instanceof AccountExpiredException) {
            result = new Result(ReturnStatus.USER_ACCOUNT_EXPIRED);
        } else if (exception.getCause() instanceof CredentialsExpiredException) {
            result = new Result(ReturnStatus.USER_PASSWORD_EXPIRED);
        } else {
            result = new Result(ReturnStatus.USER_UNKNOWN_ERROR);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getWriter(), result);

    }
}
