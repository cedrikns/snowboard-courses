package ru.tsedrik.controller;

import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public CourseController(CourseService courseService){
        this.courseService = courseService;
    }

    @PostMapping
    public CourseDto createCourse(@RequestBody CourseDto courseDto){
        courseService.addCourse(courseDto);
        return courseDto;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public CourseDto getPerson(@PathVariable Long id){
        CourseDto courseDto = courseService.getCourseById(id);
        return courseDto;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public CourseDto deleteCourse(@PathVariable Long id){
        CourseDto courseDto = courseService.deleteCourseById(id);
        return courseDto;
    }

    @PostMapping(value = "/enroll")
    public CourseDto createCourse(@RequestParam Long courseId, Long personId){
        CourseDto courseDto = courseService.enroll(courseId, personId);
        return courseDto;
    }

}
