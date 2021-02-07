package ru.tsedrik.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;
import ru.tsedrik.invoker.ForecastService;

/**
 * Конфигурационный файл для удаленного сервиса по получению погоды
 */
@Configuration
public class ForecastServiceConfig {

    @Value("${forecast-service.url}")
    private String url;
    @Bean
    public HttpInvokerProxyFactoryBean phoneService() {
        HttpInvokerProxyFactoryBean invokerProxyFactoryBean = new HttpInvokerProxyFactoryBean();
        invokerProxyFactoryBean.setServiceUrl(url);
        invokerProxyFactoryBean.setServiceInterface(ForecastService.class);
        return invokerProxyFactoryBean;
    }
}
