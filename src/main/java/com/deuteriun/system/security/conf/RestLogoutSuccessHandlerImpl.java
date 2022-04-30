package com.deuteriun.system.security.conf;

import com.deuteriun.system.common.enums.WebStatus;
import com.deuteriun.system.common.utils.DeuteriunJwtUtils;
import com.deuteriun.system.common.utils.Result;
import com.deuteriun.system.common.utils.ServletUtil;
import com.deuteriun.system.db.CacheService;
import com.deuteriun.system.entity.SysLoginJwtBlacklist;
import com.deuteriun.system.security.entity.SecurityUser;
import com.deuteriun.system.service.SysLoginJwtBlacklistService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

import static com.deuteriun.system.common.enums.ReturnStatus.LOGOUT_SUCCESS;

@Configuration
public class RestLogoutSuccessHandlerImpl implements LogoutSuccessHandler {
    @Resource
    CacheService cacheService;

    @Resource
    SysLoginJwtBlacklistService sysLoginJwtBlacklistService;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

        String token = ServletUtil.getTokenFromHttpRequest(request);
        String username = DeuteriunJwtUtils.getUsernameFromJWT(token);
        cacheService.delete(username);
        SysLoginJwtBlacklist sysLoginJwtBlacklist = new SysLoginJwtBlacklist();
        sysLoginJwtBlacklist.setCreateDate(new Date());
        sysLoginJwtBlacklist.setUserName(username);
        sysLoginJwtBlacklist.setUserJwt(token);
        sysLoginJwtBlacklistService.save(sysLoginJwtBlacklist);

        Result result = new Result(LOGOUT_SUCCESS);
        response.setContentType(WebStatus.CONTENT_TYPE);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getWriter(), result);
    }

}

