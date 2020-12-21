package ru.tsedrik.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.tsedrik.controller.dto.CourseLocationDto;
import ru.tsedrik.service.LocationService;

import java.net.URI;

/**
 * Controller for CourseLocation
 */
@RestController
@RequestMapping("/api/v1/location")
public class CourseLocationController {
    private LocationService locationService;

    public CourseLocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @PostMapping
    public ResponseEntity<CourseLocationDto> createCourseLocation(@RequestBody CourseLocationDto courseLocationDto, UriComponentsBuilder uriComponentsBuilder){
        CourseLocationDto resultCourseLocationDto = locationService.addLocation(courseLocationDto);
        URI uri = uriComponentsBuilder.path("/api/v1/location/" + resultCourseLocationDto.getId()).buildAndExpand(resultCourseLocationDto).toUri();
        return ResponseEntity.created(uri).body(resultCourseLocationDto);
    }

    @GetMapping(value = "/{id}")
    public CourseLocationDto getCourseLocation(@PathVariable Long id){
        CourseLocationDto locationDto = locationService.getLocationById(id);
        return locationDto;
    }

    @DeleteMapping(value = "/{id}")
    public boolean deleteCourseLocation(@PathVariable Long id){
        boolean isDeleted = locationService.deleteLocationById(id);
        return isDeleted;
    }

    @PutMapping(value = "/{id}")
    public CourseLocationDto updateCourseLocation(@PathVariable Long id, @RequestBody CourseLocationDto locationDto){
        if (!id.equals(locationDto.getId())){
            throw new IllegalArgumentException("Идентификатор в пути запроса " + id + " не совпадает с идентификатором в теле запроса " + locationDto.getId());
        }
        locationService.updateLocation(locationDto);
        return locationDto;
    }
}
