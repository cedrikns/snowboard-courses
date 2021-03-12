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
    @PreAuthorize("hasPermission(T(ru.tsedrik.security.BusinessOperation).COURSE_CREATE, \"Вы не можете создавать курс\")")
    CourseDto addCourse(CourseDto courseDto);

    /**
     * Удаляет существующий курс.
     *
     * @param course    существующий курс, который будет удален
     * @return  успешно ли прошло удаление
     */
    @PreAuthorize("hasPermission(T(ru.tsedrik.security.BusinessOperation).COURSE_DELETE, \"Вы не можете удалять курс\")")
    boolean deleteCourse(Course course);

    /**
     * Удаляет существующий курс по его идентификатору.
     *
     * @param id    идентификатор удаляемого курса
     * @return  успешно ли прошло удаление
     */
    @PreAuthorize("hasPermission(T(ru.tsedrik.security.BusinessOperation).COURSE_DELETE, \"Вы не можете удалять курс\")")
    boolean deleteCourseById(Long id);

    /**
     * Запрашивает курс по его идентификатору.
     *
     * @param id    идентификатор запрашиваемого курса
     * @return  найденный курс
     */
    @PreAuthorize("hasPermission(T(ru.tsedrik.security.BusinessOperation).COURSE_VIEW, \"Вы не можете просматривать курс\")")
    CourseDto getCourseById(Long id);

    /**
     * Записывает Участника на конкретный курс
     * @param courseId  идентификатор курса, на который будет записан Участник
     * @param personId  идентификатор Участника
     * @return  Обновленный курс
     */
    @PreAuthorize("hasPermission(T(ru.tsedrik.security.BusinessOperation).COURSE_ENROLL, \"Вы не можете делать записи на курс\")")
    CourseDto enroll(Long courseId, Long personId);

    /**
     * Получает все курсы, соответствующие наболу полей из courseSearchDto
     *
     * @param courseSearchDto набор параметров, по которым осуществляется поиск
     * @param pageable  настройка отображения результата
     * @return  страница найденных курсов, соотвествующая настройкам объекта pageable
     */
    @PreAuthorize("hasPermission(T(ru.tsedrik.security.BusinessOperation).COURSE_VIEW, \"Вы не можете просматривать курсы\")")
    PageDto<CourseDto> getCourses(CourseSearchDto courseSearchDto, Pageable pageable);

    /**
     * Изменяет статус курсов, у которых дата окончания меньше текущей
     */
    void changeOldCourseStatus();
}
