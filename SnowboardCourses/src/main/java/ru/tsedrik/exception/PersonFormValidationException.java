package ru.tsedrik.exception;

import org.springframework.validation.Errors;

/**
 * Исключение, которое возникает в случае, если валидация полей формы по созданию сущности Person была неуспешной
 */
public class PersonFormValidationException extends RuntimeException{

    /**
     * Ошибки, обнаруженные во время валидации полей формы
     */
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
