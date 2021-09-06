package com.epam.esm.validator;

import com.epam.esm.dto.RequestCertificateDto;
import com.epam.esm.exception.ServiceException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static com.epam.esm.exception.exception_code.ExceptionDescription.*;

/**
 * Validator for certificates from request
 *
 * @author Lukyanau I.M.
 * @version 1.0
 */
@Component
public class CertificateValidator {

    private static final long MIN_CERTIFICATE_ID = 1;
    private static final long MIN_CERTIFICATE_PRICE = 1;
    private static final long MAX_CERTIFICATE_PRICE = 1000;
    private static final long MIN_CERTIFICATE_DURATION = 30;
    private static final long MAX_CERTIFICATE_DURATION = 180;
    private static final String PRICE_REGEX = "^\\d+\\.?\\d+$";
    private static final String ID_REGEX = "^[0-9]+$";
    private static final String DURATION_REGEX = ID_REGEX;
    private static final String NAME_REGEX = "^([a-zA-Z0-9][a-zA-Z0-9, .]{3,50})$";
    private static final String DESCRIPTION_REGEX = NAME_REGEX;

    public void validateCertificateDto(RequestCertificateDto certificateDto) {
        checkCertificateDtoName(certificateDto.getName());
        checkCertificateDtoDescription(certificateDto.getDescription());
        checkCertificateDtoPrice(certificateDto.getPrice());
        checkCertificateDtoDuration(certificateDto.getDuration());
    }

    public void checkCertificateDtoId(Long id) {
        if (id == null || !id.toString().matches(ID_REGEX) || id < MIN_CERTIFICATE_ID) {
            throw new ServiceException(INVALID_CERTIFICATE_ID, String.valueOf(id));
        }
    }

    public void checkCertificateDtoName(String name) {
        if (isEmptyOrNull(name) || !name.matches(NAME_REGEX)) {
            throw new ServiceException(INVALID_CERTIFICATE_NAME, name);
        }
    }

    public void checkCertificateDtoDescription(String description) {
        if (isEmptyOrNull(description) || !description.matches(DESCRIPTION_REGEX)) {
            throw new ServiceException(INVALID_CERTIFICATE_DESCRIPTION, description);
        }
    }

    public void checkCertificateDtoPrice(BigDecimal price) {
        String strPrice = String.valueOf(price);
        if (isEmptyOrNull(strPrice) || !strPrice.matches(PRICE_REGEX)) {
            throw new ServiceException(INVALID_CERTIFICATE_PRICE, String.valueOf(price));
        }
        int certificatePrice = price.intValue();
        if (!(MIN_CERTIFICATE_PRICE <= certificatePrice && certificatePrice <= MAX_CERTIFICATE_PRICE)) {
            throw new ServiceException(WRONG_CERTIFICATE_PRICE_RANGE);
        }
    }

    public void checkCertificateDtoDuration(int duration) {
        String strDuration = String.valueOf(duration);
        if (isEmptyOrNull(strDuration) || !strDuration.matches(DURATION_REGEX)) {
            throw new ServiceException(INVALID_CERTIFICATE_DURATION, String.valueOf(duration));
        }
        if (!(MIN_CERTIFICATE_DURATION <= duration && duration <= MAX_CERTIFICATE_DURATION)) {
            throw new ServiceException(WRONG_CERTIFICATE_DURATION_RANGE);
        }
    }

    private static boolean isEmptyOrNull(String str) {
        return str == null || str.isEmpty();
    }
}
