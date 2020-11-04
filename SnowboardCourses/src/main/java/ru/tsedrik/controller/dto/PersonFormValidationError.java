package ru.tsedrik.controller.dto;

import org.springframework.validation.ObjectError;

import java.util.List;

public class PersonFormValidationError {
    private Long id;
    private String errorCode;
    private List<ObjectError> errors;
    private String systemId;

    public PersonFormValidationError(Long id, List<ObjectError> errors, String errorCode, String systemId) {
        this.id = id;
        this.errors = errors;
        this.errorCode = errorCode;
        this.systemId = systemId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<ObjectError> getErrors() {
        return errors;
    }

    public void setErrors(List<ObjectError> errors) {
        this.errors = errors;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
