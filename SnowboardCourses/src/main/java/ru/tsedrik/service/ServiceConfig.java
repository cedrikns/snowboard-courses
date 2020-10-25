package ru.tsedrik.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tsedrik.dao.GroupDAO;
import ru.tsedrik.dao.PersonDAO;

/**
 * Конфигурационный файл бинов для сервисного слоя
 */
@Configuration
public class ServiceConfig {

    @Bean
    public PersonService personService(PersonDAO personDAO){
        return new PersonServiceImpl(personDAO);
    }

    @Bean
    public GroupService groupService(GroupDAO groupDAO){
        return new GroupServiceImpl(groupDAO);
    }

    @Bean
    public CourseService courseService(){
        return new CourseServiceImpl();
    }
}
