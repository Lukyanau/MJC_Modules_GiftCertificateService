package com.epam.esm.exception.exception_code;

public enum ExceptionWithCode {
    //Certificate codes 1-100

    INVALID_CERTIFICATE_ID("001", "Invalid certificate id. Should be from 1 and more"),
    CERTIFICATE_WITH_ID_NOT_FOUND("002", "Certificate with this id not found"),
    NOT_FOUND_CERTIFICATES("003","No certificates found"),
    INVALID_CERTIFICATE_NAME("004","Invalid certificate name."),
    INVALID_CERTIFICATE_DESCRIPTION("005","Invalid certificate description."),
    INVALID_CERTIFICATE_PRICE("006","Invalid certificate price."),
    WRONG_CERTIFICATE_PRICE_RANGE("007","Wrong certificate price range. Should be from 1 to 1000"),
    INVALID_CERTIFICATE_DURATION("008","Invalid certificate duration"),
    WRONG_CERTIFICATE_DURATION_RANGE("009","Wrong certificate duration. Should be from 30 to 180"),
    CERTIFICATE_WITH_NAME_NOT_FOUND("010","Certificate with this name not found."),
    NOT_ADD_CERTIFICATE("011","Certificate with this name already exists."),
    NOT_UPDATE_CERTIFICATE("012","No certificate with this id."),

    //Tag codes 101-200

    INVALID_TAG_ID("101", "Invalid tag id. Should be from 1 and more"),
    TAG_WITH_ID_NOT_FOUND("102", "Tag with this id not found"),
    TAG_WITH_NAME_NOT_FOUND("103", "Tag with this name not found"),
    INVALID_TAG_NAME("104","Cannot add tag with this name"),
    NOT_FOUND_TAGS("105","No tags found"),
    NOT_ADD_TAG("106","Tag with this name already exists");


    private final String id;
    private final String message;

    ExceptionWithCode(String id, String message) {
        this.id = id;
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "id=" + id + "\n" +
                "message='" + message;
    }
}
