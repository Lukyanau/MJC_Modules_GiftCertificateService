package com.epam.esm.exception_handler;

import com.epam.esm.exception.ServiceException;
import com.epam.esm.translator.ExceptionMessageTranslator;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
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
 *
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
     *
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
        return new ApiError(localizedErrorMessage, ex.getErrorCode());
    }

    /**
     * Method for handling InvalidFormatException
     *
     * @param ex is object with exception description
     * @return ApiError object
     * @see InvalidFormatException
     * @see ApiError
     */
    @ExceptionHandler(InvalidFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleInvalidFormatException(InvalidFormatException ex) {
        String localizedErrorMessage;
        localizedErrorMessage = translator.translateToLocale("INVALID_FORMAT");
        LOGGER.error(ex.getLocalizedMessage());
        return new ApiError(localizedErrorMessage, "400");
    }


    /**
     * Method for handling NumberFormatException exceptions
     *
     * @param ex is object with exception description
     * @return ApiError object
     * @see NumberFormatException
     * @see ApiError
     */
    @ExceptionHandler(NumberFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleNumberFormatException(NumberFormatException ex) {
        String localizedErrorMessage;
        localizedErrorMessage = translator.translateToLocale("NUMBER_FORMAT");
        LOGGER.error(ex.getLocalizedMessage());
        return new ApiError(localizedErrorMessage, "400");
    }

    /**
     * Method for handling IllegalArgumentException exceptions
     *
     * @param ex is object with exception description
     * @return ApiError object
     * @see IllegalArgumentException
     * @see ApiError
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleIllegalArgumentException(IllegalArgumentException ex) {
        String localizedErrorMessage;
        localizedErrorMessage = translator.translateToLocale("ILLEGAL_ARGUMENT");
        LOGGER.error(ex.getLocalizedMessage());
        return new ApiError(localizedErrorMessage, "400");
    }
//
    /**
     * Method for handling IllegalArgumentException exceptions
     *
     * @param ex is object with exception description
     * @return ApiError object
     * @see MethodArgumentTypeMismatchException
     * @see ApiError
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        String localizedErrorMessage;
        localizedErrorMessage = String.format(translator.translateToLocale("TYPE_MISMATCH"), ex.getRequiredType());
        LOGGER.error(ex.getLocalizedMessage());
        return new ApiError(localizedErrorMessage, "400");
    }

    /**
     * Method for handling MissingServletRequestPartException exceptions
     *
     * @param ex is object with exception description
     * @return ApiError object
     * @see MissingServletRequestPartException
     * @see ApiError
     */
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException ex,
                                                                     HttpHeaders headers,
                                                                     HttpStatus status,
                                                                     WebRequest request) {
        String localizedErrorMessage;
        localizedErrorMessage = String.format(translator.
                translateToLocale("MISSING_REQUEST_PART"), ex.getRequestPartName());
        ApiError apiError = new ApiError(localizedErrorMessage, "400");
        LOGGER.error(localizedErrorMessage);
        return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
                                                                         HttpHeaders headers, HttpStatus status,
                                                                         WebRequest request) {
        String localizedErrorMessage;
        localizedErrorMessage = String.format(translator.
                translateToLocale("METHOD_NOT_SUPPORTED"), ex.getMethod());
        ApiError apiError = new ApiError(localizedErrorMessage, "400");
        LOGGER.error(localizedErrorMessage);
        return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Method for handling MissingServletRequestParameterException exceptions
     *
     * @param ex is object with exception description
     * @return ApiError object
     * @see MissingServletRequestParameterException
     * @see ApiError
     */
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
                                                                          HttpHeaders headers,
                                                                          HttpStatus status,
                                                                          WebRequest request) {
        String localizedErrorMessage;
        localizedErrorMessage = String.format(translator.
                translateToLocale("PARAMETER_MISSING"), ex.getParameterName());
        ApiError apiError = new ApiError(localizedErrorMessage, "400");
        LOGGER.error(localizedErrorMessage);
        return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Method for handling NoHandlerFoundException exceptions
     *
     * @param ex is object with exception description
     * @return ApiError object
     * @see NoHandlerFoundException
     * @see ApiError
     */
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String localizedErrorMessage;
        localizedErrorMessage = String.format(translator.
                translateToLocale("NO_HANDLER_FOUND"), ex.getHttpMethod() + " " + ex.getRequestURL());
        ApiError apiError = new ApiError(localizedErrorMessage, "400");
        LOGGER.error(localizedErrorMessage);
        return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Method for handling HttpMessageNotReadableException
     *
     * @param ex is object with exception description
     * @return ApiError object
     * @see HttpMessageNotReadableException
     * @see ApiError
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        String localizedErrorMessage;
        localizedErrorMessage = translator.translateToLocale("MESSAGE_NOT_READABLE");
        ApiError apiError = new ApiError(localizedErrorMessage, "400");
        LOGGER.error(localizedErrorMessage);

        return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Method for handling other exceptions
     *
     * @param ex is object with exception description
     * @return ApiError object
     * @see Exception
     * @see ApiError
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleAll(Exception ex) {
        String localizedErrorMessage;
        localizedErrorMessage = translator.translateToLocale("HANDLE_ALL");
        LOGGER.error(ex.getLocalizedMessage());
        return new ApiError(localizedErrorMessage, "400");
    }
}
