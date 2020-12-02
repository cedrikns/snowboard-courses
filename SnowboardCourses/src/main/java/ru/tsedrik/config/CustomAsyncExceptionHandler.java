package ru.tsedrik.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

import java.lang.reflect.Method;

/**
 * Класс для перехвата исключений при асинхронном взаимодействии
 */
public class CustomAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(CustomAsyncExceptionHandler.class.getName());

    @Override
    public void handleUncaughtException(Throwable throwable, Method method, Object... objects) {
        log.error("Method name - " + method.getName() + ". Exception message - " + throwable.getMessage());
    }
}
