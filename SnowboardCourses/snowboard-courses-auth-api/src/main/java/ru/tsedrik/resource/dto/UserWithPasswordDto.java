package ru.tsedrik.resource.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Класс для передачи паролья при создании сущности User
 */
@ApiModel(description = "Модель для создания пользователя с паролем")
public class UserWithPasswordDto extends UserDto{

    /**
     * Пароль пользователя
     */
    @ApiModelProperty(value = "Пароль пользователя", example = "123456", allowEmptyValue = false)
    private String password;

    public UserWithPasswordDto() {
    }

    public UserWithPasswordDto(String password) {
        this.password = password;
    }

    public UserWithPasswordDto(Long id, String userName, String password, String email, String role, String password1) {
        super(id, userName, password, email, role);
        this.password = password1;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserWithPasswordDto{" +
                "id=" + getId() +
                ", userName='" + getUserName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", role='" + getRole() + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
