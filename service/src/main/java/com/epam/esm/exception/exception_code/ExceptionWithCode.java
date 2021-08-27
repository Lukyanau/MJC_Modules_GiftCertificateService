package com.epam.esm.exception.exception_code;

public enum ExceptionWithCode {
    //Certificate codes 1-100

    INVALID_CERTIFICATE_ID("001", "Invalid certificate id. Should be from 1 and more"),
    CERTIFICATE_WITH_ID_NOT_FOUND("002", "Certificate with this id not found"),
    INCORRECT_INPUT_PARAMETERS("003", "Incorrect input parameters"),
    NOT_FOUND_CERTIFICATES("004","No certificates found"),
    INVALID_CERTIFICATE_NAME("005","Invalid certificate name."),
    CERTIFICATE_WITH_NAME_NOT_FOUND("006","Certificate with this name not found."),
    NOT_ADD_CERTIFICATE("007","Certificate with this name already exists."),

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
