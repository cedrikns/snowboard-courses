package ru.tsedrik.model;

import java.util.Objects;

/**
 * Person представляет собой участника курсов.
 * Включает в себя как инструкторов, так и непосредственных участников.
 */
public class Person  implements Identifired<Long> {

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
    private Role role;

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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", role=" + role +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id == person.id &&
                Objects.equals(firstName, person.firstName) &&
                Objects.equals(lastName, person.lastName) &&
                Objects.equals(email, person.email) &&
                role == person.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email, role);
    }
}
