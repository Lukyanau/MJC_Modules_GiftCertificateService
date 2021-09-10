package com.epam.esm.exception_handler;

import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * Class that return object for
 * handling exceptions
 * @author Lukyanau I.M.
 * @version 1.0
 */
@Data
public class ApiError {

    private final String errorMessage;
    private final String errorCode;

}
