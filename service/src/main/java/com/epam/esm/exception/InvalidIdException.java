package com.epam.esm.exception;

import com.epam.esm.exception.exception_code.ExceptionWithCode;

public class InvalidIdException extends Exception {
    public InvalidIdException() {
    }

    public InvalidIdException(String message) {
        super(message);
    }

    public InvalidIdException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidIdException(Throwable cause) {
        super(cause);
    }


}
