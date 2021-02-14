package ru.tsedrik.domain;

/**
 * Статус сообщения
 */
public enum MessageStatus {

    /**
     * Сообщение отправлено
     */
    SEND,

    /**
     * Сообщение не доставлено или доставлено с ошибкой
     */
    FAIL,

    /**
     * Сообщение успешно доставлено
     */
    SUCCESS
}
