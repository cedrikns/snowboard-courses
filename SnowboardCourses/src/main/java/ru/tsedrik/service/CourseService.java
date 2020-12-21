package ru.tsedrik.service;

import org.springframework.data.domain.Pageable;
import ru.tsedrik.controller.dto.*;
import ru.tsedrik.domain.Course;

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
     * Записывает Участника на конкретный курс
     * @param courseId  идентификатор курса, на который будет записан Участник
     * @param personId  идентификатор Участника
     * @return  Обновленный курс
     */
    CourseDto enroll(Long courseId, Long personId);

    /**
     * Получает все курсы, соответствующие наболу полей из courseSearchDto
     *
     * @param courseSearchDto набор параметров, по которым осуществляется поиск
     * @param pageable  настройка отображения результата
     * @return  страница найденных курсов, соотвествующая настройкам объекта pageable
     */
    PageDto<CourseDto> getCourses(CourseSearchDto courseSearchDto, Pageable pageable);
}
