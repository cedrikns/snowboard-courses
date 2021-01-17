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
 * API для UserController
 */
@RequestMapping("/api/v1/user")
@Api(value = "API для работы с пользователями")
public interface UserResource {

    @PostMapping
    @ApiOperation(value = "Создание пользователя")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Пользователь успешно создан", response = UserDto.class),
            @ApiResponse(code = 400, message = "Непредвиденная ошибка", response = ResponseError.class),
    })
    ResponseEntity<UserDto> createUser(@RequestBody UserWithPasswordDto userWithPasswordDto, UriComponentsBuilder uriComponentsBuilder);

    @GetMapping(value = "/{id}")
    @ApiOperation(value = "Детальная информация о пользователе")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Пользователь успешно найден", response = UserDto.class),
            @ApiResponse(code = 400, message = "Пользователь не найден либо произошла другая непредвиденная ошибка", response = ResponseError.class),
    })
    UserDto getUser(@ApiParam(value = "Идентификатор пользователя", required = true) @PathVariable Long id);

    @GetMapping
    @ApiOperation(value = "Поиск по пользователям")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Список найденных пользователей", response = UserDto.class, responseContainer = "PageDto"),
            @ApiResponse(code = 400, message = "Непредвиденная ошибка", response = ResponseError.class),
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "Номер страницы, которую нужно отобразить",
                    defaultValue = "0", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "Количество записей, которые отобразятся на одной странице",
                    defaultValue = "5", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "Критерии сортировки", allowMultiple = true,
                    defaultValue = "id", allowableValues = "id, userName, email, role, desc|asc", dataType = "String", paramType = "query")
    })
    PageDto<UserDto> getUsers(@RequestBody UserSearchDto userSearchDto,
                                @PageableDefault(value = 5) @SortDefault(value = "id") Pageable pageable);

    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "Удаление пользователя")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Пользователь успешно удален", response = Boolean.class),
            @ApiResponse(code = 400, message = "Непредвиденная ошибка", response = ResponseError.class),
    })
    boolean deleteUser(@ApiParam(value = "Идентификатор пользователя", required = true) @PathVariable Long id);

    @PutMapping(value = "/{id}")
    @ApiOperation(value = "Обновление пользователя")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Пользователь успешно обновлен", response = UserDto.class),
            @ApiResponse(code = 400, message = "Непредвиденная ошибка", response = ResponseError.class),
    })
    UserDto updateUser(@ApiParam(value = "Идентификатор пользователя", required = true) @PathVariable Long id, @RequestBody UserWithPasswordDto userWithPasswordDto);

}
