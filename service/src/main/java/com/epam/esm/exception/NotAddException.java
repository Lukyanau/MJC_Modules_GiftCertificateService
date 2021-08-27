package com.epam.esm.exception;

import org.springframework.stereotype.Component;

@Component
public class NotAddException extends RuntimeException {
    private String errorCode;
    private String message;

    public NotAddException() {
    }

    public NotAddException(String errorCode, String message) {
        super();
        this.errorCode = errorCode;
        this.message = message;
    }

    public NotAddException(String message) {
        super(message);
    }

    public NotAddException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotAddException(Throwable cause) {
        super(cause);
    }

    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
