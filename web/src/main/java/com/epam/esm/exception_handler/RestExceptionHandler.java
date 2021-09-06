package com.epam.esm.exception_handler;

import com.epam.esm.exception.ServiceException;
import com.epam.esm.translator.ExceptionMessageTranslator;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Class that handle all exception
 * in program
 * @author Lukyanai I.M.
 * @version 1.0
 */
@RestControllerAdvice
@RequiredArgsConstructor
public class RestExceptionHandler extends ResponseEntityExceptionHandler{

    private final ExceptionMessageTranslator translator;

    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * Method for handling custom exceptions
     * @param ex is object with exception description
     * @return ApiError object
     * @see ApiError
     */
    @ExceptionHandler(ServiceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError customHandle(ServiceException ex) {
        String localizedErrorMessage;
        if (ex.getErrorReason() != null) {
            localizedErrorMessage = String.format(translator.translateToLocale(ex.getErrorCode()), ex.getErrorReason());
        } else {
            localizedErrorMessage = translator.translateToLocale(ex.getErrorCode());
        }
        LOGGER.error(localizedErrorMessage);
        return new ApiError(HttpStatus.BAD_REQUEST, localizedErrorMessage, ex.getErrorCode());
    }

    /**
     * Method for handling NumberFormatException exceptions
     * @see NumberFormatException
     * @param ex is object with exception description
     * @return ApiError object
     * @see ApiError
     */
    @ExceptionHandler(NumberFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleNumberFormatException(NumberFormatException ex) {
        LOGGER.error(ex.getLocalizedMessage());
        return new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), "400");
    }

    /**
     * Method for handling IllegalArgumentException exceptions
     * @see IllegalArgumentException
     * @param ex is object with exception description
     * @return ApiError object
     * @see ApiError
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleIllegalArgumentException(IllegalArgumentException ex) {
        LOGGER.error(ex.getLocalizedMessage());
        return new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), "400");
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        LOGGER.error(ex.getLocalizedMessage());
        return new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), "400");
    }

    /**
     * Method for handling MissingServletRequestPartException exceptions
     * @see MissingServletRequestPartException
     * @param ex is object with exception description
     * @return ApiError object
     * @see ApiError
     */
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException ex,
                                                                     HttpHeaders headers,
                                                                     HttpStatus status,
                                                                     WebRequest request) {
        String error = ex.getRequestPartName() + " part is missing";
        LOGGER.error(error);
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getHttpStatus());
    }

    /**
     * Method for handling MissingServletRequestParameterException exceptions
     * @see MissingServletRequestParameterException
     * @param ex is object with exception description
     * @return ApiError object
     * @see ApiError
     */
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
                                                                          HttpHeaders headers,
                                                                          HttpStatus status,
                                                                          WebRequest request) {
        String error = ex.getParameterName() + " parameter is missing";
        LOGGER.error(error);
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getHttpStatus());
    }

    /**
     * Method for handling NoHandlerFoundException exceptions
     * @see NoHandlerFoundException
     * @param ex is object with exception description
     * @return ApiError object
     * @see ApiError
     */
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String error = "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL();
        LOGGER.error(error);
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getLocalizedMessage(), error);
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getHttpStatus());
    }

    /**
     * Method for handling other exceptions
     * @see Exception
     * @param ex is object with exception description
     * @return ApiError object
     * @see ApiError
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleAll(Exception ex) {
        LOGGER.error(ex.getLocalizedMessage());
        return new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), "400");
    }
}
