package com.deuteriun.system.security.conf;

import com.deuteriun.system.common.enums.WebStatus;
import com.deuteriun.system.common.utils.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.deuteriun.system.common.enums.ReturnStatus.LOGOUT_SUCCESS;

@Configuration
public class RestLogoutSuccessHandlerImpl implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        Result result = new Result(LOGOUT_SUCCESS);
        response.setContentType(WebStatus.CONTENT_TYPE);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getWriter(), result);
    }
}
