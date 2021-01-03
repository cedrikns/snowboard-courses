package ru.tsedrik.domain;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

/**
 * Класс для автоматического добавления всем сущностям времени создания/изменения
 */
public class ChangingEntityListener {

    @PrePersist
    public void prePersist(CreateAtIdentified createAtIdentified) {
        LocalDateTime localDateTime = LocalDateTime.now();
        createAtIdentified.setCreatedAt(localDateTime);
        createAtIdentified.setUpdatedAt(localDateTime);
    }

    @PreUpdate
    public void preUpdate(CreateAtIdentified createAtIdentified) {
        createAtIdentified.setUpdatedAt(LocalDateTime.now());
    }

}
