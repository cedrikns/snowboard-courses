package ru.tsedrik.config;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * Конфигурационный файл spring для асинхронных взаимодействий
 */
@Configuration
@EnableAsync
public class SpringAsyncConfig implements AsyncConfigurer {

    /**
     * Настройка corePoolSize для экзекьютора асинхронного взаимодействия
     */
    @Value("${async.corePoolSize}")
    private String corePoolSize;

    /**
     * Настройка maxPoolSize для экзекьютора асинхронного взаимодействия
     */
    @Value("${async.maxPoolSize}")
    private String maxPoolSize;

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(Integer.parseInt(corePoolSize));
        threadPoolTaskExecutor.setMaxPoolSize(Integer.parseInt(maxPoolSize));
        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new CustomAsyncExceptionHandler();
    }
}
