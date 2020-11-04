package ru.tsedrik.controller.dto;

public class ResponseError {
    private Long id;
    private String message;
    private String errorCode;
    private String systemId;

    public ResponseError(Long id, String message, String errorCode, String systemId) {
        this.id = id;
        this.message = message;
        this.errorCode = errorCode;
        this.systemId = systemId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }
}
