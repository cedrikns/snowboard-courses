package ru.tsedrik.service;

import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import ru.tsedrik.domain.Course;
import ru.tsedrik.resource.dto.CourseDto;
import ru.tsedrik.resource.dto.CourseSearchDto;
import ru.tsedrik.resource.dto.PageDto;

/**
 * CourseService представляет интерфейс взаимодействия с классом Course
 */
public interface CourseService {

    /**
     * Добавляет новый курс.
     *
     * @param courseDto    новый курс, который будет добавлен
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    CourseDto addCourse(CourseDto courseDto);

    /**
     * Удаляет существующий курс.
     *
     * @param course    существующий курс, который будет удален
     * @return  успешно ли прошло удаление
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    boolean deleteCourse(Course course);

    /**
     * Удаляет существующий курс по его идентификатору.
     *
     * @param id    идентификатор удаляемого курса
     * @return  успешно ли прошло удаление
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    boolean deleteCourseById(Long id);

    /**
     * Запрашивает курс по его идентификатору.
     *
     * @param id    идентификатор запрашиваемого курса
     * @return  найденный курс
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    CourseDto getCourseById(Long id);

    /**
     * Записывает Участника на конкретный курс
     * @param courseId  идентификатор курса, на который будет записан Участник
     * @param personId  идентификатор Участника
     * @return  Обновленный курс
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    CourseDto enroll(Long courseId, Long personId);

    /**
     * Получает все курсы, соответствующие наболу полей из courseSearchDto
     *
     * @param courseSearchDto набор параметров, по которым осуществляется поиск
     * @param pageable  настройка отображения результата
     * @return  страница найденных курсов, соотвествующая настройкам объекта pageable
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    PageDto<CourseDto> getCourses(CourseSearchDto courseSearchDto, Pageable pageable);

    /**
     * Изменяет статус курсов, у которых дата окончания меньше текущей
     */
    void changeOldCourseStatus();
}
