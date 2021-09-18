package com.epam.esm.validator;

import com.epam.esm.exception.ServiceException;
import org.springframework.stereotype.Component;

import static com.epam.esm.exception.exception_code.ExceptionDescription.*;

@Component
public class BaseValidator {

    private static final long MIN_ID = 1;
    private static final String ID_REGEX = "^[0-9]+$";

    public void checkId(Long id) {
        if (id == null || !id.toString().matches(ID_REGEX) || id < MIN_ID) {
            throw new ServiceException(INVALID_ID, String.valueOf(id).trim());
        }
    }

}
