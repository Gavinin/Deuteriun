package com.deuteriun.system.security.conf;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class AuthenticateSuccessImpl implements AuthenticationSuccessHandler {


    /**
     * @param request
     * @param response
     * @param authentication
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // Create JSON
        response.setContentType("application/json;charset=utf-8");
        Map<Object, Object> map = new HashMap<>(8);
        map.put("code", 200);
        map.put("msg", "登录成功");
        map.put("data", authentication);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonStr = objectMapper.writeValueAsString(map);
        // Create output stream
        PrintWriter writer = response.getWriter();
        writer.write(jsonStr);
        // refresh stream and close
        writer.flush();
        writer.close();
    }
}