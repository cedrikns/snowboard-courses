package ru.tsedrik.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.tsedrik.aspect.annotation.Audit;
import ru.tsedrik.aspect.annotation.AuditCode;
import ru.tsedrik.resource.CourseResource;
import ru.tsedrik.resource.dto.CourseDto;
import ru.tsedrik.resource.dto.CourseSearchDto;
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
    public Mono<ResponseEntity<CourseDto>> createCourse(@RequestBody CourseDto courseDto, UriComponentsBuilder uriComponentsBuilder){
        logger.debug("createCourse with {} - start ", courseDto);
        Mono<CourseDto> resultCourseDto = courseService.addCourse(courseDto);
        return resultCourseDto.flatMap(createdPerson -> {
            URI uri = uriComponentsBuilder.path("/api/v1/location/" + createdPerson.getId()).buildAndExpand(createdPerson).toUri();
            return Mono.just(ResponseEntity.created(uri).body(createdPerson));
        }).doOnSuccess(result -> logger.debug("createCourse end with result {}", result));
    }

    @Override
    public Mono<CourseDto> getCourse(@PathVariable Long id){
        logger.debug("getCourse with {} - start ", id);
        Mono<CourseDto> courseDto = courseService.getCourseById(id);
        return courseDto.doOnSuccess(result -> logger.debug("getCourse end with result {}", result));
    }

    @Audit(AuditCode.COURSE_DELETE)
    @Override
    public Mono<Boolean> deleteCourse(@PathVariable Long id){
        logger.debug("deleteCourse with {} - start ", id);
        Mono<Boolean> isDeleted = courseService.deleteCourseById(id);
        return isDeleted.doOnSuccess(result -> logger.debug("deleteCourse end with result {}", result));
    }

    @Override
    public Flux<CourseDto> getCourses(@RequestBody CourseSearchDto courseSearchDto,
                                      @PageableDefault(value = 5) @SortDefault(value = "id") Pageable pageable){
        logger.debug("getCourses with {}, {} - start ", courseSearchDto, pageable);
        Flux<CourseDto> result = courseService.getCourses(courseSearchDto, pageable);
        return result;
    }

    @Audit(AuditCode.COURSE_UPDATE)
    @Override
    public Mono<CourseDto> enrollCourse(@RequestParam Long courseId, Long personId){
        logger.debug("enrollCourse with {}, {} - start ", courseId, personId);
        Mono<CourseDto> courseDto = courseService.enroll(courseId, personId);
        return courseDto.doOnSuccess(result -> logger.debug("enrollCourse end with result {}", result));
    }

}
