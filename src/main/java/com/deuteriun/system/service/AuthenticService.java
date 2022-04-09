package com.deuteriun.system.service;

import com.deuteriun.system.jwt.JwtTokenUtils;

import javax.annotation.Resource;

public interface AuthenticService {

//    @Resource
//    JwtTokenUtils jwtTokenUtils = null;

//    public String login(String username, String password) throws CustomException {
//        try{
//            //使用用户名密码进行登录验证
//            UsernamePasswordAuthenticationToken upToken =
//                    new UsernamePasswordAuthenticationToken( username, password );
//            Authentication authentication = authenticationManager.authenticate(upToken);
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//        }catch(AuthenticationException e){
//            throw new CustomException(CustomExceptionType.USER_INPUT_ERROR,
//                    "用户名或密码不正确");
//        }
//
//        //生成JWT
//        UserDetails userDetails = userDetailsService.loadUserByUsername( username );
//        return jwtTokenUtil.generateToken(userDetails);
//    }
//
//    public String refreshToken(String oldToken) {
//        if (!jwtTokenUtil.isTokenExpired(oldToken)) {
//            return jwtTokenUtil.refreshToken(oldToken);
//        }
//        return null;
//    }
}
