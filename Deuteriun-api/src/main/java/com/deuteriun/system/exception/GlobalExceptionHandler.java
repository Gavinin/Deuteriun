package com.deuteriun.system.exception;

import com.deuteriun.common.enums.ReturnStatus;
import com.deuteriun.common.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

/**
 * @author gavin
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * NullPointerException
     * IOException
     * InsufficientAuthenticationException
     */
    @ExceptionHandler({NullPointerException.class, IOException.class, InsufficientAuthenticationException.class})
    public Result globalException(Exception e) {
        return Result.error(e.getMessage());
    }

    /**
     * JSON Pares Exception
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Result jsonError(HttpMessageNotReadableException e) {
        log.error(e.getMessage());
        return Result.error(ReturnStatus.SYSTEM_JSON_ERROR);
    }

    /**
     * Custom exception
     */
    @ExceptionHandler(BaseException.class)
    public Result unLogin(BaseException e) {
        log.warn(e.getMessage(), e.getCause());
        return Result.error(ReturnStatus.ERROR, e.getMessage());
    }

    /**
     * Other exception
     */
    @ExceptionHandler(Exception.class)
    public Result otherError(Exception e) {
        log.error(e.getMessage());
        e.printStackTrace();
        return Result.error(ReturnStatus.SYSTEM_ERROR);
    }


}
