package ru.tsedrik.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.tsedrik.dao.CourseDAO;
import ru.tsedrik.model.Course;
import ru.tsedrik.model.CourseType;

import java.util.Collection;

/**
 * Реализация интерфейса CourseService
 */
@Service
public class CourseServiceImpl implements CourseService{

    /**
     * Объект для управления персистентным состоянием объектов типа Course
     */
    private CourseDAO courseDAO;

    @Value("${course.maxGroupCount}")
    private String maxGroupCount;

    public CourseDAO getCourseDAO() {
        return courseDAO;
    }

    @Autowired
    @Qualifier("mapCourseDao2")
    public void setCourseDAO(CourseDAO courseDAO) {
        this.courseDAO = courseDAO;
    }

    @Override
    public void addCourse(Course course) {
        int groupCount = course.getGroups().size();
        if (groupCount > Integer.valueOf(maxGroupCount)){
            throw new IllegalArgumentException("Превышено максимальное количество групп на курсе: " + groupCount
                    + " вместо " + maxGroupCount);
        }
        courseDAO.create(course);
    }

    @Override
    public Course deleteCourse(Course course) {
        return courseDAO.delete(course);
    }

    @Override
    public Course deleteCourseById(Long id) {
        return courseDAO.deleteById(id);
    }

    @Override
    public Collection<Course> getCourseByType(CourseType type) {
        return courseDAO.getByCourseType(type);
    }

    @Override
    public Course getCourseById(Long id) {
        return courseDAO.getById(id);
    }
}
