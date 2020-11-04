package ru.tsedrik.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.tsedrik.controller.dto.PersonFormValidationError;
import ru.tsedrik.controller.dto.ResponseError;

@RestControllerAdvice(basePackages = "ru.tsedrik.controller")
public class GlobalExceptionHandler {

    /**
     * Метод для перехвата и обработки исключения PersonFormValidationException
     * которые появились в момент заполенния формы для создания сущности Person
     */
    @ExceptionHandler(PersonFormValidationException.class)
    public ResponseEntity<PersonFormValidationError> personFormValidationException(PersonFormValidationException e){
        PersonFormValidationError validationError = new PersonFormValidationError(
                System.currentTimeMillis(),
                e.getErrors().getAllErrors(),
                "personFormValidationException",
                "my-system");

        return new ResponseEntity<>(validationError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Метод для перехвата и обработки исключений
     * которые связаны с попыткой запросить несуществующие сущности
     */
    @ExceptionHandler({PersonNotFoundException.class, CourseNotFoundException.class})
    public ResponseEntity<ResponseError> entityNotFoundException(RuntimeException e){
        ResponseError responseError = new ResponseError(System.currentTimeMillis(),
                e.getMessage(),
                "entityNotFoundException",
                "my-system");

        return new ResponseEntity<>(responseError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Метод для перехвата и обработки исключения IllegalArgumentException
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseError> illegalArgumentException(IllegalArgumentException e){
        ResponseError responseError = new ResponseError(System.currentTimeMillis(),
                e.getMessage(),
                "illegalArgumentException",
                "my-system");

        return new ResponseEntity<>(responseError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
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
                "my-system");

        return new ResponseEntity<>(responseError, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
