package ru.tsedrik.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.tsedrik.controller.dto.CourseDto;
import ru.tsedrik.controller.dto.CourseSearchDto;
import ru.tsedrik.controller.dto.PageDto;
import ru.tsedrik.service.CourseService;

import java.net.URI;

/**
 * Controller for Course
 */
@RestController
@RequestMapping("/api/v1/course")
public class CourseController {

    private CourseService courseService;

    public CourseController(CourseService courseService){
        this.courseService = courseService;
    }

    @PostMapping
    public ResponseEntity<CourseDto> createCourse(@RequestBody CourseDto courseDto, UriComponentsBuilder uriComponentsBuilder){
        CourseDto resultCourseDto = courseService.addCourse(courseDto);
        URI uri = uriComponentsBuilder.path("/api/v1/course/" + resultCourseDto.getId()).buildAndExpand(resultCourseDto).toUri();
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

    @GetMapping
    public PageDto<CourseDto> getCourses(@RequestBody CourseSearchDto courseSearchDto,
                                         @PageableDefault(value = 5) @SortDefault(value = "id") Pageable pageable){
        return courseService.getCourses(courseSearchDto, pageable);
    }

    @PostMapping(value = "/enroll")
    public CourseDto enrollCourse(@RequestParam Long courseId, Long personId){
        CourseDto courseDto = courseService.enroll(courseId, personId);
        return courseDto;
    }

}
