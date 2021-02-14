package ru.tsedrik.domain;

import javax.persistence.*;
import java.util.UUID;

/**
 * SenderMessage представляет собой сообщение по изменению пользователя, которое отправляется в очередь.
 */
@Entity
@Table(schema = "sc_auth", name = "sender_msg")
public class SenderMessage extends CreateAtIdentified implements Identifired<UUID> {

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

    /**
     * Статус сообщения
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MessageStatus status;

    public SenderMessage() {
    }

    public SenderMessage(UUID id, String message, MessageStatus status) {
        this.id = id;
        this.message = message;
        this.status = status;
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

    public MessageStatus getStatus() {
        return status;
    }

    public void setStatus(MessageStatus status) {
        this.status = status;
    }
}
