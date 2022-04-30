package com.deuteriun.system.security.conf;

import com.deuteriun.system.common.utils.Result;
import com.deuteriun.system.common.utils.ServletUtil;
import com.deuteriun.system.common.utils.DeuteriunJwtUtils;
import com.deuteriun.system.db.CacheService;
import com.deuteriun.system.entity.SysLoginJwtBlacklist;
import com.deuteriun.system.security.entity.SecurityUser;
import com.deuteriun.system.service.SysLoginJwtBlacklistService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
public class RestAuthenticateSuccessImpl implements AuthenticationSuccessHandler {

    @Resource
    CacheService cacheService;

    @Resource
    SysLoginJwtBlacklistService sysLoginJwtBlacklistService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        SecurityUser principal = (SecurityUser) authentication.getPrincipal();
        SecurityUser securityUser = new SecurityUser();
        securityUser.setUsername(principal.getUsername());
        securityUser.setGrantedAuthorityList(principal.getGrantedAuthorityList());
        String jwt = DeuteriunJwtUtils.generateJWT(authentication);
        //Reset logout blacklist
        SysLoginJwtBlacklist sysLoginJwtBlacklist = sysLoginJwtBlacklistService.listByuserName(principal.getUsername());
        if (sysLoginJwtBlacklist != null) {
            sysLoginJwtBlacklistService.delete(principal.getUsername());
        }
        //Set jwt token to cache
        cacheService.put(principal.getUsername(), jwt);
        //Return success message and jwt that should be store into header in Front-end
        Result success;
        success = Result.success(jwt, request.getServletPath());
        try {
            ServletUtil.render(response, success);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}