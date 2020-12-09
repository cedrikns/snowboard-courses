package ru.tsedrik.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tsedrik.domain.Course;
import ru.tsedrik.domain.CourseType;

import java.util.Collection;

/**
 * Интерфейс управления персистентным состоянием объектов типа Course
 */
@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    /**
     * Возвращает записи о курсах указанного типа.
     * @param type  тип курса
     * @return  список найденных курсов
     */
    Collection<Course> getCourseByCourseType(CourseType type);
}
