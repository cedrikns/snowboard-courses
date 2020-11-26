package ru.tsedrik.exception;

/**
 * Исключение, которое возникает при попытке обратиться к несуществующей сущности класса Course
 */
public class CourseNotFoundException extends RuntimeException{

    public CourseNotFoundException() {
    }

    public CourseNotFoundException(String message) {
        super(message);
    }
}
