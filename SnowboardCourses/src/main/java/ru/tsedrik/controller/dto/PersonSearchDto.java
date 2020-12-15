package ru.tsedrik.controller.dto;

/**
 * Класс для передачи параметров поиска сущности Person
 */
public class PersonSearchDto {

    /**
     * Фамилия участника
     */
    private String firstName;

    /**
     * Имя участника
     */
    private String lastName;

    /**
     * Электронный адрес участника
     */
    private String email;

    /**
     * Роль участника (Инструктор или Учащийся)
     */
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
