package ru.tsedrik.domain;

import javax.persistence.*;
import java.util.UUID;

/**
 * ReceiverMessage представляет собой полученное сообщение.
 */
@Entity
@Table(schema = "sc_jms", name = "receiver_msg")
public class ReceiverMessage extends CreateAtIdentified implements Identifired<UUID> {

    /**
     * Идентификатор сообщения
     */
    @Id
    @Column
    private UUID id;

    /**
     * Содержание сообщения
     */
    @Column(nullable = false)
    String message;

    public ReceiverMessage() {
    }

    public ReceiverMessage(UUID id, String message) {
        this.id = id;
        this.message = message;
    }

    @Override
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
