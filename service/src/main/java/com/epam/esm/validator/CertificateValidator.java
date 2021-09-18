package com.epam.esm.validator;

import com.epam.esm.dto.RequestCertificateDto;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.exception.exception_code.ExceptionDescription;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
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
    private static final String NAME_REGEX = "^(.{3,50})$";
    private static final String DESCRIPTION_REGEX = NAME_REGEX;

    public void validateCertificateDto(RequestCertificateDto certificateDto) {
        List<ExceptionDescription> errorCodes = new ArrayList<>();
        //List<error>
        if (!checkCertificateDtoName(certificateDto.getName())) {
            errorCodes.add(INVALID_CERTIFICATE_NAME);
        }
        if (!checkCertificateDtoDescription(certificateDto.getDescription())) {
            errorCodes.add(INVALID_CERTIFICATE_DESCRIPTION);
        }
        if (checkCertificateDtoPrice(certificateDto.getPrice()) != null) {
            errorCodes.add(checkCertificateDtoPrice(certificateDto.getPrice()));
        }
        if (checkCertificateDtoDuration(certificateDto.getDuration()) != null) {
            errorCodes.add(INVALID_CERTIFICATE_DURATION);
        }
        if(!errorCodes.isEmpty()){
            throw new ServiceException(errorCodes);
        }//map is not empty throw ServiceException
    }

    private boolean checkCertificateDtoName(String name) {
        return !isEmptyOrNull(name) && name.trim().matches(NAME_REGEX);
    }

    private boolean checkCertificateDtoDescription(String description) {
        return !isEmptyOrNull(description) && description.trim().matches(DESCRIPTION_REGEX);
    }

    private ExceptionDescription checkCertificateDtoPrice(BigDecimal price) {
        String strPrice = String.valueOf(price);
        if (isEmptyOrNull(strPrice) || !strPrice.matches(PRICE_REGEX)) {
            return INVALID_CERTIFICATE_PRICE;
        }
        int certificatePrice = price.intValue();
        if (!(MIN_CERTIFICATE_PRICE <= certificatePrice && certificatePrice <= MAX_CERTIFICATE_PRICE)) {
            return WRONG_CERTIFICATE_PRICE_RANGE;
        }
        return null;
    }

    private ExceptionDescription checkCertificateDtoDuration(int duration) {
        String strDuration = String.valueOf(duration);
        if (isEmptyOrNull(strDuration) || !strDuration.matches(DURATION_REGEX) || duration == 0) {
            return INVALID_CERTIFICATE_DURATION;
        }
        if (!(MIN_CERTIFICATE_DURATION <= duration && duration <= MAX_CERTIFICATE_DURATION)) {
            return WRONG_CERTIFICATE_DURATION_RANGE;
        }
        return null;
    }

    private boolean isEmptyOrNull(String str) {
        return str == null || str.isEmpty();
    }
}
