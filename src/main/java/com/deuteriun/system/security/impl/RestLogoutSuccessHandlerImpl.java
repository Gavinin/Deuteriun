package com.deuteriun.system.security.impl;

import com.deuteriun.common.enums.WebStatus;
import com.deuteriun.common.utils.Result;
import com.deuteriun.system.cache.CacheService;
import com.deuteriun.system.entity.SysLoginJwtBlacklist;
import com.deuteriun.system.service.SysLoginJwtBlacklistService;
import com.deuteriun.system.utils.DeuteriunJwtUtils;
import com.deuteriun.system.utils.ServletUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

import static com.deuteriun.common.enums.ReturnStatus.SYSTEM_LOGOUT_SUCCESS;


@Configuration
public class RestLogoutSuccessHandlerImpl implements LogoutSuccessHandler {
    @Resource
    CacheService cacheService;

    @Resource
    SysLoginJwtBlacklistService sysLoginJwtBlacklistService;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

        //del user from cache service
        String token = ServletUtil.getTokenFromHttpRequest(request);
        String username = DeuteriunJwtUtils.getUsername(token);
        cacheService.delete(username);

        //Save this token to sys_login_jwt_blacklist
        SysLoginJwtBlacklist sysLoginJwtBlacklist = new SysLoginJwtBlacklist();
        sysLoginJwtBlacklist.setCreateDate(new Date());
        sysLoginJwtBlacklist.setUserName(username);
        sysLoginJwtBlacklist.setUserJwt(token);
        sysLoginJwtBlacklistService.save(sysLoginJwtBlacklist);

        Result result = new Result(SYSTEM_LOGOUT_SUCCESS);
        response.setContentType(WebStatus.CONTENT_TYPE);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getWriter(), result);
    }

}

