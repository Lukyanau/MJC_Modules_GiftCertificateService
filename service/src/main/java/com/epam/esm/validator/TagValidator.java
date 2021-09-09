package com.epam.esm.validator;

import com.epam.esm.exception.ServiceException;
import org.springframework.stereotype.Component;

import static com.epam.esm.exception.exception_code.ExceptionDescription.INVALID_TAG_ID;
import static com.epam.esm.exception.exception_code.ExceptionDescription.INVALID_TAG_NAME;

/**
 * Validator for tags from request
 * @author Lukyanau I.M.
 * @version 1.0
 */
@Component
public class TagValidator {

    private static final long MIN_TAG_ID = 1;
    private static final String ID_REGEX = "^[0-9]+$";
    private static final String NAME_REGEX = "^(.{3,50})$";

    public void checkTagDtoId(Long id) {
        if (id == null || !id.toString().matches(ID_REGEX) || id < MIN_TAG_ID) {
            throw new ServiceException(INVALID_TAG_ID, String.valueOf(id));
        }
    }

    public void checkTagDtoName(String name) {
        if (isEmptyOrNull(name) || !name.trim().matches(NAME_REGEX)) {
            throw new ServiceException(INVALID_TAG_NAME, name.trim());
        }
    }

    private static boolean isEmptyOrNull(String str) {
        return str == null || str.isEmpty();
    }

}
