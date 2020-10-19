package ru.tsedrik.service;

import ru.tsedrik.dao.CourseDAO;
import ru.tsedrik.model.Course;
import ru.tsedrik.model.CourseType;

import java.util.Collection;

public class CourseServiceImpl implements CourseService{
    private CourseDAO courseDAO;

    public CourseDAO getCourseDAO() {
        return courseDAO;
    }

    public void setCourseDAO(CourseDAO courseDAO) {
        this.courseDAO = courseDAO;
    }

    @Override
    public void addCourse(Course course) {
        courseDAO.create(course);
    }

    @Override
    public Course deleteCourse(Course course) {
        return courseDAO.delete(course);
    }

    @Override
    public Course deleteCourseById(int id) {
        return courseDAO.deleteById(id);
    }

    @Override
    public Collection<Course> getCourseByType(CourseType type) {
        return courseDAO.getByCourseType(type);
    }

    @Override
    public Course getCourseById(int id) {
        return courseDAO.getById(id);
    }
}
