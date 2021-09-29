package com.epam.esm.validator;

import com.epam.esm.exception.ServiceException;
import org.springframework.stereotype.Component;

import static com.epam.esm.exception.exception_code.ExceptionDescription.INVALID_TAG_NAME;

/**
 * Validator for tags from request
 *
 * @author Lukyanau I.M.
 * @version 1.0
 */
@Component
public class TagValidator {

  private static final String NAME_REGEX = "^(.{3,50})$";

  public void checkTagDtoName(String name) {
    if (!isNull(name) && !name.isEmpty() && !name.trim().matches(NAME_REGEX)) {
      throw new ServiceException(INVALID_TAG_NAME, name.trim());
    }
  }

  private boolean isNull(String str) {
    return str == null;
  }
}
