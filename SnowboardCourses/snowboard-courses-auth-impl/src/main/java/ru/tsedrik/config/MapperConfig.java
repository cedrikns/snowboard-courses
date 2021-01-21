package ru.tsedrik.config;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.converter.ConverterFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import net.rakugakibox.spring.boot.orika.OrikaMapperFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ru.tsedrik.domain.User;
import ru.tsedrik.resource.dto.UserDto;
import ru.tsedrik.resource.dto.UserWithPasswordDto;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

/**
 * Конфигурационный файл для Mapper
 */
@Configuration
public class MapperConfig implements OrikaMapperFactoryConfigurer {

    private final PasswordConverter passwordConverter;

    public MapperConfig(PasswordConverter passwordConverter) {
        this.passwordConverter = passwordConverter;
    }

    @Bean
    DatatypeFactory datatypeFactory() throws DatatypeConfigurationException {
        return DatatypeFactory.newInstance();
    }

    @Bean
    MappingContext.Factory mappingFactory() {
        MappingContext.Factory factory = new MappingContext.Factory();
        new DefaultMapperFactory.Builder().mappingContextFactory(factory).build();
        return factory;
    }

    @Override
    public void configure(MapperFactory orikaMapperFactory) {

        orikaMapperFactory.classMap(User.class, UserDto.class)
                .mapNullsInReverse(false)
                .byDefault()
                .register();

        orikaMapperFactory.classMap(User.class, UserWithPasswordDto.class)
                .mapNullsInReverse(false)
                .fieldMap("password", "password")
                .bToA().converter("passwordConverter").add()
                .byDefault()
                .register();

        ConverterFactory converterFactory = orikaMapperFactory.getConverterFactory();
        converterFactory.registerConverter("passwordConverter", passwordConverter);

    }

}
