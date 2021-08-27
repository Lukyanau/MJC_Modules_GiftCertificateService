package com.epam.esm.exception;

import org.springframework.stereotype.Component;

@Component
public class InvalidNameException extends RuntimeException{
    private String errorCode;
    private String message;
    public InvalidNameException() {
    }

    public InvalidNameException(String errorCode, String message) {
        super();
        this.errorCode = errorCode;
        this.message = message;
    }

    public InvalidNameException(String message) {
        super(message);
    }

    public InvalidNameException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidNameException(Throwable cause) {
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
