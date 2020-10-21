package ru.tsedrik.dao.map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import ru.tsedrik.dao.CourseDAO;
import ru.tsedrik.dao.GroupDAO;
import ru.tsedrik.dao.LocationDAO;
import ru.tsedrik.dao.PersonDAO;

/**
 * Конфигурационный файл бинов для персистентного слоя данных
 */
@Configuration
public class RepositoryConfig {

    @Bean
    public PersonDAO mapPersonDao(){
        return new PersonDAOImpl();
    }

    @Bean
    public LocationDAO mapLocationDao(){
        return new LocationDAOImpl();
    }

    @Bean
    public GroupDAO mapGroupDao(){
        return new GroupDAOImpl();
    }

    @Bean
    @Primary
    public GroupDAO mapGroupDao2(){
        return new GroupDAOImpl();
    }

    @Bean
    public CourseDAO mapCourseDao(){
        return new CourseDAOImpl();
    }

    @Bean
    public CourseDAO mapCourseDao2(){
        return new CourseDAOImpl();
    }
}
