package ru.tsedrik.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * Конфигурационный файл для настройки и создания DataSource
 */
@Configuration
@EnableTransactionManagement
public class DataSourceConfig {

    @Bean
    public DataSource dataSource(Environment environment){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(environment.getRequiredProperty("datasource.driver"));
        dataSource.setUrl(environment.getRequiredProperty("datasource.ulr"));
        dataSource.setUsername(environment.getRequiredProperty("datasource.username"));
        dataSource.setPassword(environment.getRequiredProperty("datasource.password"));
        return dataSource;
    }

    @Bean
    public TransactionManager transactionManager(DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }
}
