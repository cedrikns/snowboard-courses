package ru.tsedrik.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.tsedrik.aspect.annotation.Audit;
import ru.tsedrik.aspect.annotation.AuditCode;
import ru.tsedrik.resource.LocationResource;
import ru.tsedrik.resource.dto.LocationDto;
import ru.tsedrik.resource.dto.LocationSearchDto;
import ru.tsedrik.service.LocationService;

import java.net.URI;

/**
 * Controller for Location
 */
@RestController
public class LocationController implements LocationResource {

    private static final Logger logger = LoggerFactory.getLogger(LocationController.class.getName());

    private LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @Audit(AuditCode.LOCATION_CREATE)
    @Override
    public Mono<ResponseEntity<LocationDto>> createLocation(@RequestBody LocationDto locationDto, UriComponentsBuilder uriComponentsBuilder){
        logger.debug("createLocation with {} - start ", locationDto);
        Mono<LocationDto> resultLocationDto = locationService.addLocation(locationDto);

        return resultLocationDto.flatMap(createdLocation -> {
            URI uri = uriComponentsBuilder.path("/api/v1/location/" + createdLocation.getId()).buildAndExpand(createdLocation).toUri();
            return Mono.just(ResponseEntity.created(uri).body(createdLocation));
        }).doOnSuccess(result -> logger.debug("createLocation end with result {}", result));
    }

    @Override
    public Mono<LocationDto> getLocation(@PathVariable Long id){
        logger.debug("getLocation with {} - start ", id);
        Mono<LocationDto> locationDto = locationService.getLocationById(id);
        return locationDto.doOnSuccess(result -> logger.debug("getLocation end with result {}", result));
    }

    @Audit(AuditCode.LOCATION_DELETE)
    @Override
    public Mono<Boolean> deleteLocation(@PathVariable Long id){
        logger.debug("deleteLocation with {} - start ", id);
        Mono<Boolean> isDeleted = locationService.deleteLocationById(id);
        return isDeleted.doOnSuccess(result -> logger.debug("deleteLocation end with result {}", result));
    }

    @Audit(AuditCode.LOCATION_UPDATE)
    @Override
    public Mono<LocationDto> updateLocation(@PathVariable Long id, @RequestBody LocationDto locationDto){
        logger.debug("updateLocation with {}, {} - start ", id, locationDto);
        if (!id.equals(locationDto.getId())){
            throw new IllegalArgumentException("Идентификатор в пути запроса " + id + " не совпадает с идентификатором в теле запроса " + locationDto.getId());
        }
        Mono<LocationDto> updatedLocationDto = locationService.updateLocation(locationDto);
        return updatedLocationDto.doOnSuccess(result -> logger.debug("updateLocation end with result {}", result));
    }

    @Override
    public Flux<LocationDto> getLocations(@RequestBody LocationSearchDto locationSearchDto,
                                                   @PageableDefault(value = 5) @SortDefault(value = "id") Pageable pageable){
        logger.debug("getLocations with {}, {} - start ", locationSearchDto, pageable);
        Flux<LocationDto> result = locationService.getLocations(locationSearchDto, pageable);
        return result;
    }
}
