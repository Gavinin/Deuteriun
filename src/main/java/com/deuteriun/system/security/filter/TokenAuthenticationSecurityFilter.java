package com.deuteriun.system.security.filter;

import com.deuteriun.common.enums.ReturnStatus;
import com.deuteriun.system.exception.SysException;
import com.deuteriun.system.utils.DeuteriunJwtUtils;
import com.deuteriun.common.utils.Result;
import com.deuteriun.system.utils.ServletUtil;
import com.deuteriun.system.cache.CacheService;
import com.deuteriun.system.entity.SysLoginJwtBlacklist;
import com.deuteriun.system.security.entity.SecurityUser;
import com.deuteriun.system.security.service.SecurityService;
import com.deuteriun.system.service.SysLoginJwtBlacklistService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Configuration
public class TokenAuthenticationSecurityFilter extends OncePerRequestFilter {

    @Resource
    SecurityService securityService;

    @Resource
    CacheService cacheService;

    @Resource
    SysLoginJwtBlacklistService sysLoginJwtBlacklistService;

    /**
     * Modify Filter
     * <p>
     * 使用Redis 存储在线用户，如果用户Logout 存入BlackList，
     *
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = ServletUtil.getTokenFromHttpRequest(request);
        if (StringUtils.isNotBlank(token)) {
            String username = DeuteriunJwtUtils.getUsername(token);
            String tokenFromCache = cacheService.get(username);
            if (tokenFromCache != null && tokenFromCache.equals(token)) {
                List<String> authFromJWT = DeuteriunJwtUtils.getAuth(token);
                List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
                if (authFromJWT != null) {
                    for (String authCode : authFromJWT) {
                        grantedAuthorityList.add(new SimpleGrantedAuthority(authCode));
                    }
                }
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, null, grantedAuthorityList);
                SecurityContext context = SecurityContextHolder.getContext();
                context.setAuthentication(authentication);
            } else {
                //Check token weather in blacklist
                SysLoginJwtBlacklist list = sysLoginJwtBlacklistService.listByuserNameAndToken(username, token);
                //IF token is not in blacklist,means user refresh token has expired ,need refresh token
                if (list == null) {
                    if (DeuteriunJwtUtils.validateToken(token)) {
                        //paras TOKEN for Expire Date
                        Date expireDate = DeuteriunJwtUtils.getExpireDate(token);
                        Date refreshDate = DeuteriunJwtUtils.getRefreshDate(token);
                       if (expireDate==null || refreshDate==null){
                            throw new SysException("Check your token");
                       }
                        Date currentDate = new Date();
                        SecurityUser securityUser = securityService.getUserDetailByName(username);
                        if (currentDate.after(expireDate)) {
                            //if date has expire
                            sysLoginJwtBlacklistService.save(new SysLoginJwtBlacklist(securityUser.getUsername(), token, new Date()));
                        } else if (currentDate.before(refreshDate)) {
                            //if date not expire, just refresh token was expired
                            String jwt = DeuteriunJwtUtils.generateJWT(securityUser);
                            //Refresh cache service
                            cacheService.put(securityUser.getUsername(), jwt);
                            Result success = Result.success(ReturnStatus.REFRESH_TOKEN, (Object) jwt);
                            ServletUtil.render(response, success);
                            return;
                        }

                    } else {
                        //If user has logout, need relaunch
                        ServletUtil.render(request, response, Result.error(ReturnStatus.USER_NOT_LOGIN));
                        return;
                    }

                } else {
                    //User has logout need relaunch
                    ServletUtil.render(request, response, Result.error(ReturnStatus.USER_NOT_LOGIN));
                    return;
                }
            }
        }
        filterChain.doFilter(request, response);
    }


}