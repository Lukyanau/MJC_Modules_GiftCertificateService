package com.epam.esm.validator;

import com.epam.esm.dto.RequestCertificateDto;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.exception.exception_code.ExceptionDescription;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.epam.esm.exception.exception_code.ExceptionDescription.*;

/**
 * Validator for certificates from request
 *
 * @author Lukyanau I.M.
 * @version 1.0
 */
@Component
public class CertificateValidator {

    private static final BigDecimal MIN_CERTIFICATE_PRICE = BigDecimal.valueOf(1);
    private static final BigDecimal MAX_CERTIFICATE_PRICE = BigDecimal.valueOf(1000);
    private static final long MIN_CERTIFICATE_DURATION = 30;
    private static final long MAX_CERTIFICATE_DURATION = 180;
    private static final String NAME_REGEX = "^(.{3,50})$";
    private static final String DESCRIPTION_REGEX = NAME_REGEX;

    public void validateCertificateDto(RequestCertificateDto certificateDto) {
        List<ExceptionDescription> errorCodes = new ArrayList<>();
        if (!checkCertificateDtoName(certificateDto.getName())) {
            errorCodes.add(INVALID_CERTIFICATE_NAME);
        }
        if (!checkCertificateDtoDescription(certificateDto.getDescription())) {
            errorCodes.add(INVALID_CERTIFICATE_DESCRIPTION);
        }
        if (checkCertificateDtoPrice(certificateDto.getPrice()).isPresent()) {
            errorCodes.add(checkCertificateDtoPrice(certificateDto.getPrice()).get());
        }
        if (checkCertificateDtoDuration(certificateDto.getDuration()).isPresent()) {
            errorCodes.add(INVALID_CERTIFICATE_DURATION);
        }
        if (!errorCodes.isEmpty()) {
            throw new ServiceException(errorCodes);
        }
    }

    private boolean checkCertificateDtoName(String name) {
        return !isNull(name) && !name.isEmpty() && name.trim().matches(NAME_REGEX);
    }

    private boolean checkCertificateDtoDescription(String description) {
        return !isNull(description) && !description.isEmpty() && description.trim().matches(DESCRIPTION_REGEX);
    }

    private Optional<ExceptionDescription> checkCertificateDtoPrice(BigDecimal price) {
        if (isNull(price)) {
            return Optional.of(INVALID_CERTIFICATE_PRICE);
        }
        if (!(price.compareTo(MIN_CERTIFICATE_PRICE) >= 0 && price.compareTo(MAX_CERTIFICATE_PRICE) <= 0)) {
            return Optional.of(WRONG_CERTIFICATE_PRICE_RANGE);
        }
        return Optional.empty();
    }

    private Optional<ExceptionDescription> checkCertificateDtoDuration(int duration) {
        if (!(MIN_CERTIFICATE_DURATION <= duration && duration <= MAX_CERTIFICATE_DURATION)) {
            return Optional.of(WRONG_CERTIFICATE_DURATION_RANGE);
        }
        return Optional.empty();
    }

    private boolean isNull(Object obj) {
        return obj == null;
    }
}
