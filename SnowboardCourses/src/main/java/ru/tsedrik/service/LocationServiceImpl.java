package ru.tsedrik.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tsedrik.controller.dto.CourseLocationDto;
import ru.tsedrik.exception.CourseLocationNotFoundException;
import ru.tsedrik.domain.CourseLocation;
import ru.tsedrik.repository.LocationRepository;

/**
 * Реализация интерфейса LocationService
 */
@Service
@Transactional
public class LocationServiceImpl implements LocationService{
    /**
     * Объект для управления персистентным состоянием объектов типа CourseLocation
     */
    private LocationRepository locationRepository;

    public LocationServiceImpl(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public CourseLocationDto addLocation(CourseLocationDto locationDto) {
        CourseLocation location = new CourseLocation(
                System.currentTimeMillis(), locationDto.getName(),
                locationDto.getCountry(), locationDto.getCity()
        );
        locationRepository.save(location);
        locationDto.setId(location.getId());

        return locationDto;
    }

    @Override
    public boolean deleteLocation(CourseLocation location) {
        locationRepository.delete(location);
        return true;
    }

    @Override
    public boolean deleteLocationById(Long id) {
        locationRepository.deleteById(id);
        return true;
    }

    @Override
    public CourseLocationDto getLocationById(Long id) {
        CourseLocationDto locationDto = locationRepository.findById(id)
                .map(location -> new CourseLocationDto(
                        location.getId(), location.getName(),
                        location.getCountry(), location.getCity()))
                .orElseThrow(() -> new CourseLocationNotFoundException("There wasn't found course location with id = " + id));

        return locationDto;
    }

    @Override
    public CourseLocationDto updateLocation(CourseLocationDto locationDto) {
        CourseLocation location = locationRepository.findById(locationDto.getId())
                .orElseThrow(() -> new CourseLocationNotFoundException("There wasn't found course location with id = " + locationDto.getId()));

        location.setName(locationDto.getName());
        location.setCountry(locationDto.getCountry());
        location.setCity(locationDto.getCity());

        locationRepository.save(location);

        return locationDto;
    }
}
