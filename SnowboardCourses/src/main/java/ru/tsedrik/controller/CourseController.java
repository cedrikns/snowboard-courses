package ru.tsedrik.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.tsedrik.controller.dto.CourseDto;
import ru.tsedrik.service.CourseService;

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
    @ResponseStatus(HttpStatus.CREATED)
    public CourseDto createCourse(@RequestBody CourseDto courseDto){
        courseService.addCourse(courseDto);
        return courseDto;
    }

    @GetMapping(value = "/{id}")
    public CourseDto getPerson(@PathVariable Long id){
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
