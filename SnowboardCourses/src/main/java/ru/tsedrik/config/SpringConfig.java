package ru.tsedrik.config;

import org.springframework.context.annotation.*;

/**
 * Общий конфигурационный файл spring
 */
@Configuration
@PropertySource({"classpath:application.properties", "classpath:exception_messages.properties"})
@ComponentScan("ru.tsedrik")
public class SpringConfig {

}
