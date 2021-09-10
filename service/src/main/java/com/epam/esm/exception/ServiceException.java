package com.epam.esm.exception;

import com.epam.esm.exception.exception_code.ExceptionDescription;

/**
 * Custom exception class for
 * service and validators
 * @author Lukyana I.M.
 * @version 1.0
 */
public class ServiceException extends RuntimeException {

    private String errorCode;
    private String errorReason;

    public ServiceException(ExceptionDescription exceptionDescription) {
        super();
        this.errorCode = exceptionDescription.getErrorCode();
    }

    public ServiceException(ExceptionDescription exceptionDescription, String errorReason) {
        super();
        this.errorCode = exceptionDescription.getErrorCode();
        this.errorReason = errorReason;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorReason() {
        return errorReason;
    }

    @Override
    public String toString() {
        return "errorCode=" + errorCode + '\n' +
                "reason=" + errorReason;
    }
}
