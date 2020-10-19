package ru.tsedrik.dao;

import ru.tsedrik.model.Course;
import ru.tsedrik.model.CourseType;
import ru.tsedrik.model.Group;
import ru.tsedrik.model.Person;

import java.util.Collection;

public interface CourseDAO extends GenericDAO<Course, Long> {

    Course getByGroup(Group group);
    Collection<Course> getByInstructor(Person person);
    Collection<Course> getByCourseType(CourseType type);
}
