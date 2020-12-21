package ru.tsedrik.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.tsedrik.controller.dto.LocationDto;
import ru.tsedrik.controller.dto.LocationSearchDto;
import ru.tsedrik.controller.dto.PageDto;
import ru.tsedrik.service.LocationService;

import java.net.URI;

/**
 * Controller for Location
 */
@RestController
@RequestMapping("/api/v1/location")
public class LocationController {
    private LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @PostMapping
    public ResponseEntity<LocationDto> createLocation(@RequestBody LocationDto locationDto, UriComponentsBuilder uriComponentsBuilder){
        LocationDto resultLocationDto = locationService.addLocation(locationDto);
        URI uri = uriComponentsBuilder.path("/api/v1/location/" + resultLocationDto.getId()).buildAndExpand(resultLocationDto).toUri();
        return ResponseEntity.created(uri).body(resultLocationDto);
    }

    @GetMapping(value = "/{id}")
    public LocationDto getLocation(@PathVariable Long id){
        LocationDto locationDto = locationService.getLocationById(id);
        return locationDto;
    }

    @DeleteMapping(value = "/{id}")
    public boolean deleteLocation(@PathVariable Long id){
        boolean isDeleted = locationService.deleteLocationById(id);
        return isDeleted;
    }

    @PutMapping(value = "/{id}")
    public LocationDto updateLocation(@PathVariable Long id, @RequestBody LocationDto locationDto){
        if (!id.equals(locationDto.getId())){
            throw new IllegalArgumentException("Идентификатор в пути запроса " + id + " не совпадает с идентификатором в теле запроса " + locationDto.getId());
        }
        locationService.updateLocation(locationDto);
        return locationDto;
    }

    @GetMapping
    public PageDto<LocationDto> getLocations(@RequestBody LocationSearchDto locationSearchDto,
                                             @PageableDefault(value = 5) @SortDefault(value = "id") Pageable pageable){
        return locationService.getLocations(locationSearchDto, pageable);
    }
}
