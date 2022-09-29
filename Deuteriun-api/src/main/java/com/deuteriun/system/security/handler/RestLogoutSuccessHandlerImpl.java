package com.deuteriun.system.security.handler;

import com.deuteriun.common.enums.ReturnStatus;
import com.deuteriun.common.utils.Result;
import com.deuteriun.common.utils.StringUtils;
import com.deuteriun.system.service.SecurityService;
import com.deuteriun.system.utils.ServletUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.deuteriun.common.enums.ReturnStatus.SYSTEM_LOGOUT_SUCCESS;

/**
 * @author gavin
 */
@Configuration
public class RestLogoutSuccessHandlerImpl implements LogoutSuccessHandler {
    public final static String CONTENT_TYPE = "application/json;charset=utf-8";

    @Resource
    SecurityService securityService;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String token = ServletUtil.getTokenFromHttpRequest(request);
        Result result = new Result(ReturnStatus.ERROR);
        if (StringUtils.isNoneBlank(token)) {
            Boolean hasLogout = securityService.logoutNow(token);

            if (hasLogout) {
                result = new Result(SYSTEM_LOGOUT_SUCCESS);
            }
        }
        response.setContentType(CONTENT_TYPE);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getWriter(), result);
    }

}

