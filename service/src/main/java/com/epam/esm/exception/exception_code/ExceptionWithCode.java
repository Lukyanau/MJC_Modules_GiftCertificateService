package com.epam.esm.exception.exception_code;

public enum ExceptionWithCode {
    INVALID_CERTIFICATE_ID(1, "Invalid certificate id. Should be from 1 and more"),
    INVALID_TAG_ID(2, "Invalid tag id. Should be from 1 and more"),
    CERTIFICATE_WITH_ID_NOT_FOUND(3, "Certificate with this id not found"),
    TAG_WITH_ID_NOT_FOUND(4, "Tag with this id not found"),
    INCORRECT_INPUT_PARAMETERS(5, "Incorrect input parameters"),
    INVALID_TAG_NAME(6,"Cannot add tag with this name"),
    NOT_FOUND_CERTIFICATES(7,"No certificates found"),
    NOT_FOUND_TAGS(8,"No tags found"),
    INVALID_CERTIFICATE_NAME(9,"Invalid certificate name."),
    CERTIFICATE_WITH_NAME_NOT_FOUND(10,"Certificate with this name not found.");

    private final int id;
    private final String message;

    ExceptionWithCode(int id, String message) {
        this.id = id;
        this.message = message;
    }

    public int getId() {
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
