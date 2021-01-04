package ru.tsedrik.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.tsedrik.aspect.annotation.Audit;
import ru.tsedrik.aspect.annotation.AuditCode;
import ru.tsedrik.resource.LocationResource;
import ru.tsedrik.resource.dto.LocationDto;
import ru.tsedrik.resource.dto.LocationSearchDto;
import ru.tsedrik.resource.dto.PageDto;
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
    public ResponseEntity<LocationDto> createLocation(@RequestBody LocationDto locationDto, UriComponentsBuilder uriComponentsBuilder){
        logger.debug("createLocation with {} - start ", locationDto);
        LocationDto resultLocationDto = locationService.addLocation(locationDto);
        URI uri = uriComponentsBuilder.path("/api/v1/location/" + resultLocationDto.getId()).buildAndExpand(resultLocationDto).toUri();
        logger.debug("createLocation end with result {}", resultLocationDto);
        return ResponseEntity.created(uri).body(resultLocationDto);
    }

    @Override
    public LocationDto getLocation(@PathVariable Long id){
        logger.debug("getLocation with {} - start ", id);
        LocationDto locationDto = locationService.getLocationById(id);
        logger.debug("getLocation end with result {}", locationDto);
        return locationDto;
    }

    @Audit(AuditCode.LOCATION_DELETE)
    @Override
    public boolean deleteLocation(@PathVariable Long id){
        logger.debug("deleteLocation with {} - start ", id);
        boolean isDeleted = locationService.deleteLocationById(id);
        logger.debug("deleteLocation end with result {}", isDeleted);
        return isDeleted;
    }

    @Audit(AuditCode.LOCATION_UPDATE)
    @Override
    public LocationDto updateLocation(@PathVariable Long id, @RequestBody LocationDto locationDto){
        logger.debug("updateLocation with {}, {} - start ", id, locationDto);
        if (!id.equals(locationDto.getId())){
            throw new IllegalArgumentException("Идентификатор в пути запроса " + id + " не совпадает с идентификатором в теле запроса " + locationDto.getId());
        }
        locationService.updateLocation(locationDto);
        logger.debug("updateLocation end with result {}", locationDto);
        return locationDto;
    }

    @Override
    public PageDto<LocationDto> getLocations(@RequestBody LocationSearchDto locationSearchDto,
                                             @PageableDefault(value = 5) @SortDefault(value = "id") Pageable pageable){
        logger.debug("getLocations with {}, {} - start ", locationSearchDto, pageable);
        PageDto<LocationDto> result = locationService.getLocations(locationSearchDto, pageable);
        logger.debug("getLocations end with result {}", result);
        return result;
    }
}
