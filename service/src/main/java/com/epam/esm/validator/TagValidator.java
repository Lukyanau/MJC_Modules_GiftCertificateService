package com.epam.esm.validator;

import com.epam.esm.exception.ServiceException;
import org.springframework.stereotype.Component;

import static com.epam.esm.exception.exception_code.ExceptionDescription.INVALID_TAG_ID;
import static com.epam.esm.exception.exception_code.ExceptionDescription.INVALID_TAG_NAME;

@Component
public class TagValidator {

    private static final long MIN_TAG_ID = 1;
    private static final String ID_REGEX = "^[0-9]+$";
    private static final String NAME_REGEX = "^(#[a-zA-Z0-9]{3,20})$";

    public void checkTagDTOId(long id) {
        if (!isNotEmptyOrNull(String.valueOf(id)) || !String.valueOf(id).matches(ID_REGEX) || id < MIN_TAG_ID) {
            throw new ServiceException(INVALID_TAG_ID);
        }
    }

    public void checkTagDTOName(String name) {
        if (!isNotEmptyOrNull(name) || !name.matches(NAME_REGEX)) {
            throw new ServiceException(INVALID_TAG_NAME);
        }
    }

    private static boolean isNotEmptyOrNull(String str) {
        return str != null && !str.isEmpty();
    }

}
