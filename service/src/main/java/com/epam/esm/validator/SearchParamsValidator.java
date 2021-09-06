package com.epam.esm.validator;

import com.epam.esm.exception.ServiceException;
import com.epam.esm.utils.CertificateSearchParameters;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.*;

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
    private static final String SORT_BY_NAME = "name";
    private static final String SORT_BY_CREATED_DATE = "created";
    private static final String SORT_TYPE_ASC = "asc";
    private static final String SORT_TYPE_DESC = "desc";


    public void checkSearchParams(Map<String, String> searchParams) {
        validateSearchMapKeys(searchParams.keySet());
        searchParams.values().forEach(s -> {
            if (isEmptyOrNull(s)) {
                throw new ServiceException(INVALID_SEARCH_PARAMETER_VALUE);
            }
        });
        if (searchParams.containsKey(SORT_BY)) {
            if (!searchParams.get(SORT_BY).equalsIgnoreCase(SORT_BY_NAME) &&
                    !searchParams.get(SORT_BY).equalsIgnoreCase(SORT_BY_CREATED_DATE)) {
                throw new ServiceException(INVALID_SORT_BY, searchParams.get(SORT_BY));
            }
        }
        if (searchParams.containsKey(SORT_TYPE)) {
            if (!(searchParams.get(SORT_TYPE).equalsIgnoreCase(SORT_TYPE_ASC) ||
                    searchParams.get(SORT_TYPE).equalsIgnoreCase(SORT_TYPE_DESC)) || !searchParams.containsKey(SORT_BY)) {
                throw new ServiceException(INVALID_SORT_TYPE, searchParams.get(SORT_TYPE));
            }
        }
    }

    private void validateSearchMapKeys(Set<String> keySet) {
        List<String> searchKeys = Arrays.asList(PART_OF_NAME.toLowerCase(), PART_OF_DESCRIPTION.toLowerCase(),
                SORT_BY.toLowerCase(), SORT_TYPE.toLowerCase(), TAG_NAME.toLowerCase());
        keySet.forEach(k -> {
            if (!searchKeys.contains(k.trim().toLowerCase())) {
                throw new ServiceException(NO_SUCH_SEARCH_PARAMETER, k);
            }
        });
    }

    private static boolean isEmptyOrNull(String str) {
        return str == null || str.isEmpty();
    }
}
