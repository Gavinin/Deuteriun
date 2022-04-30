package com.deuteriun.system.security.filter;

import com.auth0.jwt.JWT;
import com.deuteriun.system.common.enums.ReturnStatus;
import com.deuteriun.system.common.utils.Result;
import com.deuteriun.system.common.utils.ServletUtil;
import com.deuteriun.system.db.CacheService;
import com.deuteriun.system.common.utils.DeuteriunJwtUtils;
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
     * 使用Redis 存储在线用户，如果用户Logout，将重写Redis信息为:Logout，
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
            String username = DeuteriunJwtUtils.getUsernameFromJWT(token);
            String tokenFromCache = cacheService.get(username);
            if (tokenFromCache != null && tokenFromCache.equals(token)) {

                List<String> authFromJWT = DeuteriunJwtUtils.getAuthFromJWT(token);
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

                SysLoginJwtBlacklist list = sysLoginJwtBlacklistService.listByuserNameAndToken(username, token);
                //Not Logout
                if (list == null) {
                    //paras TOKEN for Expire Date
                    Date expireDate = DeuteriunJwtUtils.getExpireDate(token);
                    Date currentDate = new Date();
                    if (currentDate.after(expireDate)) {
                        //Date not expire

                        SecurityUser securityUser = securityService.getUserDetailByName(username);
                        if (SecurityContextHolder.getContext().getAuthentication() == null) {
                            if (DeuteriunJwtUtils.validateToken(token)) {
                                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(securityUser.getUsername(), null, securityUser.getAuthorities());
                                SecurityContext context = SecurityContextHolder.getContext();
                                context.setAuthentication(authentication);

                                //refresh JWT token expire date
                                String jwt = DeuteriunJwtUtils.generateJWT(authentication);
                                Result success;
                                success = Result.success(jwt, request.getServletPath());
                                ServletUtil.render(response, success);
                            }
                        }

                    } else {
                        //need relaunch
                        ServletUtil.render(request, response, Result.error(ReturnStatus.USER_NOT_LOGIN));
                        return;
                    }

                } else {
                    ServletUtil.render(request, response, Result.error(ReturnStatus.LOGOUT_SUCCESS));
                    return;
                }


            }

        }
        filterChain.doFilter(request, response);
    }


}