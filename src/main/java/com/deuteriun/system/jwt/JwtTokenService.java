package com.deuteriun.system.jwt;

import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.core.Authentication;

public interface JwtTokenService {

    String generateToken(Authentication authentication);

    String generateToken(String userName);

    Boolean validateToken(String token);

    String getUsernameFromJWT(String token);

    Algorithm getDecoder();
}
