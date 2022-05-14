package com.deuteriun.system.exception;

import com.deuteriun.common.enums.ReturnStatus;
import com.deuteriun.common.utils.Result;
import com.deuteriun.system.exception.BaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);

    //空指针异常
    @ExceptionHandler(NullPointerException.class)
    public Result nullPoint(NullPointerException e, HttpServletRequest req) {
        logger.error(e.getMessage());
        return Result.error(ReturnStatus.SYSTEM_NULL_POINT);
    }

    //IO异常
    @ExceptionHandler(IOException.class)
    public Result IOException(IOException e, HttpServletRequest req, HttpServletResponse res) {
        logger.error(e.getMessage());
        return Result.error(ReturnStatus.SYSTEM_NULL_POINT);
    }

    //权限异常
    @ExceptionHandler(AccessDeniedException.class)
    public Result IOException(AccessDeniedException e, HttpServletRequest req, HttpServletResponse res) {
        logger.error(e.getMessage());
        return Result.error(ReturnStatus.AUTHORITY_UNAUTHORIZED);
    }

    @ExceptionHandler(InsufficientAuthenticationException.class)
    public Result handleAuthenticationException(AuthenticationException e, HttpServletResponse response) {
        logger.error(e.getMessage());
        return Result.error(ReturnStatus.USER_NOT_LOGIN);
    }


    //JSON转换异常
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Result jsonError(HttpMessageNotReadableException e, HttpServletRequest req, HttpServletResponse res) {
        logger.error(e.getMessage());
        return Result.error(ReturnStatus.SYSTEM_JSON_ERROR);
    }

    //自定义异常
    @ExceptionHandler(BaseException.class)
    public Result unLogin(BaseException e, HttpServletRequest req, HttpServletResponse res) {
        logger.warn(e.getMessage(), e.getCause());
        return Result.error(ReturnStatus.ERROR, e.getMessage());
    }

    //其它异常
    @ExceptionHandler(Exception.class)
    public Result systemError(Exception e, HttpServletRequest req, HttpServletResponse res) {
        logger.error(e.getMessage());
        e.printStackTrace();
        return Result.error(ReturnStatus.SYSTEM_ERROR);
    }


}
