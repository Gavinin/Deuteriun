package com.deuteriun.system.exception;

public class UserException extends BaseException{
    public UserException() {
        super();
    }

    public UserException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserException(String message) {
        super(message);
    }

    public UserException(Throwable cause) {
        super(cause);
    }
}
