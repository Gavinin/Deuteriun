package com.deuteriun.system.exception;

/**
 * @author gavin
 */
public class SystemException extends BaseException {
    public SystemException() {
        super();
    }

    public SystemException(String message, Throwable cause) {
        super(message, cause);
    }

    public SystemException(String message) {
        super(message);
    }

    public SystemException(Throwable cause) {
        super(cause);
    }
}
