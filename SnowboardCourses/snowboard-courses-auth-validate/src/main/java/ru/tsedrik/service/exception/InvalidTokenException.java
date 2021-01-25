package ru.tsedrik.service.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * Выбрасывается, если токен не прошел валидацию
 */
public class InvalidTokenException extends AuthenticationException {

    public InvalidTokenException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public InvalidTokenException(String msg) {
        super(msg);
    }
}
