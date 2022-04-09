//package com.deuteriun.system.controller;
//
//import com.deuteriun.system.common.enums.ReturnStatus;
//import com.deuteriun.system.common.utils.ResultUtils;
//import com.deuteriun.system.entity.Result;
//import com.deuteriun.system.common.utils.StringUtils;
//import com.deuteriun.system.entity.LoginInfoDTO;
//import com.deuteriun.system.jwt.JwtTokenUtils;
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
//        if (StringUtils.isEmpty(loginInfoDTO.getUsername()) || StringUtils.isEmpty(loginInfoDTO.getPassword())) {
//            return ResultUtils.error(ReturnStatus.USER_PASSWORD_ERROR);
//        }
//        try {
//
//            return ResultUtils.success(jwtTokenUtils.generateToken(lo));
//        } catch (Exception e) {
//            return ResultUtils.error(e.toString());
//        }
//    }
//
//    @PostMapping(value = "/refreshtoken")
//    public Result refresh(@RequestHeader("${jwt.header}") String token) {
//        return ResultUtils.success(jwtTokenUtils.generateToken(token));
//    }
//
//
//}
