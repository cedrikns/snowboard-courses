package ru.tsedrik.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tsedrik.domain.CourseLocation;

/**
 * Интерфейс управления персистентным состоянием объектов типа CourseLocation
 */
@Repository
public interface LocationRepository extends JpaRepository<CourseLocation, Long> {
}
