package ru.tsedrik.kafka;

import ru.tsedrik.domain.UserStatus;

/**
 * Класс для передачи данных об изменении статуса пользователя
 */
public class KafkaUserDto {
    /**
     * Идентификатор пользователя
     */
    private Long id;

    /**
     * Статус пользователя
     */
    private UserStatus status;

    public KafkaUserDto() {
    }

    public KafkaUserDto(Long id, UserStatus status) {
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
        return "KafkaUserDto{" +
                "id=" + id +
                ", status=" + status +
                '}';
    }
}
