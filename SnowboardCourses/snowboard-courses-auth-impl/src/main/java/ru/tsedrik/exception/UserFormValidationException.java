package ru.tsedrik.exception;

import org.springframework.validation.Errors;

/**
 * Исключение, которое возникает в случае, если валидация полей формы по созданию сущности User была неуспешной
 */
public class UserFormValidationException extends RuntimeException{

    /**
     * Ошибки, обнаруженные во время валидации полей формы
     */
    Errors errors;

    public UserFormValidationException() {
    }

    public UserFormValidationException(String message) {
        super(message);
    }

    public UserFormValidationException(String message, Errors errors){
        super(message);
        this.errors = errors;
    }

    public Errors getErrors() {
        return errors;
    }
}
