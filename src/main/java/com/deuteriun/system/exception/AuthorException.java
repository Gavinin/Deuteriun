package com.deuteriun.system.exception;

/**
 * @ClassName AuthorException
 * @Description TODO
 * @Author gavin
 * @Date 9/5/2022 03:29
 * @Version 1.0
 **/
public class AuthorException extends BaseException {
    public AuthorException() {
        super();
    }

    public AuthorException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthorException(String message) {
        super(message);
    }

    public AuthorException(Throwable cause) {
        super(cause);
    }
}
