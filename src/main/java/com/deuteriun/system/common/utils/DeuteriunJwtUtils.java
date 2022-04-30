package com.deuteriun.system.common.utils;


import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.deuteriun.system.jwt.JwtUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class DeuteriunJwtUtils extends JwtUtils {

    public static List<String> getAuthFromJWT(String token) {
        Claim valueFromJWT = JwtUtils.getClaimFromJWT(JWT_AUTH_FLAG, token);
        return valueFromJWT == null ? null : valueFromJWT.asList(String.class);
    }


    public static String getUsernameFromJWT(String token) {
        return getValueFromJWT(token, JWT_NAME_FLAG);
    }

    public static String getValueFromJWT(String token, String key) {
        Claim valueFromJWT = JwtUtils.getClaimFromJWT(key, token);
        return valueFromJWT == null ? null : valueFromJWT.asString();
    }

    public static Date getExpireDate(String token) {
        return JWT.decode(token).getExpiresAt();
    }
}
