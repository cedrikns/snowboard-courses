package ru.tsedrik.controller.dto;

public class PersonDto {
    /**
     * Идентификатор участника
     */
    private Long id;

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

    public PersonDto() {
    }

    public PersonDto(Long id, String firstName, String lastName, String email, String role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
