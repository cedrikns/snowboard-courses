package ru.tsedrik.dao;

import ru.tsedrik.model.Course;
import ru.tsedrik.model.CourseType;
import ru.tsedrik.model.Person;

import java.time.LocalDate;
import java.util.Collection;

/**
 * Интерфейс управления персистентным состоянием объектов типа Course
 */
public interface CourseDAO extends GenericDAO<Course, Long> {

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

    /**
     * Возвращает записи о курсах указанного типа, которые проходят в указанный период времени.
     * @param type  тип курса
     * @param beginDate дата начала интересующего периода
     * @param endDate дата окончания интересующего периода
     * @return  список найденных курсов
     */
    Collection<Course> getByCourseTypeAndDate(CourseType type, LocalDate beginDate, LocalDate endDate);
}
