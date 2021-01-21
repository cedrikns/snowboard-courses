package ru.tsedrik.service;

import ru.tsedrik.resource.dto.AuthUserDto;
import ru.tsedrik.resource.dto.TokenDto;

/**
 * AuthService представляет интерфейс для работы с токеном
 */
public interface AuthService {

    /**
     * Создает токен пользователя
     * @param authUserDto   данные пользователя для аутентификации
     * @return  сгенерированный токен пользователя
     */
    TokenDto createToken(AuthUserDto authUserDto);
}
