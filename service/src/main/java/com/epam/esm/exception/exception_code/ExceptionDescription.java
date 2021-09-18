package com.epam.esm.exception.exception_code;

/**
 * Class with exception codes
 * for ServiceException
 * @see com.epam.esm.exception.ServiceException
 * @author Lukyanau I.M.
 * @version 1.0
 */
public enum ExceptionDescription {

    //Common codes
    INVALID_ID("CM-00001"),

    //Certificate codes
    CERTIFICATE_WITH_ID_NOT_FOUND("CF-00001"),
    NOT_FOUND_CERTIFICATES("CF-00002"),
    INVALID_CERTIFICATE_NAME("CF-00003"),
    INVALID_CERTIFICATE_DESCRIPTION("CF-00004"),
    INVALID_CERTIFICATE_PRICE("CF-00005"),
    WRONG_CERTIFICATE_PRICE_RANGE("CF-00006"),
    INVALID_CERTIFICATE_DURATION("CF-00007"),
    WRONG_CERTIFICATE_DURATION_RANGE("CF-00008"),
    NOT_UPDATE_CERTIFICATE("CF-00009"),
    INVALID_ORDER_BY("CF-00010"),
    INVALID_SORT("CF-00011"),
    NO_SUCH_SEARCH_PARAMETER("CF-00012"),
    INVALID_SEARCH_PARAMETER_VALUE("CF-00013"),
    NOT_DELETE_CERTIFICATE("CF-00014"),

    //Tag codes
    TAG_WITH_ID_NOT_FOUND("TG-00001"),
    NO_TAGS_WITH_THIS_NAME("TG-00002"),
    INVALID_TAG_NAME("TG-00003"),
    NOT_FOUND_TAGS("TG-00004"),
    NOT_ADD_TAG("TG-00005"),
    TAG_USED_IN_SOME_CERTIFICATES("TG-00006"),
    NOT_DELETE_TAG("TG-00007"),

    //Order
    ORDER_WITH_ID_NOT_FOUND("OR-00001"),

    //User codes
    NOT_FOUND_USERS("US-00001"),
    USER_WITH_ID_NOT_FOUND("US-00002"),
    NOT_ENOUGH_MONEY("US-00003");

    private final String errorCode;

    ExceptionDescription(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
