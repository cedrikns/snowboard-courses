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
import ru.tsedrik.resource.CourseResource;
import ru.tsedrik.resource.dto.CourseDto;
import ru.tsedrik.resource.dto.CourseSearchDto;
import ru.tsedrik.resource.dto.PageDto;
import ru.tsedrik.service.CourseService;

import java.net.URI;

/**
 * Controller for Course
 */
@RestController
public class CourseController implements CourseResource {

    private static final Logger logger = LoggerFactory.getLogger(CourseController.class.getName());

    private CourseService courseService;

    public CourseController(CourseService courseService){
        this.courseService = courseService;
    }

    @Audit(AuditCode.COURSE_CREATE)
    @Override
    public ResponseEntity<CourseDto> createCourse(@RequestBody CourseDto courseDto, UriComponentsBuilder uriComponentsBuilder){
        logger.debug("createCourse with {} - start ", courseDto);
        CourseDto resultCourseDto = courseService.addCourse(courseDto);
        URI uri = uriComponentsBuilder.path("/api/v1/course/" + resultCourseDto.getId()).buildAndExpand(resultCourseDto).toUri();
        logger.debug("createCourse end with result {}", resultCourseDto);
        return ResponseEntity.created(uri).body(resultCourseDto);
    }

    @Override
    public CourseDto getCourse(@PathVariable Long id){
        logger.debug("getCourse with {} - start ", id);
        CourseDto courseDto = courseService.getCourseById(id);
        logger.debug("getCourse end with result {}", courseDto);
        return courseDto;
    }

    @Audit(AuditCode.COURSE_DELETE)
    @Override
    public boolean deleteCourse(@PathVariable Long id){
        logger.debug("deleteCourse with {} - start ", id);
        boolean isDeleted = courseService.deleteCourseById(id);
        logger.debug("deleteCourse end with result {}", isDeleted);
        return isDeleted;
    }

    @Override
    public PageDto<CourseDto> getCourses(@RequestBody CourseSearchDto courseSearchDto,
                                         @PageableDefault(value = 5) @SortDefault(value = "id") Pageable pageable){
        logger.debug("getCourses with {}, {} - start ", courseSearchDto, pageable);
        PageDto<CourseDto> result = courseService.getCourses(courseSearchDto, pageable);
        logger.debug("getCourses end with result {}", result);
        return result;
    }

    @Audit(AuditCode.COURSE_UPDATE)
    @Override
    public CourseDto enrollCourse(@RequestParam Long courseId, Long personId){
        logger.debug("enrollCourse with {}, {} - start ", courseId, personId);
        CourseDto courseDto = courseService.enroll(courseId, personId);
        logger.debug("enrollCourse end with result {}", courseDto);
        return courseDto;
    }

}
