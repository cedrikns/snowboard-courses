package ru.tsedrik.dao;

import ru.tsedrik.model.CourseLocation;

/**
 * Интерфейс управления персистентным состоянием объектов типа Location
 */
public interface LocationDAO extends GenericDAO<CourseLocation, Long> {

}
