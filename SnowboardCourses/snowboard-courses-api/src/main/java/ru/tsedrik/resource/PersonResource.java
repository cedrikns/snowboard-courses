package ru.tsedrik.resource;

import io.swagger.annotations.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.tsedrik.resource.dto.*;

/**
 * API for PersonController
 */
@RequestMapping("/api/v1/person")
@Api(value = "API для работы с участниками курсов")
public interface PersonResource {

    @PostMapping
    @ApiOperation(value = "Создание участника курсов")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Участник курсов успешно создан", response = PersonDto.class),
            @ApiResponse(code = 400, message = "Непредвиденная ошибка", response = ResponseError.class),
            @ApiResponse(code = 401, message = "Ошибка аутентификации", response = ResponseError.class),
            @ApiResponse(code = 403, message = "Не достаточно прав", response = ResponseError.class)
    })
    Mono<ResponseEntity<PersonDto>> createPerson(@Validated @RequestBody PersonDto personDto, UriComponentsBuilder uriComponentsBuilder);

    @GetMapping(value = "/{id}")
    @ApiOperation(value = "Детальная информация об участнике курсов")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Участник курсов успешно найден", response = PersonDto.class),
            @ApiResponse(code = 400, message = "Участник курсов не найден либо произошла другая непредвиденная ошибка", response = ResponseError.class),
            @ApiResponse(code = 401, message = "Ошибка аутентификации", response = ResponseError.class),
            @ApiResponse(code = 403, message = "Не достаточно прав", response = ResponseError.class)
    })
    Mono<PersonDto> getPerson(@ApiParam(value = "Идентификатор участника курсов", required = true) @PathVariable Long id);

    @GetMapping
    @ApiOperation(value = "Поиск по участникам курсов")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Список найденных участников курсов", response = PersonDto.class, responseContainer = "PageDto"),
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
                    defaultValue = "id", allowableValues = "id, firstName, lastName, email, role, desc|asc", dataType = "String", paramType = "query")
    })
    Flux<PersonDto> getPersons(@RequestBody PersonSearchDto personSearchDto,
                               @PageableDefault(value = 5) @SortDefault(value = "id") Pageable pageable);

    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "Удаление участника курсов")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Участник курсов успешно удален", response = Boolean.class),
            @ApiResponse(code = 400, message = "Непредвиденная ошибка", response = ResponseError.class),
            @ApiResponse(code = 401, message = "Ошибка аутентификации", response = ResponseError.class),
            @ApiResponse(code = 403, message = "Не достаточно прав", response = ResponseError.class)
    })
    Mono<Boolean> deletePerson(@ApiParam(value = "Идентификатор участника курсов", required = true) @PathVariable Long id);

    @PutMapping(value = "/{id}")
    @ApiOperation(value = "Обновление участника курсов")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Участник курсов успешно обновлен", response = Boolean.class),
            @ApiResponse(code = 400, message = "Непредвиденная ошибка", response = ResponseError.class),
            @ApiResponse(code = 401, message = "Ошибка аутентификации", response = ResponseError.class),
            @ApiResponse(code = 403, message = "Не достаточно прав", response = ResponseError.class)
    })
    Mono<PersonDto> updatePerson(@ApiParam(value = "Идентификатор участника курсов", required = true) @PathVariable Long id, @RequestBody PersonDto personDto);

}
