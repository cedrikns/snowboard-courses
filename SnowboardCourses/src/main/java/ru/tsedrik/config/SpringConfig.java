package ru.tsedrik.config;

import org.springframework.context.annotation.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

/**
 * Общий конфигурационный файл spring
 */
@Configuration
@PropertySource({"classpath:application.properties", "classpath:exception_messages.properties"})
@ComponentScan("ru.tsedrik")
public class SpringConfig {

    @Bean
    public RequestMappingHandlerAdapter requestMappingHandlerAdapter() {
        RequestMappingHandlerAdapter bean = new RequestMappingHandlerAdapter();
        bean.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        return bean;
    }
}
