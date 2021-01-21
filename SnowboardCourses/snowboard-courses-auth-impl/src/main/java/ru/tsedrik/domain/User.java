package ru.tsedrik.domain;

import javax.persistence.*;

/**
 * User представляет собой пользователя системы.
 */
@Entity
@Table(schema = "sc_auth", name = "users")
public class User extends CreateAtIdentified implements Identifired<Long> {

    /**
     * Идентификатор пользователя
     */
    @Id
    @Column
    private Long id;

    /**
     * Имя пользователя
     */
    @Column(name = "user_name", nullable = false)
    private String userName;

    /**
     * Пароль пользователя
     */
    @Column(nullable = false)
    private String password;

    /**
     * Электронный адрес пользователя
     */

    private String email;

    /**
     * Роль пользователя
     */
    @Enumerated(EnumType.STRING)
    private Role role;

    /**
     * Статус пользователя
     */
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    public User(){}

    public User(Long id, String userName, String password, String email, Role role, UserStatus status) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.role = role;
        this.status = status;
    }

    @Override
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", status=" + status +
                '}';
    }
}
