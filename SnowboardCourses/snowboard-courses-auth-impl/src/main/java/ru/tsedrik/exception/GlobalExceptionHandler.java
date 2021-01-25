package ru.tsedrik.exception;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.tsedrik.resource.dto.UserFormValidationError;
import ru.tsedrik.resource.dto.ResponseError;

/**
 * Класс для перехвата исключений
 */
@RestControllerAdvice(basePackages = "ru.tsedrik.controller")
public class GlobalExceptionHandler {

    /**
     * Идентификатор системы, в которой произошло исключение
     */
    @Value("${spring.application.name}")
    private String systemId;

    /**
     * Метод для перехвата и обработки исключения UserFormValidationException
     * которые появились в момент заполенния формы для создания сущности User
     */
    @ExceptionHandler(UserFormValidationException.class)
    public ResponseEntity<UserFormValidationError> personFormValidationException(UserFormValidationException e){
        UserFormValidationError validationError = new UserFormValidationError(
                System.currentTimeMillis(),
                e.getErrors().getAllErrors(),
                "personFormValidationException",
                systemId);

        return new ResponseEntity<>(validationError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Метод для перехвата и обработки исключения IllegalArgumentException
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseError> illegalArgumentException(IllegalArgumentException e){
        ResponseError responseError = new ResponseError(System.currentTimeMillis(),
                e.getMessage(),
                "illegalArgumentException",
                systemId);

        return new ResponseEntity<>(responseError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Метод для перехвата и обработки исключения AccessDeniedException
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ResponseError> accessDeniedException(AccessDeniedException e){
        ResponseError responseError = new ResponseError(System.currentTimeMillis(),
                e.getMessage(),
                "accessDeniedException",
                systemId);
        return new ResponseEntity<>(responseError, new HttpHeaders(), HttpStatus.FORBIDDEN);
    }

    /**
     * Метод для перехвата и обработки исключения AuthenticationException
     */
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ResponseError> authenticationException(AuthenticationException e){
        ResponseError responseError = new ResponseError(System.currentTimeMillis(),
                e.getMessage(),
                "authenticationException",
                systemId);
        return new ResponseEntity<>(responseError, new HttpHeaders(), HttpStatus.UNAUTHORIZED);
    }

    /**
     * Метод для перехвата и обработки всех исключений
     * для которых нет отдельных методов
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseError> exception(Exception e){
        ResponseError responseError = new ResponseError(System.currentTimeMillis(),
                e.getMessage(),
                "undefinedException",
                systemId);

        return new ResponseEntity<>(responseError, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
