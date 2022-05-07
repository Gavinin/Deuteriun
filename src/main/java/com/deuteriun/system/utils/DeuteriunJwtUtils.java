package com.deuteriun.system.utils;


import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.deuteriun.system.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;

@Component
public class DeuteriunJwtUtils extends JwtUtils {

    @Value("${deuteriun.jwt-secret-key}")
    private String jwtSecretKey;

    @Value("${deuteriun.jwt-expire-time}")
    private Long jwtExpireTime;

    @Value("${deuteriun.jwt-refresh-expire-time}")
    private Long jwtRefreshExpireTimeFlag;

    @PostConstruct
    public void postConstruct() {
        JWT_SECRET_KEY = jwtSecretKey;
        JWT_EXPIRE_TIME = jwtExpireTime * 60 * 1000;
        JWT_REFRESH_EXPIRE_TIME_FLAG = jwtRefreshExpireTimeFlag * 60 * 1000;
    }

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

    public static Date getRefreshDate(String token) {
        return JwtUtils.getClaimFromJWT(JWT_REFRESH_FLAG, token).asDate();
    }
}
