package ru.tsedrik.resource.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Класс для вывода информации о пользователе
 */
@ApiModel(description = "Модель для вывода информации о пользователе")
public class UserInfoDto {

    /**
     * Токен пользователя
     */
    @ApiModelProperty(value = "Токен пользователя", allowEmptyValue = false)
    private String token;

    /**
     * Имя пользователя
     */
    @ApiModelProperty(value = "Имя пользователя в системе", example = "ivanov", allowEmptyValue = false)
    private String userName;

    /**
     * Электронный адрес пользователя
     */
    @ApiModelProperty(value = "Электронный адрес пользователя", example = "ivanov@mail.ru", required = true)
    private String email;

    /**
     * Роли пользователя
     */
    @ApiModelProperty(value = "Роли пользователя", example = "ROLE_USER", required = true)
    private List<String> roles;

    public UserInfoDto() {
    }

    public UserInfoDto(String token, String userName, String email, List<String> roles) {
        this.token = token;
        this.userName = userName;
        this.email = email;
        this.roles = roles;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getRole() {
        return roles;
    }

    public void setRole(List<String> roles) {
        this.roles = roles;
    }
}
