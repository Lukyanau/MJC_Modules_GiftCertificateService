package com.epam.esm.validator;

import com.epam.esm.dto.RequestCertificateDTO;
import com.epam.esm.exception.IncorrectInputParametersException;
import com.epam.esm.exception.InvalidIdException;
import com.epam.esm.exception.InvalidNameException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static com.epam.esm.exception.exception_code.ExceptionWithCode.*;

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
    private static final String NAME_REGEX = "^([a-zA-Z][a-zA-Z, ]{1,30})+$";
    private static final String DESCRIPTION_REGEX = NAME_REGEX;

    public void validateCertificateDTO(RequestCertificateDTO certificateDTO) {
        checkCertificateDTOName(certificateDTO.getName());
        if (!checkCertificateDTODescription(certificateDTO.getDescription())
                && checkCertificateDTOPrice(certificateDTO.getPrice()) && checkCertificateDTODuration(certificateDTO.getDuration())) {
            throw new IncorrectInputParametersException(INCORRECT_INPUT_PARAMETERS.getId(), INCORRECT_INPUT_PARAMETERS.getMessage());
        }
    }


    public void checkCertificateDTOId(long id) {
        if (!isNotEmptyOrNull(String.valueOf(id)) && String.valueOf(id).matches(ID_REGEX) && id >= MIN_CERTIFICATE_ID) {
            throw new InvalidIdException(INVALID_CERTIFICATE_ID.getId(), INVALID_CERTIFICATE_ID.name());
        }
    }

    public void checkCertificateDTOName(String name) {
        if (!isNotEmptyOrNull(name) && name.matches(NAME_REGEX)) {
            throw new InvalidNameException(INVALID_CERTIFICATE_NAME.getId(), INVALID_CERTIFICATE_NAME.name());
        }
    }

    public boolean checkCertificateDTODescription(String description) {
        return isNotEmptyOrNull(description) && description.matches(DESCRIPTION_REGEX);
    }

    public boolean checkCertificateDTOPrice(BigDecimal price) {
        String strPrice = String.valueOf(price);
        if (isNotEmptyOrNull(strPrice) && strPrice.matches(PRICE_REGEX)) {
            int certificatePrice = price.intValue();
            return MIN_CERTIFICATE_PRICE <= certificatePrice && certificatePrice <= MAX_CERTIFICATE_PRICE;
        }
        return false;
    }

    public boolean checkCertificateDTODuration(int duration) {
        String strDuration = String.valueOf(duration);
        if (isNotEmptyOrNull(strDuration) && strDuration.matches(DURATION_REGEX)) {
            return duration > MIN_CERTIFICATE_DURATION && duration < MAX_CERTIFICATE_DURATION;
        }
        return false;
    }

    private static boolean isNotEmptyOrNull(String str) {
        return str != null && !str.isEmpty();
    }
}
