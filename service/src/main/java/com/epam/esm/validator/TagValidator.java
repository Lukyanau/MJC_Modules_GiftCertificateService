package com.epam.esm.validator;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.exception.InvalidIdException;
import com.epam.esm.exception.InvalidNameException;
import com.epam.esm.exception.exception_code.ExceptionWithCode;
import org.springframework.stereotype.Component;


import static com.epam.esm.exception.exception_code.ExceptionWithCode.*;

@Component
public class TagValidator {
    private static final TagValidator instance = new TagValidator();

    private TagValidator() {
    }

    public static TagValidator getInstance() {
        return instance;
    }

    private static final long MIN_TAG_ID = 1;
    private static final String ID_REGEX = "^[0-9]+$";
    private static final String NAME_REGEX = "^(#[a-zA-Z0-9]{1,20})$";

    public void checkTagDTOId(long id) {
        if (!isNotEmptyOrNull(String.valueOf(id)) || !String.valueOf(id).matches(ID_REGEX) || id < MIN_TAG_ID) {
            throw new InvalidIdException(INVALID_TAG_ID.getId(), INVALID_TAG_ID.name());
        }
    }

    public void checkTagDTOName(String name) {
        if (!isNotEmptyOrNull(name) || !name.matches(NAME_REGEX)) {
            throw new InvalidNameException(ExceptionWithCode.INVALID_TAG_NAME + name);
        }
    }

    private static boolean isNotEmptyOrNull(String str) {
        return str != null && !str.isEmpty();
    }

}
