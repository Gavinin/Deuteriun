package com.deuteriun.system.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.deuteriun.system.security.entity.SecurityUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Component
public abstract class JwtUtils {
    public static String JWT_SECRET_KEY = "dh89hsd9hc9hscdug72u98mxcn39ncm34gq89nq3cg347nq";

    public static Long JWT_EXPIRE_TIME = 300000L;

    public static final String JWT_NAME_FLAG = "username";

    public static final String JWT_AUTH_FLAG = "auth";

    public static final String JWT_REFRESH_FLAG = "refresh";

    public static Long JWT_REFRESH_EXPIRE_TIME_FLAG = 300000L;

    public static String generateJWT(SecurityUser principal) {
        List<GrantedAuthority> grantedAuthorityList = principal.getGrantedAuthorityList();
        List<String> result = new LinkedList<>();
        for (GrantedAuthority grantedAuthority : grantedAuthorityList) {
            result.add(grantedAuthority.getAuthority());
        }
        Date expireDate = Date.from(LocalDateTime.now().plusSeconds(JWT_EXPIRE_TIME).atZone(ZoneId.systemDefault()).toInstant());
        Date refreshDate = Date.from(LocalDateTime.now().plusSeconds(JWT_REFRESH_EXPIRE_TIME_FLAG).atZone(ZoneId.systemDefault()).toInstant());

        try {
            return JWT.create()
                    .withExpiresAt(expireDate)
                    .withClaim(JWT_NAME_FLAG, principal.getUsername())
                    .withClaim(JWT_REFRESH_FLAG,refreshDate)
                    .withArrayClaim(JWT_AUTH_FLAG, result.toArray(new String[0]))
                    .sign(Algorithm.HMAC256(JWT_SECRET_KEY));
        } catch (JWTCreationException jwtCreationException) {
            return null;
        }

    }

    public static String generateJWT(String userName) {
        Date expireDate = Date.from(LocalDateTime.now().plusSeconds(JWT_EXPIRE_TIME).atZone(ZoneId.systemDefault()).toInstant());
        try {
            return JWT.create()
                    .withExpiresAt(expireDate)
                    .withClaim(JWT_NAME_FLAG, userName)
                    .sign(Algorithm.HMAC256(JWT_SECRET_KEY));
        } catch (JWTCreationException jwtCreationException) {
            return null;
        }
    }

    public static String generateJWT(String userName, Object data) {
        Date expireDate = new Date(System.currentTimeMillis() + JWT_EXPIRE_TIME);
        try {
            return JWT.create()
                    .withExpiresAt(expireDate)
                    .withClaim(JWT_NAME_FLAG, userName)
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

    public static Claim getClaimFromJWT(String key, String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(key);
        } catch (JWTDecodeException jwtDecodeException) {
            return null;
        }
    }


    public static String getUsernameFromJWT(String token) {
        Claim valueFromJWT = getClaimFromJWT(JWT_NAME_FLAG, token);
        return valueFromJWT == null ? null : valueFromJWT.asString();
    }
}
