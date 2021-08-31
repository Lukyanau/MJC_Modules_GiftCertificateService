package com.epam.esm.exception;

import com.epam.esm.exception.exception_code.ExceptionDescription;
import org.springframework.stereotype.Component;

@Component
public class ServiceException extends RuntimeException {
    private String errorCode;
    private String message;

    public ServiceException() {
    }

    public ServiceException(ExceptionDescription exceptionDescription) {
        super();
        this.errorCode = exceptionDescription.getId();
        this.message = exceptionDescription.getMessage();
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "errorCode='" + errorCode + '\n' +
                "message='" + message;
    }
}
