package ru.tsedrik.resource.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Класс для передачи параметров создания сущности User
 */
@ApiModel(description = "Модель для аутентификации пользователя")
public class AuthUserDto {

    /**
     * Имя пользователя
     */
    @ApiModelProperty(value = "Имя пользователя", example = "user", required = true)
    private String userName;

    /**
     * Пароль пользователя
     */
    @ApiModelProperty(value = "Пароль пользователя", example = "123456", required = true)
    private String password;

    /**
     * Имя системы, на которую нужно получить токен
     */
    @ApiModelProperty(value = "Имя системы, на которую нужно получить токен", example = "my-system", required = true)
    private String systemName;

    public AuthUserDto() {
    }

    public AuthUserDto(String userName, String password, String systemName) {
        this.userName = userName;
        this.password = password;
        this.systemName = systemName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }
}
