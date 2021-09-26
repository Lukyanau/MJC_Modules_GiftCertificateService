package com.epam.esm.exception;

import com.epam.esm.exception.exception_code.ExceptionDescription;

import java.util.List;

/**
 * Custom exception class for
 * service and validators
 *
 * @author Lukyana I.M.
 * @version 1.0
 */
public class ServiceException extends RuntimeException {

    private String errorCode;
    private String errorReason;
    private List<ExceptionDescription> errorCodes;

    public ServiceException(ExceptionDescription exceptionDescription) {
        super();
        this.errorCode = exceptionDescription.getErrorCode();
        System.out.println(errorCode);
    }

    public ServiceException(List<ExceptionDescription> errorCodes) {
        super();
        this.errorCodes = errorCodes;
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

    public List<ExceptionDescription> getErrorCodes() {
        return errorCodes;
    }

    @Override
    public String toString() {
        return "ServiceException" +
                "errorCode='" + errorCode + '\'' +
                ", errorReason='" + errorReason + '\'' +
                ", errorCodes=" + errorCodes + super.toString();
    }
}
