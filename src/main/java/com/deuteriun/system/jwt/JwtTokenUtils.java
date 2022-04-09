package com.deuteriun.system.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenUtils extends JwtTokenProvider {

    public String generateToken(Authentication authentication) {
        User userPrincipal = (User) authentication.getPrincipal();
        return generateToken(userPrincipal.getUsername());
    }

    public String generateToken(String userName) {
        Date expireDate = new Date(System.currentTimeMillis() + getJwtExpiration());
        try {
            // Sign JWT
            return JWT.create().withExpiresAt(expireDate).withClaim(JWT_NAME, userName)
                    .sign(getDecoder());
        } catch (JWTCreationException jwtCreationException) {
            return null;
        }
    }

    public Boolean validateToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(getDecoder()).build();
            verifier.verify(token);
            return true;
        } catch (JWTVerificationException jwtVerificationException) {
            return false;
        }
    }


    public String getUsernameFromJWT(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(JWT_NAME).asString();
        } catch (JWTDecodeException jwtDecodeException) {
            return null;
        }
    }
}
