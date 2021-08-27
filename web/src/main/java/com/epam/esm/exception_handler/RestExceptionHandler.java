package com.epam.esm.exception_handler;

import com.epam.esm.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler{

    @ExceptionHandler(IncorrectInputParametersException.class)
    public ResponseEntity<String> handleInvalidIdException(IncorrectInputParametersException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.valueOf(exception.getErrorCode()));
    }

    @ExceptionHandler(InvalidIdException.class)
    public ResponseEntity<String> handleInvalidIdException(InvalidIdException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.valueOf(exception.getErrorCode()));
    }

    @ExceptionHandler(InvalidNameException.class)
    public ResponseEntity<String> handleInvalidIdException(InvalidNameException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.valueOf(exception.getErrorCode()));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleInvalidIdException(NotFoundException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.valueOf(exception.getErrorCode()));
    }

    @ExceptionHandler(NotAddException.class)
    public ResponseEntity<String> handleInvalidIdException(NotAddException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.valueOf(exception.getErrorCode()));
    }

}
