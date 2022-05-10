package com.deuteriun.system.exception;

public class SysException extends BaseException{
    public SysException() {
        super();
    }

    public SysException(String message, Throwable cause) {
        super(message, cause);
    }

    public SysException(String message) {
        super(message);
    }

    public SysException(Throwable cause) {
        super(cause);
    }
}
