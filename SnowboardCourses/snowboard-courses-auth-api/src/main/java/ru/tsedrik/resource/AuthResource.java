package ru.tsedrik.resource;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.tsedrik.resource.dto.*;

/**
 * API для AuthController
 */
@RequestMapping("/api/auth")
@Api(value = "API для работы с аутенфикацией пользователей")
public interface AuthResource {

    @PostMapping
    @ApiOperation(value = "Создание токена")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Токен успешно создан", response = TokenDto.class),
            @ApiResponse(code = 400, message = "Непредвиденная ошибка", response = ResponseError.class),
            @ApiResponse(code = 401, message = "Ошибка аутентификации", response = ResponseError.class)
    })
    ResponseEntity<TokenDto> createToken(@RequestBody AuthUserDto authUserDto);

}
