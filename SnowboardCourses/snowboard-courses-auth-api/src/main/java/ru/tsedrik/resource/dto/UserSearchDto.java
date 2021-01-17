package ru.tsedrik.resource.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Класс для передачи параметров поиска сущности User
 */
@ApiModel(description = "Модель для поиска по пользователям")
public class UserSearchDto {

    /**
     * Фамилия пользователя
     */
    @ApiModelProperty(value = "Имя пользователя", example = "ivanov", allowEmptyValue = true)
    private String userName;

    /**
     * Электронный адрес пользователя
     */
    @ApiModelProperty(value = "Электронный адрес пользователя", example = "ivanov@mail.ru", allowEmptyValue = true)
    private String email;

    /**
     * Роль пользователя
     */
    @ApiModelProperty(value = "Роль пользователя", example = "ROLE_USER", allowableValues = "ROLE_ADMIN, ROLE_USER", allowEmptyValue = true)
    private String role;

    /**
     * Статус пользователя
     */
    @ApiModelProperty(value = "Статус пользователя", example = "DELETED", allowableValues = "ACTIVED, LOCKED, DELETED", allowEmptyValue = true)
    private String status;

    public UserSearchDto() {
    }

    public UserSearchDto(String userName, String email, String role, String status) {
        this.userName = userName;
        this.email = email;
        this.role = role;
        this.status = status;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
