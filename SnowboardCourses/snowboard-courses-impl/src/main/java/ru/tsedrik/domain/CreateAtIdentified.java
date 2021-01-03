package ru.tsedrik.domain;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * Класс-родитель всех сущностей для наличия в них времени создания/изменения
 */
@MappedSuperclass
@EntityListeners(ChangingEntityListener.class)
public abstract class CreateAtIdentified {

    /**
     * Время создания сущности
     */
    private LocalDateTime createdAt;

    /**
     * Время последнего обновления сущности
     */
    private LocalDateTime updatedAt;

    public CreateAtIdentified() {
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
