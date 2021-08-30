package com.epam.esm.exception_handler;

import com.epam.esm.exception.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler{

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<String> handleInvalidIdException(ServiceException exception){
        return new ResponseEntity<>(exception.toString(), HttpStatus.valueOf(exception.getErrorCode()));
    }

}
