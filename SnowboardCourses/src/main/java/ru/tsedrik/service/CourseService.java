package ru.tsedrik.service;

import ru.tsedrik.model.Course;
import ru.tsedrik.model.CourseType;

import java.util.Collection;


public interface CourseService {
    void addCourse(Course course);
    Course deleteCourse(Course course);
    Course deleteCourseById(Long id);
    Course getCourseById(Long id);
    Collection<Course> getCourseByType(CourseType type);
}
