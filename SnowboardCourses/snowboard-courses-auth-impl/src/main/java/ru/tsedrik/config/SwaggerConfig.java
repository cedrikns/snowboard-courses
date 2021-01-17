package ru.tsedrik.config;

import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Pageable;
import ru.tsedrik.config.properties.SwaggerProperties;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.schema.ScalarType;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ParameterType;
import springfox.documentation.service.RequestParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;

import static springfox.documentation.builders.PathSelectors.regex;

/**
 * Конфигурационный файл для Swagger
 */
@Configuration
@Profile("swagger")
public class SwaggerConfig {
    private SwaggerProperties properties;

    private ServerProperties serverProperties;

    private BuildProperties buildProperties;

    public SwaggerConfig(SwaggerProperties properties, ServerProperties serverProperties, BuildProperties buildProperties) {
        this.properties = properties;
        this.serverProperties = serverProperties;
        this.buildProperties = buildProperties;
    }

    @Bean
    public Docket apiOrder() {
        return buildDocket("/api");
    }

    @Bean
    public Docket actuatorOrder() {
        return buildDocket("/actuator");
    }

    protected Docket buildDocket(String serviceUrl) {
        RequestParameter authorizationHeader = new RequestParameterBuilder()
                .name("Authorization")
                .description("Authorization token")
                .in(ParameterType.HEADER)
                .required(true)
                .query(q -> q.defaultValue("Bearer")
                        .model(m -> m.scalarModel(ScalarType.STRING)))
                .build();

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName(String.format("%s/", serviceUrl))
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(regex(String.format(".*%s.*", serviceUrl)))
                .build()
                .ignoredParameterTypes(Pageable.class)
                .apiInfo(metadata())
                .globalRequestParameters(Arrays.asList(authorizationHeader));
    }

    protected ApiInfo metadata() {
        return new ApiInfoBuilder()
                .title(properties.getTitle())
                .description(properties.getDescription())
                .version(buildProperties.getVersion())
                .contact(new Contact(
                        properties.getContact().getName(),
                        properties.getContact().getUrl(),
                        properties.getContact().getMail()
                ))
                .build();
    }
}
