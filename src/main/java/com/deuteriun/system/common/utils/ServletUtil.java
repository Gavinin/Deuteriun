package com.deuteriun.system.common.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ServletUtil {

    /**
     * Get Token From Http request , with 2 side, header or address parameter
     *
     * @param request
     * @return
     */
    public static String getTokenFromHttpRequest(HttpServletRequest request) {

        String token = request.getHeader(ConstUtils.FRONT_TOKEN);
        if (StringUtils.isBlank(token)) {
            token = request.getParameter(ConstUtils.FRONT_TOKEN);
        }
        return token;
    }

    public static void render(HttpServletRequest request, HttpServletResponse response,Object data) throws IOException {
        render(response,data);
    }

    public static void render( HttpServletResponse response,Object data) throws IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Headers", "token, Accept, Origin, X-Requested-With, Content-Type, Last-Modified");
        response.setHeader("Content-type", "application/json;charset=UTF-8");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getWriter(), data);
    }
}
