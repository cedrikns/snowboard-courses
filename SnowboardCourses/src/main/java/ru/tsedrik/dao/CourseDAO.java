package ru.tsedrik.dao;

import ru.tsedrik.model.Course;
import ru.tsedrik.model.CourseType;
import ru.tsedrik.model.Group;
import ru.tsedrik.model.Person;

import java.util.Collection;

/**
 * Интерфейс управления персистентным состоянием объектов типа Course
 */
public interface CourseDAO extends GenericDAO<Course, Long> {

    /**
     * Возвращает запись о курсе, который содержит указанную группу.
     *
     * @param group группа для поиска курса
     * @return  найденная запись о курсе
     */
    Course getByGroup(Group group);

    /**
     * Возвращает записи о курсах, в которых участвует указанный участник.
     *
     * @param person    участник курсов
     * @return  список найденных курсов
     */
    Collection<Course> getByInstructor(Person person);

    /**
     * Возвращает записи о курсах указанного типа.
     * @param type  тип курса
     * @return  список найденных курсов
     */
    Collection<Course> getByCourseType(CourseType type);
}
