package ru.tsedrik.exception;

/**
 * Исключение, которое возникает при попытке обратиться к несуществующей сущности класса Person
 */
public class PersonNotFoundException extends RuntimeException{
    public PersonNotFoundException() {
    }

    public PersonNotFoundException(String message) {
        super(message);
    }
}
