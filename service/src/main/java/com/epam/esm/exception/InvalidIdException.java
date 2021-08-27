package com.epam.esm.exception;

import com.epam.esm.exception.exception_code.ExceptionWithCode;
import org.springframework.stereotype.Component;

@Component
public class InvalidIdException extends RuntimeException {
    private String errorCode;
    private String message;

    public InvalidIdException() {
    }

    public InvalidIdException(String errorCode, String message) {
        super();
        this.errorCode = errorCode;
        this.message = message;
    }

    public InvalidIdException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidIdException(Throwable cause) {
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
