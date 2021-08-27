package com.epam.esm.exception;

import org.springframework.stereotype.Component;

@Component
public class NotFoundException extends RuntimeException {
    private String errorCode;
    private String message;

    public NotFoundException() {
    }

    public NotFoundException(String errorCode, String message) {
        super();
        this.errorCode = errorCode;
        this.message = message;
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundException(Throwable cause) {
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
