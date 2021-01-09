package ru.tsedrik.config;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import net.rakugakibox.spring.boot.orika.OrikaMapperFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tsedrik.domain.Course;
import ru.tsedrik.domain.Location;
import ru.tsedrik.domain.Person;
import ru.tsedrik.resource.dto.CourseDto;
import ru.tsedrik.resource.dto.LocationDto;
import ru.tsedrik.resource.dto.PersonDto;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

/**
 * Конфигурационный файл для Mapper
 */
@Configuration
public class MapperConfig implements OrikaMapperFactoryConfigurer {

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
        orikaMapperFactory.classMap(Location.class, LocationDto.class)
                .byDefault()
                .register();

        orikaMapperFactory.classMap(Person.class, PersonDto.class)
                .byDefault()
                .register();

        orikaMapperFactory.classMap(Course.class, CourseDto.class)
                .byDefault()
                .register();

    }

}
