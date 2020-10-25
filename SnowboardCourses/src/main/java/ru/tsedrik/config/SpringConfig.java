package ru.tsedrik.config;

import org.springframework.context.annotation.*;
import ru.tsedrik.dao.map.RepositoryConfig;
import ru.tsedrik.service.ServiceConfig;

/**
 * Общий конфигурационный файл spring
 */
@Configuration
@PropertySource("classpath:application.properties")
@Import({RepositoryConfig.class, ServiceConfig.class})
public class SpringConfig {

}
