package com.epam.esm.exception;

import org.springframework.stereotype.Component;

@Component
public class IncorrectInputParametersException extends RuntimeException{
    private String errorCode;
    private String message;
    public IncorrectInputParametersException() {
    }

    public IncorrectInputParametersException(String errorCode, String message){
        super();
        this.errorCode = errorCode;
        this.message = message;
    }
    public IncorrectInputParametersException(String message) {
        super(message);
    }

    public IncorrectInputParametersException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectInputParametersException(Throwable cause) {
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
