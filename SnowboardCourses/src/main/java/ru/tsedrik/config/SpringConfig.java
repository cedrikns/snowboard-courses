package ru.tsedrik.config;

import org.springframework.context.annotation.*;

/**
 * Общий конфигурационный файл spring
 */
@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan("ru.tsedrik")
public class SpringConfig {

}
