package com.epam.esm.validator;

import com.epam.esm.exception.ServiceException;
import org.springframework.stereotype.Component;

import static com.epam.esm.exception.exception_code.ExceptionDescription.*;

@Component
public class UserValidator {

    private static final long MIN_USER_ID = 1;
    private static final String ID_REGEX = "^[0-9]+$";

}

