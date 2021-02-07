package ru.tsedrik.invoker;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.httpinvoker.HttpInvokerServiceExporter;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.context.support.HttpRequestHandlerServlet;

/**
 * Конфигурационный файл для регистрации удаленного сервиса
 */
@Configuration
public class PublisherConfig {

    @Bean
    public ServletRegistrationBean updateServletRegistrationBean() {
        return new ServletRegistrationBean(new HttpRequestHandlerServlet(), "/remoting/forecastService");
    }

    @Bean(name = "httpRequestHandlerServlet")
    public HttpRequestHandler phoneExporter() {
        HttpInvokerServiceExporter httpInvokerServiceExporter = new HttpInvokerServiceExporter();
        httpInvokerServiceExporter.setService(new ForecastServiceImpl());
        httpInvokerServiceExporter.setServiceInterface(ForecastService.class);
        return httpInvokerServiceExporter;
    }
}
