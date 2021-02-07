package ru.tsedrik;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Конфигурационный файл для запуска приложения Spring Boot
 */
@SpringBootApplication
@Configuration
@EnableScheduling
@EnableTransactionManagement
@PropertySource("classpath:exception_messages.properties")
@EnableFeignClients("ru.tsedrik")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
