package ru.tsedrik.jms;

import ru.tsedrik.domain.UserStatus;

import java.io.Serializable;

/**
 * Класс для передачи данных об изменении статуса пользователя
 */
public class JMSUserDto implements Serializable {

    /**
     * Идентификатор пользователя
     */
    private Long id;

    /**
     * Статус пользователя
     */
    private UserStatus status;

    public JMSUserDto(){}

    public JMSUserDto(Long id, UserStatus status) {
        this.id = id;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "JMSUserDto{" +
                "id=" + id +
                ", status=" + status +
                '}';
    }
}
