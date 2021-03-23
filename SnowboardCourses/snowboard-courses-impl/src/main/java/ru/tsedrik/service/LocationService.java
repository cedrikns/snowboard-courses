package ru.tsedrik.service;

import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.tsedrik.domain.Location;
import ru.tsedrik.resource.dto.LocationDto;
import ru.tsedrik.resource.dto.LocationSearchDto;

/**
 * LocationService представляет интерфейс взаимодействия с классом Location
 */
public interface LocationService {

    /**
     * Добавляет новое место проведения курсов.
     *
     * @param locationDto    новое место проведения курсов, которое было добавлено
     */
    @PreAuthorize("hasPermission(T(ru.tsedrik.security.BusinessOperation).LOCATION_CREATE, \"Вы не можете создавать место проведения курса\")")
    Mono<LocationDto> addLocation(LocationDto locationDto);

    /**
     * Удаляет существующее место проведения курса.
     *
     * @param location    существующее место проведения курса, которое будет удалено
     * @return  успешно ли прошло удаление
     */
    @PreAuthorize("hasPermission(T(ru.tsedrik.security.BusinessOperation).LOCATION_DELETE, \"Вы не можете удалять место проведения курса\")")
    Mono<Boolean> deleteLocation(Location location);

    /**
     * Удаляет существующее место проведения курса по его идентификатору.
     *
     * @param id    идентификатор удаляемого места проведения курса
     * @return  успешно ли прошло удаление
     */
    @PreAuthorize("hasPermission(T(ru.tsedrik.security.BusinessOperation).LOCATION_DELETE, \"Вы не можете удалять место проведения курса\")")
    Mono<Boolean> deleteLocationById(Long id);

    /**
     * Запрашивает место проведения курса по его идентификатору.
     *
     * @param id    идентификатор места проведения курса
     * @return  найденное место проведения курса
     */
    @PreAuthorize("hasPermission(T(ru.tsedrik.security.BusinessOperation).LOCATION_VIEW, \"Вы не можете просматривать место проведения курса\")")
    Mono<LocationDto> getLocationById(Long id);

    /**
     * Обновляет место проведения курса.
     *
     * @param locationDto    место проведения курса, которое будет обновлено
     */
    @PreAuthorize("hasPermission(T(ru.tsedrik.security.BusinessOperation).LOCATION_UPDATE, \"Вы не можете изменять место проведения курса\")")
    Mono<LocationDto> updateLocation(LocationDto locationDto);

    /**
     * Получает все места проведения курсов, соответствующие наболу полей из locationSearchDto
     *
     * @param locationSearchDto набор параметров, по которым осуществляется поиск
     * @param pageable  настройка отображения результата
     * @return  страница найденных мест проведения курсов, соотвествующая настройкам объекта pageable
     */
    @PreAuthorize("hasPermission(T(ru.tsedrik.security.BusinessOperation).LOCATION_VIEW, \"Вы не можете просматривать места проведения курса\")")
    Flux<LocationDto> getLocations(LocationSearchDto locationSearchDto, Pageable pageable);
}
