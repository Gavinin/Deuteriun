package com.deuteriun.system.security.handler;

import com.deuteriun.common.enums.ReturnStatus;
import com.deuteriun.common.utils.Result;
import com.deuteriun.system.service.SecurityPolicyService;
import com.deuteriun.system.service.impl.SecurityPolicyServiceImpl;
import com.deuteriun.system.utils.ServletUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.deuteriun.system.security.conf.JwtAuthenticationSecurityConfig.SERVICE_NAME;

/**
 * @author gavin
 */
@Configuration
public class RestAuthenticateFailureImpl implements AuthenticationFailureHandler {
    @Resource
    SecurityPolicyService securityPolicyService;

    @Value("${deuteriun.login-ip-block-times:3}")
    Integer ipBlockTimes;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        securityPolicyService.ipBlockor(SERVICE_NAME, request, ipBlockTimes + 1);
        Result result;
        if (exception instanceof LockedException) {
            result = new Result(ReturnStatus.USER_ACCOUNT_LOCKED);
        } else if (exception instanceof BadCredentialsException) {
            result = new Result(ReturnStatus.USER_PASSWORD_ERROR);
        } else if (exception instanceof DisabledException) {
            result = new Result(ReturnStatus.USER_ACCOUNT_DISABLE);
        } else if (exception instanceof AccountExpiredException) {
            result = new Result(ReturnStatus.USER_ACCOUNT_EXPIRED);
        } else if (exception instanceof CredentialsExpiredException) {
            result = new Result(ReturnStatus.USER_PASSWORD_EXPIRED);
        } else {
            result = new Result(ReturnStatus.SYSTEM_UNKNOWN_ERROR);
        }
        ServletUtil.render(request, response, result);

    }
}
