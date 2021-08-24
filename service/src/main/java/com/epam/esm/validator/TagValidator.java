package com.epam.esm.validator;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.exception.InvalidIdException;
import com.epam.esm.exception.InvalidNameException;
import com.epam.esm.exception.exception_code.ExceptionWithCode;

public class TagValidator {
    private static final TagValidator instance = new TagValidator();

    private TagValidator() {
    }

    public static TagValidator getInstance(){
        return instance;
    }

    private static final long MIN_TAG_ID = 1;
    private static final String ID_REGEX = "^[0-9]+$";
    private static final String NAME_REGEX = "^(#[a-zA-Z0-9]{1,20})$";

    public void validateTagDTO(TagDTO tagDTO) throws InvalidIdException, InvalidNameException {
         checkTagDTOId(tagDTO.getId());
         checkTagDTOName(tagDTO.getName());
    }

    public void checkTagDTOId(long id) throws InvalidIdException {
        if (!notEmptyOrNull(String.valueOf(id)) && String.valueOf(id).matches(ID_REGEX) && id>=MIN_TAG_ID) {
            throw new InvalidIdException(ExceptionWithCode.INVALID_TAG_ID.toString());
        }
    }

    public void checkTagDTOName(String name) throws InvalidNameException {
         if(!notEmptyOrNull(name) && name.matches(NAME_REGEX)){
             throw new InvalidNameException(ExceptionWithCode.INVALID_TAG_NAME + name);
         }
    }

    private static boolean notEmptyOrNull(String str) {
        return str != null && !str.isEmpty();
    }

}
