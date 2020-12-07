package ru.tsedrik.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.tsedrik.controller.dto.CourseDto;
import ru.tsedrik.service.CourseService;

import java.net.URI;

/**
 * Controller for Course
 */
@RestController
@RequestMapping("/course")
public class CourseController {

    private CourseService courseService;

    public CourseController(CourseService courseService){
        this.courseService = courseService;
    }

    @PostMapping
    public ResponseEntity<CourseDto> createCourse(@RequestBody CourseDto courseDto, UriComponentsBuilder uriComponentsBuilder){
        CourseDto resultCourseDto = courseService.addCourse(courseDto);
        URI uri = uriComponentsBuilder.path("/course/" + resultCourseDto.getId()).buildAndExpand(resultCourseDto).toUri();
        return ResponseEntity.created(uri).body(resultCourseDto);
    }

    @GetMapping(value = "/{id}")
    public CourseDto getCourse(@PathVariable Long id){
        CourseDto courseDto = courseService.getCourseById(id);
        return courseDto;
    }

    @DeleteMapping(value = "/{id}")
    public boolean deleteCourse(@PathVariable Long id){
        boolean isDeleted = courseService.deleteCourseById(id);
        return isDeleted;
    }

    @PostMapping(value = "/enroll")
    public CourseDto enrollCourse(@RequestParam Long courseId, Long personId){
        CourseDto courseDto = courseService.enroll(courseId, personId);
        return courseDto;
    }

}
