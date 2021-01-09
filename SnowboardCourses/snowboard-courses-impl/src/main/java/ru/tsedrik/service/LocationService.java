package ru.tsedrik.service;

import org.springframework.data.domain.Pageable;
import ru.tsedrik.domain.Location;
import ru.tsedrik.resource.dto.LocationDto;
import ru.tsedrik.resource.dto.LocationSearchDto;
import ru.tsedrik.resource.dto.PageDto;

/**
 * LocationService представляет интерфейс взаимодействия с классом Location
 */
public interface LocationService {

    /**
     * Добавляет новое место проведения курсов.
     *
     * @param locationDto    новое место проведения курсов, которое было добавлено
     */
    LocationDto addLocation(LocationDto locationDto);

    /**
     * Удаляет существующее место проведения курса.
     *
     * @param location    существующее место проведения курса, которое будет удалено
     * @return  успешно ли прошло удаление
     */
    boolean deleteLocation(Location location);

    /**
     * Удаляет существующее место проведения курса по его идентификатору.
     *
     * @param id    идентификатор удаляемого места проведения курса
     * @return  успешно ли прошло удаление
     */
    boolean deleteLocationById(Long id);

    /**
     * Запрашивает место проведения курса по его идентификатору.
     *
     * @param id    идентификатор места проведения курса
     * @return  найденное место проведения курса
     */
    LocationDto getLocationById(Long id);

    /**
     * Обновляет место проведения курса.
     *
     * @param locationDto    место проведения курса, которое будет обновлено
     */
    LocationDto updateLocation(LocationDto locationDto);

    /**
     * Получает все места проведения курсов, соответствующие наболу полей из locationSearchDto
     *
     * @param locationSearchDto набор параметров, по которым осуществляется поиск
     * @param pageable  настройка отображения результата
     * @return  страница найденных мест проведения курсов, соотвествующая настройкам объекта pageable
     */
    PageDto<LocationDto> getLocations(LocationSearchDto locationSearchDto, Pageable pageable);
}
