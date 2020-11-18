package ru.tsedrik.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tsedrik.controller.dto.CourseLocationDto;
import ru.tsedrik.dao.LocationDAO;
import ru.tsedrik.exception.CourseLocationNotFoundException;
import ru.tsedrik.model.CourseLocation;

/**
 * Реализация интерфейса LocationService
 */
@Service
@Transactional
public class LocationServiceImpl implements LocationService{
    /**
     * Объект для управления персистентным состоянием объектов типа CourseLocation
     */
    private LocationDAO locationDAO;

    public LocationServiceImpl(LocationDAO locationDAO) {
        this.locationDAO = locationDAO;
    }

    @Override
    public CourseLocationDto addLocation(CourseLocationDto locationDto) {
        CourseLocation location = new CourseLocation(
                System.currentTimeMillis(), locationDto.getName(),
                locationDto.getCountry(), locationDto.getCity()
        );
        locationDAO.create(location);
        locationDto.setId(location.getId());
        return locationDto;
    }

    @Override
    public CourseLocation deleteLocation(CourseLocation location) {
        return locationDAO.delete(location);
    }

    @Override
    public boolean deleteLocationById(Long id) {
        return locationDAO.deleteById(id);
    }

    @Override
    public CourseLocationDto getLocationById(Long id) {
        CourseLocation location = locationDAO.getById(id);
        if (location == null){
            throw new CourseLocationNotFoundException("There wasn't found course location with id = " + id);
        }
        CourseLocationDto locationDto = new CourseLocationDto(
                location.getId(), location.getName(),
                location.getCountry(), location.getCity()
        );
        return locationDto;
    }

    @Override
    public CourseLocationDto updateLocation(CourseLocationDto locationDto) {
        CourseLocation location = locationDAO.getById(locationDto.getId());
        if (location == null){
            throw new CourseLocationNotFoundException("There wasn't found course location with id = " + locationDto.getId());
        }
        location.setName(locationDto.getName());
        location.setCountry(locationDto.getCountry());
        location.setCity(locationDto.getCity());

        locationDAO.update(location);
        return locationDto;
    }
}
