package ru.tsedrik.domain;

import javax.persistence.*;
import java.util.Objects;

/**
 * Person представляет собой участника курсов.
 * Включает в себя как инструкторов, так и непосредственных участников.
 */
@Entity
@Table(name = "person")
public class Person  implements Identifired<Long> {

    /**
     * Идентификатор участника
     */
    @Id
    @Column
    private Long id;

    /**
     * Фамилия участника
     */
    @Column(name = "first_name")
    private String firstName;

    /**
     * Имя участника
     */
    @Column(name = "last_name")
    private String lastName;

    /**
     * Электронный адрес участника
     */
    @Column(nullable = false, unique = true)
    private String email;

    /**
     * Роль участника (Инструктор или Учащийся)
     */
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    public Person(){}

    public Person(Long id, String firstName, String lastName, String email, String role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.role = Role.valueOf(role.toUpperCase());
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setRole(String role) {
        this.role = Role.valueOf(role.toUpperCase());
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
