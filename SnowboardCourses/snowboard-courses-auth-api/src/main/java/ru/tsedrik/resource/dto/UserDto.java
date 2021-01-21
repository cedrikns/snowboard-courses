package ru.tsedrik.resource.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Класс для передачи параметров создания сущности User
 */
@ApiModel(description = "Модель для создания пользователя")
public class UserDto {
    /**
     * Идентификатор пользователя
     */
    @ApiModelProperty(value = "Идентификатор пользователя", example = "1608061807885", allowEmptyValue = true)
    private Long id;

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
     * Роль пользователя
     */
    @ApiModelProperty(value = "Роль пользователя", example = "ROLE_USER", allowableValues = "ROLE_ADMIN, ROLE_USER", required = false)
    private String role;

    public UserDto() {
    }

    public UserDto(Long id, String userName, String password, String email, String role) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
