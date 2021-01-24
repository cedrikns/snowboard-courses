package ru.tsedrik.resource;

import io.swagger.annotations.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.tsedrik.resource.dto.*;

/**
 * API for LocationController
 */
@RequestMapping("/api/v1/location")
@Api(value = "API для работы с местами проведения курсов")
public interface LocationResource {

    @PostMapping
    @ApiOperation(value = "Создание места проведения курсов")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Место проведения курсов успешно создано", response = LocationDto.class),
            @ApiResponse(code = 400, message = "Непредвиденная ошибка", response = ResponseError.class),
            @ApiResponse(code = 401, message = "Ошибка аутентификации", response = ResponseError.class),
            @ApiResponse(code = 403, message = "Не достаточно прав", response = ResponseError.class)
    })
    ResponseEntity<LocationDto> createLocation(@RequestBody LocationDto locationDto, UriComponentsBuilder uriComponentsBuilder);

    @GetMapping(value = "/{id}")
    @ApiOperation(value = "Детальная информация по месту проведения курсов")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Место проведения курсов успешно найдено", response = LocationDto.class),
            @ApiResponse(code = 400, message = "Место проведения курсов не найдено либо произошла другая непредвиденная ошибка", response = ResponseError.class),
            @ApiResponse(code = 401, message = "Ошибка аутентификации", response = ResponseError.class),
            @ApiResponse(code = 403, message = "Не достаточно прав", response = ResponseError.class)
    })
    LocationDto getLocation(@ApiParam(value = "Идентификатор места проведения курсов", required = true) @PathVariable Long id);

    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "Удаление места проведения курсов")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Место проведения курсов успешно удалено", response = Boolean.class),
            @ApiResponse(code = 400, message = "Непредвиденная ошибка", response = ResponseError.class),
            @ApiResponse(code = 401, message = "Ошибка аутентификации", response = ResponseError.class),
            @ApiResponse(code = 403, message = "Не достаточно прав", response = ResponseError.class)
    })
    boolean deleteLocation(@ApiParam(value = "Идентификатор места проведения курсов", required = true) @PathVariable Long id);

    @PutMapping(value = "/{id}")
    @ApiOperation(value = "Обновление места проведения курсов")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Место проведения курсов успешно обновлено", response = LocationDto.class),
            @ApiResponse(code = 400, message = "Непредвиденная ошибка", response = ResponseError.class),
            @ApiResponse(code = 401, message = "Ошибка аутентификации", response = ResponseError.class),
            @ApiResponse(code = 403, message = "Не достаточно прав", response = ResponseError.class)
    })
    LocationDto updateLocation(@ApiParam(value = "Идентификатор места проведения курсов", required = true) @PathVariable Long id, @RequestBody LocationDto locationDto);

    @GetMapping
    @ApiOperation(value = "Поиск по местам проведения курсов")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Список найденных мест проведения курсов", response = LocationDto.class, responseContainer = "PageDto"),
            @ApiResponse(code = 400, message = "Непредвиденная ошибка", response = ResponseError.class),
            @ApiResponse(code = 401, message = "Ошибка аутентификации", response = ResponseError.class),
            @ApiResponse(code = 403, message = "Не достаточно прав", response = ResponseError.class)
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "Номер страницы, которую нужно отобразить",
                    defaultValue = "0", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "Количество записей, которые отобразятся на одной странице",
                    defaultValue = "5", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "Критерии сортировки", allowMultiple = true,
                    defaultValue = "id", allowableValues = "id, name, country, city, desc|asc", dataType = "String", paramType = "query")
    })
    PageDto<LocationDto> getLocations(@RequestBody LocationSearchDto locationSearchDto,
                                      @PageableDefault(value = 5) @SortDefault(value = "id") Pageable pageable);
}
