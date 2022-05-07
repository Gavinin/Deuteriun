package com.deuteriun.system.security.conf;

import com.deuteriun.common.enums.ReturnStatus;
import com.deuteriun.common.utils.Result;
import com.deuteriun.system.utils.ServletUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class RestAuthenticateFailureImpl implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
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
            result = new Result(ReturnStatus.SYSTEM_UNKNOWN_ERROR);
        }
        ServletUtil.render(response, result);

    }
}
