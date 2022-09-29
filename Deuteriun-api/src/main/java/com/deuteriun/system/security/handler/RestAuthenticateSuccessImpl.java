package com.deuteriun.system.security.handler;

import com.deuteriun.common.utils.Result;
import com.deuteriun.system.cache.CacheService;
import com.deuteriun.system.entity.LoginDO;
import com.deuteriun.system.entity.SysLoginJwtBlacklist;
import com.deuteriun.system.exception.AuthorException;
import com.deuteriun.system.security.entity.SecurityUser;
import com.deuteriun.system.service.SecurityPolicyService;
import com.deuteriun.system.service.SysLoginJwtBlacklistService;
import com.deuteriun.system.utils.JwtUtils;
import com.deuteriun.system.utils.ServletUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static com.deuteriun.system.security.conf.JwtAuthenticationSecurityConfig.SERVICE_NAME;

/**
 * @author gavin
 */
@Configuration
public class RestAuthenticateSuccessImpl implements AuthenticationSuccessHandler {

    @Resource
    SecurityPolicyService securityPolicyService;

    @Value("${deuteriun.login-ip-block-times:3}")
    Integer ipBlockTimes;

    @Resource(name = "Redis1")
    CacheService cacheService;

    @Resource
    SysLoginJwtBlacklistService sysLoginJwtBlacklistService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        if (securityPolicyService.hasIpBlockor(SERVICE_NAME, request, ipBlockTimes+1)) {
            throw new AuthorException("Try too many times");
        }

        SecurityUser principal = (SecurityUser) authentication.getPrincipal();
        SecurityUser securityUser = new SecurityUser();
        securityUser.setUsername(principal.getUsername());
        securityUser.setGrantedAuthorityList(principal.getGrantedAuthorityList());
        String jwt = JwtUtils.generateJwt(securityUser);
        LoginDO loginDO = new LoginDO(jwt);
        //Reset logout blacklist
        List<SysLoginJwtBlacklist> sysLoginJwtBlacklist = sysLoginJwtBlacklistService.listByuserName(principal.getUsername());
        if (sysLoginJwtBlacklist.size() > 0) {
            sysLoginJwtBlacklistService.delete(principal.getUsername());
        }
        //Set jwt token to cache
        cacheService.expire(principal.getUsername(), jwt);
        //Return success message and jwt that should be store into header in Front-end
        Result success;
        success = Result.success(loginDO, request.getServletPath());
        try {
            ServletUtil.render(request, response, success);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}