//package com.deuteriun.system.controller;
//
//import com.deuteriun.system.entity.LoginInfoDTO;
//import com.deuteriun.system.common.utils.Result;
//import com.deuteriun.system.jwt.JwtTokenUtils;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestHeader;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.annotation.Resource;
//
//@RestController("/jauth")
//public class JwtAuthenticationController {
//
//    @Resource
//    JwtTokenUtils jwtTokenUtils;
//
//    @PostMapping(value = "/login")
//    public Result login(@RequestBody LoginInfoDTO loginInfoDTO) {
////        if (StringUtils.isEmpty(loginInfoDTO.getUsername()) || StringUtils.isEmpty(loginInfoDTO.getPassword())) {
////            return Result.error(ReturnStatus.USER_PASSWORD_ERROR);
////        }
////        try {
////
////            return Result.success(jwtTokenUtils.generateToken(lo));
////        } catch (Exception e) {
////            return Result.error(e.toString());
////        }
//        return Result.success("");
//    }
//
//    @Resource
//    ObjectMapper objectMapper;
//
//    @PostMapping(value = "/refreshtoken")
//    public Result refresh(@RequestHeader("${jwt.header}") String token) {
//
//        return Result.success(jwtTokenUtils.generateToken(token));
//    }
//
//
//}
