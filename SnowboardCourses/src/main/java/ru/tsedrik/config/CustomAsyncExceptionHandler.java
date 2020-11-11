package ru.tsedrik.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

import java.lang.reflect.Method;

/**
 * Класс для перехвата исключений при асинхронном взаимодействии
 */
public class CustomAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {

    private static final Logger log = LogManager.getLogger(CustomAsyncExceptionHandler.class.getName());

    @Override
    public void handleUncaughtException(Throwable throwable, Method method, Object... objects) {
        log.error("Method name - " + method.getName() + ". Exception message - " + throwable.getMessage());
    }
}