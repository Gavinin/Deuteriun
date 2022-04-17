package com.deuteriun.system.security.filter;

import com.deuteriun.system.common.enums.ReturnStatus;
import com.deuteriun.system.common.utils.ConstConfigure;
import com.deuteriun.system.common.utils.Result;
import com.deuteriun.system.common.utils.ServletUtil;
import com.deuteriun.system.jwt.JwtTokenService;
import com.deuteriun.system.security.entity.SecurityUser;
import com.deuteriun.system.security.service.SecurityService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SecurityAuthTokenFilter extends BasicAuthenticationFilter {

    @Resource
    JwtTokenService jwtTokenUtils;

    @Resource
    SecurityService securityService;

    @Resource
    ConstConfigure constConfigure;

    public SecurityAuthTokenFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    /**
     * Modify Filter
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getTokenFromHttpRequest(request);
        if (StringUtils.isNotBlank(token)) {
            String username = jwtTokenUtils.getUsernameFromJWT(token);
            SecurityUser securityUser = securityService.getUserDetailByName(username);
            if (securityUser.getUsername() == null) {
                ServletUtil.render(request,response,Result.error(ReturnStatus.USER_ACCOUNT_NOT_EXIST));
                return;
            }
            if (StringUtils.isNotBlank(securityUser.getUsername()) && SecurityContextHolder.getContext().getAuthentication() == null) {
                if (!jwtTokenUtils.validateToken(token)) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(securityUser.getUsername(), null, securityUser.getAuthorities());
                    SecurityContext context = SecurityContextHolder.getContext();
                    context.setAuthentication(authentication);
                }
            }
        }
        // 继续下一个过滤器
        filterChain.doFilter(request, response);
    }

    /**
     * Get Token From Http request , with 2 side, header or address parameter
     * @param request
     * @return
     */
    public String getTokenFromHttpRequest(HttpServletRequest request) {
        String token = request.getHeader(constConfigure.getToken());
        if (StringUtils.isBlank(token)) {
            token = request.getParameter(constConfigure.getToken());
        }
        return token;
    }
}