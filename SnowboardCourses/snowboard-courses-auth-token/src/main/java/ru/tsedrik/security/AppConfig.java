package ru.tsedrik.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * Конфигурационный файл включения авторизации для сложной ролевой модели
 */
@Configuration
public class AppConfig {

    @Bean
    @Primary
    public AccessControlSecurityExpressionHandler accessControlSecurityExpressionHandler(RoleFactory roleFactory) {
        return new AccessControlSecurityExpressionHandler(roleFactory);
    }
}
