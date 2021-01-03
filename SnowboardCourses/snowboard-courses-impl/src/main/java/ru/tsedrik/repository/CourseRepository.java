package ru.tsedrik.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.tsedrik.domain.Course;
import ru.tsedrik.domain.CourseStatus;

import java.time.LocalDate;

/**
 * Интерфейс управления персистентным состоянием объектов типа Course
 */
@Repository
public interface CourseRepository extends JpaRepository<Course, Long>, JpaSpecificationExecutor<Course> {

    /**
     * Обновление статуса курсов
     * @param date  дата, у курсов старше которой будет изменен статус
     * @param courseStatus  статус, на который нужно изменить статус курсов
     */
    @Modifying
    @Query("update Course c set c.courseStatus = :courseStatus where c.endDate < :date")
    void updateClosedCourseStatus(LocalDate date, CourseStatus courseStatus);
}
