package ru.tsedrik.exception;

public class CourseLocationNotFoundException extends RuntimeException{

    public CourseLocationNotFoundException() {
    }

    public CourseLocationNotFoundException(String message) {
        super(message);
    }
}
