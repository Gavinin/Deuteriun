package com.deuteriun.system.utils;


import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;

/**
 * @author gavin
 */
@Component
public class JwtUtils extends AbstractJwtUtils {

    @Value("${deuteriun.jwt.secret-key}")
    private String jwtSecretKey;

    @Value("${deuteriun.jwt.expire-time}")
    private Long jwtExpireTime;

    @Value("${deuteriun.jwt.refresh-expire-time}")
    private Long jwtRefreshExpireTimeFlag;

    @PostConstruct
    public void postConstruct() {
        JWT_SECRET_KEY = jwtSecretKey;
        JWT_EXPIRE_TIME = jwtExpireTime * 60 * 1000;
        JWT_REFRESH_EXPIRE_TIME_FLAG = jwtRefreshExpireTimeFlag * 60 * 1000;
    }

    public static List<String> getAuth(String token) {
        Claim valueFromJwt = AbstractJwtUtils.getClaimFromJwt(JWT_AUTH_FLAG, token);
        return valueFromJwt == null ? null : valueFromJwt.asList(String.class);
    }

    public static String getUsername(String token) {
        return getValue(token, JWT_USER_NAME_FLAG);
    }

    public static Long getUserId(String token) {
        Claim claimFromJwt = AbstractJwtUtils.getClaimFromJwt(JWT_USER_NAME_ID, token);
        return claimFromJwt == null ? null : claimFromJwt.asLong();
    }

    public static String getValue(String token, String key) {
        Claim valueFromJwt = AbstractJwtUtils.getClaimFromJwt(key, token);
        return valueFromJwt == null ? null : valueFromJwt.asString();
    }

    public static Date getExpireDate(String token) {
        return JWT.decode(token).getExpiresAt();
    }

    public static Date getRefreshDate(String token) {
        return AbstractJwtUtils.getClaimFromJwt(JWT_REFRESH_FLAG, token).asDate();
    }

}
