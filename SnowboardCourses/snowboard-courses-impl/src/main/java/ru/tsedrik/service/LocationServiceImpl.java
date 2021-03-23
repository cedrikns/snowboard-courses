package ru.tsedrik.service;

import ma.glasnost.orika.MapperFacade;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.tsedrik.exception.LocationNotFoundException;
import ru.tsedrik.domain.Location;
import ru.tsedrik.repository.LocationRepository;
import ru.tsedrik.resource.dto.LocationDto;
import ru.tsedrik.resource.dto.LocationSearchDto;

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
    public Mono<LocationDto> addLocation(LocationDto locationDto) {
        Location location = mapperFacade.map(locationDto, Location.class);
        location.setId(System.currentTimeMillis());
        Mono<Void> dbRequest = Mono.fromRunnable(() -> locationRepository.save(location));
        return Mono.when(dbRequest).then(Mono.fromSupplier(() -> mapperFacade.map(location, LocationDto.class)));
    }

    @Override
    public Mono<Boolean> deleteLocation(Location location) {
        return Mono.fromRunnable(() -> locationRepository.delete(location)).thenReturn(true);
    }

    @Override
    public Mono<Boolean> deleteLocationById(Long id) {
        return Mono.fromCallable(() -> {
            locationRepository.deleteById(id);
            return true;
        });
    }

    @Override
    public Mono<LocationDto> getLocationById(Long id) {
        return Mono.fromSupplier(() ->
            locationRepository.findById(id)
                    .map(location -> mapperFacade.map(location, LocationDto.class))
                    .orElseThrow(() -> new LocationNotFoundException("There wasn't found course location with id = " + id))
        );
    }

    @Override
    public Mono<LocationDto> updateLocation(LocationDto locationDto) {
        Mono<Location> foundedLocation = Mono.fromSupplier(() -> {
            Location location = locationRepository.findById(locationDto.getId())
                    .orElseThrow(() -> new LocationNotFoundException("There wasn't found course location with id = " + locationDto.getId()));
            System.out.println("From DB: " + location);
            mapperFacade.map(locationDto, location);
            System.out.println("After map: " + location);
            locationRepository.save(location);
            return location;
        });

        return foundedLocation.flatMap(location -> Mono.just(mapperFacade.map(location, LocationDto.class)));
    }

    @Override
    public Flux<LocationDto> getLocations(LocationSearchDto locationSearchDto, Pageable pageable) {
        return Flux.fromIterable(locationRepository.findAll(getSpecification(locationSearchDto), pageable)
                .map(location -> mapperFacade.map(location, LocationDto.class))
                .toList());
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
