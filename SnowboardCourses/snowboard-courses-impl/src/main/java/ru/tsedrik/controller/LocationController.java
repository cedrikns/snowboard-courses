package ru.tsedrik.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
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
    private LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @Override
    public ResponseEntity<LocationDto> createLocation(@RequestBody LocationDto locationDto, UriComponentsBuilder uriComponentsBuilder){
        LocationDto resultLocationDto = locationService.addLocation(locationDto);
        URI uri = uriComponentsBuilder.path("/api/v1/location/" + resultLocationDto.getId()).buildAndExpand(resultLocationDto).toUri();
        return ResponseEntity.created(uri).body(resultLocationDto);
    }

    @Override
    public LocationDto getLocation(@PathVariable Long id){
        LocationDto locationDto = locationService.getLocationById(id);
        return locationDto;
    }

    @Override
    public boolean deleteLocation(@PathVariable Long id){
        boolean isDeleted = locationService.deleteLocationById(id);
        return isDeleted;
    }

    @Override
    public LocationDto updateLocation(@PathVariable Long id, @RequestBody LocationDto locationDto){
        if (!id.equals(locationDto.getId())){
            throw new IllegalArgumentException("Идентификатор в пути запроса " + id + " не совпадает с идентификатором в теле запроса " + locationDto.getId());
        }
        locationService.updateLocation(locationDto);
        return locationDto;
    }

    @Override
    public PageDto<LocationDto> getLocations(@RequestBody LocationSearchDto locationSearchDto,
                                             @PageableDefault(value = 5) @SortDefault(value = "id") Pageable pageable){
        return locationService.getLocations(locationSearchDto, pageable);
    }
}
