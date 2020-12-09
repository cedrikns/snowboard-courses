package ru.tsedrik.service;

import ru.tsedrik.controller.dto.CourseDto;
import ru.tsedrik.domain.Course;
import ru.tsedrik.domain.CourseType;

import java.util.Collection;

/**
 * CourseService представляет интерфейс взаимодействия с классом Course
 */
public interface CourseService {

    /**
     * Добавляет новый курс.
     *
     * @param courseDto    новый курс, который будет добавлен
     */
    CourseDto addCourse(CourseDto courseDto);

    /**
     * Удаляет существующий курс.
     *
     * @param course    существующий курс, который будет удален
     * @return  успешно ли прошло удаление
     */
    boolean deleteCourse(Course course);

    /**
     * Удаляет существующий курс по его идентификатору.
     *
     * @param id    идентификатор удаляемого курса
     * @return  успешно ли прошло удаление
     */
    boolean deleteCourseById(Long id);

    /**
     * Запрашивает курс по его идентификатору.
     *
     * @param id    идентификатор запрашиваемого курса
     * @return  найденный курс
     */
    CourseDto getCourseById(Long id);

    /**
     * Запрашивает набор всех курсов указанного типа CourseType.
     *
     * @param type  тип курсов для поиска
     * @return  список всех найденных курсов указанного типа
     */
    Collection<Course> getCourseByType(CourseType type);

    /**
     * Записывает Участника на конкретный курс
     * @param courseId  идентификатор курса, на который будет записан Участник
     * @param personId  идентификатор Участника
     * @return  Обновленный курс
     */
    CourseDto enroll(Long courseId, Long personId);
}
