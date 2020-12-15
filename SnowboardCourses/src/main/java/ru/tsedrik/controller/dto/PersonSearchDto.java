package ru.tsedrik.controller.dto;

/**
 * Класс для передачи параметров поиска
 */
public class PersonSearchDto {

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

    public PersonSearchDto(String email, String role) {
        this.email = email;
        this.role = role;
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
