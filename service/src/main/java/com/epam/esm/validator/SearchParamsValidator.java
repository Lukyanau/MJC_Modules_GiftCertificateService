package com.epam.esm.validator;

import com.epam.esm.exception.ServiceException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.epam.esm.exception.exception_code.ExceptionDescription.*;
import static com.epam.esm.utils.CertificateSearchParameters.*;

/**
 * Validator for search params from request
 *
 * @author Lukyanau I.M.
 * @version 1.0
 */
@Component
public class SearchParamsValidator {
    private static final String ORDER_BY_NAME = "name";
    private static final String ORDER_BY_CREATED = "created";
    private static final String SORT_ASC = "asc";
    private static final String SORT_DESC = "desc";
    private static final String PARAMETER_REGEX = "^(.{3,50})$";


    public void checkSearchParams(Map<String, String> searchParams) {
        validateSearchMapKeys(searchParams.keySet());
        searchParams.values().forEach(param -> {
            if (isEmptyOrNull(param.trim()) || !param.trim().matches(PARAMETER_REGEX)) {
                throw new ServiceException(INVALID_SEARCH_PARAMETER_VALUE);
            }
        });
        if (searchParams.containsKey(ORDER_BY)) {
            if (!searchParams.get(ORDER_BY).equalsIgnoreCase(ORDER_BY_NAME) &&
                    !searchParams.get(ORDER_BY).equalsIgnoreCase(ORDER_BY_CREATED)) {
                throw new ServiceException(INVALID_ORDER_BY, searchParams.get(ORDER_BY));
            }
        }
        if (searchParams.containsKey(SORT)) {
            if (!(searchParams.get(SORT).equalsIgnoreCase(SORT_ASC) ||
                    searchParams.get(SORT).equalsIgnoreCase(SORT_DESC)) || !searchParams.containsKey(ORDER_BY)) {
                throw new ServiceException(INVALID_SORT, searchParams.get(SORT));
            }
        }
    }

    private void validateSearchMapKeys(Set<String> keySet) {
        List<String> searchKeys = Arrays.asList(NAME, DESCRIPTION, ORDER_BY, SORT, TAG_NAME);
        keySet.forEach(key -> {
            if (!searchKeys.contains(key.trim())) {
                throw new ServiceException(NO_SUCH_SEARCH_PARAMETER, key);
            }
        });
    }

    private static boolean isEmptyOrNull(String str) {
        return str == null || str.isEmpty();
    }
}
