package ru.tsedrik.service;

import ma.glasnost.orika.MapperFacade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tsedrik.exception.LocationNotFoundException;
import ru.tsedrik.domain.Location;
import ru.tsedrik.repository.LocationRepository;
import ru.tsedrik.resource.dto.LocationDto;
import ru.tsedrik.resource.dto.LocationSearchDto;
import ru.tsedrik.resource.dto.PageDto;

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

    /**
     * Объект для маппинга между классами Location и LocationDto
     */
    private MapperFacade mapperFacade;

    public LocationServiceImpl(LocationRepository locationRepository, MapperFacade mapperFacade) {
        this.locationRepository = locationRepository;
        this.mapperFacade = mapperFacade;
    }

    @Override
    public LocationDto addLocation(LocationDto locationDto) {
        Location location = mapperFacade.map(locationDto, Location.class);
        location.setId(System.currentTimeMillis());
        locationRepository.save(location);
        locationDto = mapperFacade.map(location, LocationDto.class);
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
                .map(location -> mapperFacade.map(location, LocationDto.class))
                .orElseThrow(() -> new LocationNotFoundException("There wasn't found course location with id = " + id));

        return locationDto;
    }

    @Override
    public LocationDto updateLocation(LocationDto locationDto) {
        Location location = locationRepository.findById(locationDto.getId())
                .orElseThrow(() -> new LocationNotFoundException("There wasn't found course location with id = " + locationDto.getId()));

        mapperFacade.map(locationDto, location);

        locationRepository.save(location);

        return mapperFacade.map(location, LocationDto.class);
    }

    @Override
    public PageDto<LocationDto> getLocations(LocationSearchDto locationSearchDto, Pageable pageable) {
        Page<Location> page = locationRepository.findAll(getSpecification(locationSearchDto), pageable);

        List<LocationDto> locations = page
                .map(location -> mapperFacade.map(location, LocationDto.class))
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
