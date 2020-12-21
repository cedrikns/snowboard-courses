package ru.tsedrik.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tsedrik.controller.dto.*;
import ru.tsedrik.exception.LocationNotFoundException;
import ru.tsedrik.domain.Location;
import ru.tsedrik.repository.LocationRepository;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * Реализация интерфейса LocationService
 */
@Service
@Transactional
public class LocationServiceImpl implements LocationService{
    /**
     * Объект для управления персистентным состоянием объектов типа Location
     */
    private LocationRepository locationRepository;

    public LocationServiceImpl(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public LocationDto addLocation(LocationDto locationDto) {
        Location location = new Location(
                System.currentTimeMillis(), locationDto.getName(),
                locationDto.getCountry(), locationDto.getCity()
        );
        locationRepository.save(location);
        locationDto.setId(location.getId());

        return locationDto;
    }

    @Override
    public boolean deleteLocation(Location location) {
        locationRepository.delete(location);
        return true;
    }

    @Override
    public boolean deleteLocationById(Long id) {
        locationRepository.deleteById(id);
        return true;
    }

    @Override
    public LocationDto getLocationById(Long id) {
        LocationDto locationDto = locationRepository.findById(id)
                .map(location -> new LocationDto(
                        location.getId(), location.getName(),
                        location.getCountry(), location.getCity()))
                .orElseThrow(() -> new LocationNotFoundException("There wasn't found course location with id = " + id));

        return locationDto;
    }

    @Override
    public LocationDto updateLocation(LocationDto locationDto) {
        Location location = locationRepository.findById(locationDto.getId())
                .orElseThrow(() -> new LocationNotFoundException("There wasn't found course location with id = " + locationDto.getId()));

        location.setName(locationDto.getName());
        location.setCountry(locationDto.getCountry());
        location.setCity(locationDto.getCity());

        locationRepository.save(location);

        return locationDto;
    }

    @Override
    public PageDto<LocationDto> getLocations(LocationSearchDto locationSearchDto, Pageable pageable) {
        Page<Location> page = locationRepository.findAll(getSpecification(locationSearchDto), pageable);

        List<LocationDto> locations = page
                .map(location ->
                        new LocationDto(location.getId(), location.getName(),
                                location.getCity(), location.getCountry())
                )
                .toList();

        return new PageDto<>(locations, page.getTotalElements());
    }

    private Specification<Location> getSpecification(LocationSearchDto locationSearchDto) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (locationSearchDto.getName() != null) {
                predicates.add(root.get("name").in(locationSearchDto.getName()));
            }

            if (locationSearchDto.getCity() != null) {
                predicates.add(root.get("city").in(locationSearchDto.getCity()));
            }

            if (locationSearchDto.getCountry() != null){
                predicates.add(root.get("country").in(locationSearchDto.getCountry()));
            }

            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}
