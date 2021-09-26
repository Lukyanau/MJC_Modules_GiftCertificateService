package com.epam.esm.validator;

import com.epam.esm.exception.ServiceException;
import org.springframework.stereotype.Component;

import static com.epam.esm.exception.exception_code.ExceptionDescription.INVALID_ID;
import static com.epam.esm.exception.exception_code.ExceptionDescription.INVALID_PAGINATION_PARAM;

@Component
public class BaseValidator {

    private static final long MIN_ID = 1;
    private static final long MIN_PAGE = 1;
    private static final long MIN_SIZE = 1;

    public void checkId(Long id) {
        if (isNull(id) || id < MIN_ID) {
            throw new ServiceException(INVALID_ID, String.valueOf(id).trim());
        }
    }

    public void checkPaginationParams(Integer page, Integer size) {
        if (isNull(page) || isNull(size) || page < MIN_PAGE || size < MIN_SIZE) {
            throw new ServiceException(INVALID_PAGINATION_PARAM);
        }
    }

    private boolean isNull(Object obj) {
        return obj == null;
    }
}
