package ru.tsedrik.resource;

import io.swagger.annotations.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.tsedrik.resource.dto.CourseDto;
import ru.tsedrik.resource.dto.CourseSearchDto;
import ru.tsedrik.resource.dto.PageDto;
import ru.tsedrik.resource.dto.ResponseError;

/**
 * API for CourseController
 */
@RequestMapping("/api/v1/course")
@Api(value = "API для работы с курсами")
public interface CourseResource {

    @PostMapping
    @ApiOperation(value = "Создание курса")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Курс успешно создан", response = CourseDto.class),
            @ApiResponse(code = 400, message = "Непредвиденная ошибка", response = ResponseError.class),
    })
    ResponseEntity<CourseDto> createCourse(@RequestBody CourseDto courseDto, UriComponentsBuilder uriComponentsBuilder);

    @GetMapping(value = "/{id}")
    @ApiOperation(value = "Детальная информация по курсу")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Курс успешно найден", response = CourseDto.class),
            @ApiResponse(code = 400, message = "Курс не найден либо произошла другая непредвиденная ошибка", response = ResponseError.class),
    })
    CourseDto getCourse(@ApiParam(value = "Идентификатор курса", required = true) @PathVariable Long id);

    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "Удаление курса")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Курс успешно удален", response = Boolean.class),
            @ApiResponse(code = 400, message = "Непредвиденная ошибка", response = ResponseError.class),
    })
    boolean deleteCourse(@ApiParam(value = "Идентификатор курса", required = true) @PathVariable Long id);

    @GetMapping
    @ApiOperation(value = "Поиск по курсам")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Список найденных курсов", response = CourseDto.class, responseContainer = "PageDto"),
            @ApiResponse(code = 400, message = "Непредвиденная ошибка", response = ResponseError.class),
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "Номер страницы, которую нужно отобразить",
                    defaultValue = "0", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "Количество записей, которые отобразятся на одной странице",
                    defaultValue = "5", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "Критерии сортировки", allowMultiple = true,
                    defaultValue = "id", allowableValues = "id, courseType, startTime, endTime, desc|asc", dataType = "String", paramType = "query")
    })
    PageDto<CourseDto> getCourses(@RequestBody CourseSearchDto courseSearchDto,
                                  @PageableDefault(value = 5) @SortDefault(value = "id") Pageable pageable);

    @PostMapping(value = "/enroll")
    @ApiOperation(value = "Запись участника на курс")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Участник успешно записан на курс", response = CourseDto.class),
            @ApiResponse(code = 400, message = "Непредвиденная ошибка", response = ResponseError.class),
    })
    CourseDto enrollCourse(@ApiParam(value = "Идентификатор курса", required = true) @RequestParam Long courseId,
                           @ApiParam(value = "Идентификатор участника курсов", required = true) @RequestParam Long personId);

}
