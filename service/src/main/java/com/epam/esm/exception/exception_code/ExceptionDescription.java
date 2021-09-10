package com.epam.esm.exception.exception_code;

/**
 * Class with exception codes
 * for ServiceException
 * @see com.epam.esm.exception.ServiceException
 * @author Lukyanau I.M.
 * @version 1.0
 */
public enum ExceptionDescription {
    //Certificate codes 1-100

    INVALID_CERTIFICATE_ID("CF-00001"),
    CERTIFICATE_WITH_ID_NOT_FOUND("CF-00002"),
    NOT_FOUND_CERTIFICATES("CF-00003"),
    INVALID_CERTIFICATE_NAME("CF-00004"),
    INVALID_CERTIFICATE_DESCRIPTION("CF-00005"),
    INVALID_CERTIFICATE_PRICE("CF-00006"),
    WRONG_CERTIFICATE_PRICE_RANGE("CF-00007"),
    INVALID_CERTIFICATE_DURATION("CF-00008"),
    WRONG_CERTIFICATE_DURATION_RANGE("CF-00009"),
    NOT_UPDATE_CERTIFICATE("CF-00010"),
    INVALID_ORDER_BY("CF-00011"),
    INVALID_SORT("CF-00012"),
    NO_SUCH_SEARCH_PARAMETER("CF-00013"),
    INVALID_SEARCH_PARAMETER_VALUE("CF-00014"),
    NOT_DELETE_CERTIFICATE("CF-00015"),

    //Tag codes 101-200

    INVALID_TAG_ID("TG-00001"),
    TAG_WITH_ID_NOT_FOUND("TG-00002"),
    NO_TAGS_WITH_THIS_NAME("TG-00003"),
    INVALID_TAG_NAME("TG-00004"),
    NOT_FOUND_TAGS("TG-00005"),
    NOT_ADD_TAG("TG-00006"),
    TAG_USED_IN_SOME_CERTIFICATES("TG-00007"),
    NOT_DELETE_TAG("TG-00008");


    private final String errorCode;

    ExceptionDescription(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
