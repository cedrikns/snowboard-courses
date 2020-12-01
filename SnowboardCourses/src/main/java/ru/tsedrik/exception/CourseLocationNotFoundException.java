package ru.tsedrik.exception;

/**
 * Исключение, которое возникает при попытке обратиться к несуществующей сущности класса CourseLocation
 */
public class CourseLocationNotFoundException extends RuntimeException{

    public CourseLocationNotFoundException() {
    }

    public CourseLocationNotFoundException(String message) {
        super(message);
    }
}
