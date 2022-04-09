package com.deuteriun.system.jwt;

import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;

public abstract class JwtTokenProvider implements JwtTokenInterface {

    @Value("deuteriun.jwt-secret-key")
    protected static String jwtSecretKey;

    public Long getJwtExpiration() {
        return 5 * 60 * 1000L;
    }

    public String getTokenPrefix() {

        return "Bearer";
    }

    /**
     * Recommend Override this method
     *
     * @return SecreKey
     */
    public String getJwtSecretKey() {
        if (null == jwtSecretKey) {
            jwtSecretKey = "ChangeThisKey";
        }
        return jwtSecretKey;
    }

    public String generateToken(Authentication authentication) {
        return null;
    }

    public String generateToken(String userName) {
        return null;
    }

    public Boolean validateToken(String token) {
        return null;
    }

    public String getUsernameFromJWT(String token) {
        return null;
    }

    /**
     * Default Encoder is H256
     *
     * @return H256 Encoder
     */
    public Algorithm getDecoder() {
        return Algorithm.HMAC256(getJwtSecretKey());
    }
}
