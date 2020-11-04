package ru.tsedrik.exception;

import org.springframework.validation.Errors;

public class PersonFormValidationException extends RuntimeException{
    Errors errors;

    public PersonFormValidationException() {
    }

    public PersonFormValidationException(String message) {
        super(message);
    }

    public PersonFormValidationException(String message, Errors errors){
        super(message);
        this.errors = errors;
    }

    public Errors getErrors() {
        return errors;
    }
}
