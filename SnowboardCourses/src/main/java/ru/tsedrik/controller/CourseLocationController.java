package ru.tsedrik.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.tsedrik.controller.dto.CourseLocationDto;
import ru.tsedrik.service.LocationService;

/**
 * Controller for CourseLocation
 */
@RestController
@RequestMapping("/courseLocation")
public class CourseLocationController {
    private LocationService locationService;

    @Autowired
    public CourseLocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CourseLocationDto createCourseLocation(@RequestBody CourseLocationDto courseLocationDto){
        locationService.addLocation(courseLocationDto);
        return courseLocationDto;
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

    @PutMapping
    public CourseLocationDto updateCourseLocation(@RequestBody CourseLocationDto locationDto){
        locationService.updateLocation(locationDto);
        return locationDto;
    }
}
