package com.deuteriun.system.exception;

/**
 * @ClassName FileException
 * @Description TODO
 * @Author gavin
 * @Date 9/5/2022 21:35
 * @Version 1.0
 **/
public class FileException extends BaseException{
    public FileException() {
        super();
    }

    public FileException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileException(String message) {
        super(message);
    }

    public FileException(Throwable cause) {
        super(cause);
    }
}
