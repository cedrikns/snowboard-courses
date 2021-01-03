package ru.tsedrik.exception;

/**
 * Исключение, которое возникает при попытке обратиться к несуществующей сущности класса Location
 */
public class LocationNotFoundException extends RuntimeException{

    public LocationNotFoundException() {
    }

    public LocationNotFoundException(String message) {
        super(message);
    }
}
