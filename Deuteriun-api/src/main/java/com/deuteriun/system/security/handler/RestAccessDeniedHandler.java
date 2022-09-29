package com.deuteriun.system.security.handler;

import com.deuteriun.common.enums.ReturnStatus;
import com.deuteriun.common.utils.Result;
import com.deuteriun.system.utils.ServletUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName RestAccessDeniedHandler
 * @Description TODO
 * @Author gavin
 * @Date 29/5/2022 17:51
 * @Version 1.0
 **/
@Configuration
public class RestAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        Result result = Result.error(ReturnStatus.AUTHORITY_UNAUTHORIZED);
        ServletUtil.render(request, response, result);
    }
}
