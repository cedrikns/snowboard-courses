package ru.tsedrik.service;

import ru.tsedrik.controller.dto.CourseLocationDto;
import ru.tsedrik.model.CourseLocation;

/**
 * LocationService представляет интерфейс взаимодействия с классом CourseLocation
 */
public interface LocationService {

    /**
     * Добавляет новое место проведения курсов.
     *
     * @param locationDto    новое место проведения курсов, которое было добавлено
     */
    CourseLocationDto addLocation(CourseLocationDto locationDto);

    /**
     * Удаляет существующее место проведения курса.
     *
     * @param location    существующее место проведения курса, которое будет удалено
     * @return  удаленное место проведения
     */
    CourseLocation deleteLocation(CourseLocation location);

    /**
     * Удаляет существующее место проведения курса по его идентификатору.
     *
     * @param id    идентификатор удаляемого места проведения курса
     * @return  было ли удалено место проведения курса по его идентификатору
     */
    boolean deleteLocationById(Long id);

    /**
     * Запрашивает место проведения курса по его идентификатору.
     *
     * @param id    идентификатор места проведения курса
     * @return  найденное место проведения курса
     */
    CourseLocationDto getLocationById(Long id);

    /**
     * Обновляет место проведения курса.
     *
     * @param id    идентификатор места проведения, которое будет обновлено
     * @param locationDto    место проведения курса, которое будет обновлено
     */
    CourseLocationDto updateLocation(Long id, CourseLocationDto locationDto);
}
