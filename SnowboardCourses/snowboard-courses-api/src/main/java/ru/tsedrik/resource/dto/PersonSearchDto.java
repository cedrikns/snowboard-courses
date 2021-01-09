package ru.tsedrik.resource.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Класс для передачи параметров поиска сущности Person
 */
@ApiModel(description = "Модель для поиска по участникам курсов")
public class PersonSearchDto {

    /**
     * Фамилия участника
     */
    @ApiModelProperty(value = "Фамилия участника курса", example = "Иванов", allowEmptyValue = true)
    private String firstName;

    /**
     * Имя участника
     */
    @ApiModelProperty(value = "Имя участника курса", example = "Иван", allowEmptyValue = true)
    private String lastName;

    /**
     * Электронный адрес участника
     */
    @ApiModelProperty(value = "Электронный адрес участника курса", example = "ivanov@mail.ru", allowEmptyValue = true)
    private String email;

    /**
     * Роль участника (Инструктор или Учащийся)
     */
    @ApiModelProperty(value = "Роль участника курса", example = "STUDENT", allowableValues = "INSTRUCTOR, STUDENT", allowEmptyValue = true)
    private String role;

    public PersonSearchDto() {
    }

    public PersonSearchDto(String firstName, String lastName, String email, String role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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
}
