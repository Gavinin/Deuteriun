package com.deuteriun.system.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.deuteriun.system.security.entity.SecurityUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenUtils  {

    public static String JWT_SECRET_KEY = "dh89hsd9hc9hscdug72u98mxcn39ncm34gq89nq3cg347nq";

    private static Long JWT_EXPIRE_TIME = 300000L;

    public static String JWT_NAME = "username";

    @Value("${deuteriun.jwt-secret-key}")
    public static void setJwtSecretKey(String jwtSecretKey) {
        JWT_SECRET_KEY = jwtSecretKey;
    }

    @Value("${deuteriun.jwt-expire-time}")
    public static void setJwtExpireTime(Long jwtExpireTime) {
        JWT_EXPIRE_TIME = jwtExpireTime * 60 * 1000;
    }

    @Value("${deuteriun.jwt-name}")
    public static void setJwtName(String jwtName) {
        JWT_NAME = jwtName;
    }

    public static String generateToken(Authentication authentication) {
        SecurityUser userPrincipal = (SecurityUser) authentication.getPrincipal();
        return generateToken(userPrincipal.getUsername());
    }

    public static String generateToken(String userName) {
        Date expireDate = new Date(System.currentTimeMillis() + JWT_EXPIRE_TIME);
        try {
            return JWT.create()
                    .withExpiresAt(expireDate)
                    .withClaim(JWT_NAME, userName)
                    .sign(Algorithm.HMAC256(JWT_SECRET_KEY));
        } catch (JWTCreationException jwtCreationException) {
            return null;
        }
    }

    public static Boolean validateToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(JWT_SECRET_KEY)).build();
            verifier.verify(token);
            return true;
        } catch (JWTVerificationException jwtVerificationException) {
            return false;
        }
    }


    public static String getUsernameFromJWT(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(JWT_NAME).asString();
        } catch (JWTDecodeException jwtDecodeException) {
            return null;
        }
    }
}
