package com.deuteriun.system.jwt;
//
//import com.auth0.jwt.JWT;
//import com.auth0.jwt.JWTVerifier;
//import com.auth0.jwt.algorithms.Algorithm;
//import com.auth0.jwt.exceptions.JWTVerificationException;
//import com.auth0.jwt.interfaces.Claim;
//import com.auth0.jwt.interfaces.DecodedJWT;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StringUtils;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//@Component
//public class JwtCheckFilter extends OncePerRequestFilter {
//    @Autowired
//    private StringRedisTemplate stringRedisTemplate;
//
//    /**
//     * 每一次请求都会进行执行改过滤器
//     * 每次请求都会走这个方法
//     * jwt 从header带过来
//     * 解析jwt
//     * 设置到上下文当中去
//     * jwt 性能没有session好
//     *
//     * @param httpServletRequest
//     * @param httpServletResponse
//     * @param filterChain
//     * @throws ServletException
//     * @throws IOException
//     */
//    @Override
//    protected void doFilterInternal(HttpServletRequest httpServletRequest,
//                                    HttpServletResponse httpServletResponse,
//                                    FilterChain filterChain) throws ServletException, IOException {
//        httpServletResponse.setContentType("application/json;charset=utf-8");
//        String requestURI = httpServletRequest.getRequestURI(); // 获取请求 url 来判断是否是登录
//        String method = httpServletRequest.getMethod(); // 请求类型
//        if ("/doLogin".equals(requestURI) && "POST".equalsIgnoreCase(method)) {
//            filterChain.doFilter(httpServletRequest, httpServletResponse);
//            return;
//        }
//        // 判断是否带入了token
//        String header = httpServletRequest.getHeader(JwtConstant.AUTHORIZATION);
//        if (StringUtils.hasText(header)) {
//            // 解析真正的 token
//            header = header.replaceAll(JwtConstant.JWTTYPE, "");
//            // 判断token是否存在
//            Boolean aBoolean = stringRedisTemplate.hasKey(JwtConstant.JWT_KET + header);
//            // 校验是否过期 登出
//            if (!aBoolean) {
//                // 说明已经登出或者过期
//                httpServletResponse.getWriter().write("token过期，请重新登录!");
//                return;
//            }
//
//            // 解析验证
//            JWTVerifier build = JWT.require(Algorithm.HMAC256(JwtConstant.SIGN)).build();
//            DecodedJWT verify = null;
//            try {
//                verify = build.verify(header);
//            } catch (JWTVerificationException e) {
//                httpServletResponse.getWriter().write("token验证失败");
//                return;
//            }
//            // 拿到解析后的jwt了 后端服务器没保存用户信息 给他设置进去
//            Claim usernameClaim = verify.getClaim("username");
//            String username = usernameClaim.asString();
//            // 拿到权限
//            Claim authsClaim = verify.getClaim("authorizations");
//            List authStrs = authsClaim.asList(String.class);
//            // 转变权限信息
//            ArrayList simpleGrantedAuthorities = new ArrayList<>(authStrs.size() * 2);
//            authStrs.forEach(auth -> simpleGrantedAuthorities.add(new SimpleGrantedAuthority(auth)));
//            // 变成security认识的对象`
//            // 参数一： 用户名称
//            // 参数二：密码
//            // 参数三：权限标识
//            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, simpleGrantedAuthorities);
//            SecurityContextHolder.getContext().setAuthentication(authenticationToken); // 重新存储到Security当中
//            filterChain.doFilter(httpServletRequest, httpServletResponse); // 放行
//            return;
//        }
//        httpServletResponse.getWriter().write("token验证失败.");
//        return;
//    }
//}