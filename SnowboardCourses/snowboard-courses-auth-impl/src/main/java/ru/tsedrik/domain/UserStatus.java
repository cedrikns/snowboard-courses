package ru.tsedrik.domain;

/**
 * Статус пользователя
 */
public enum UserStatus {

    /**
     * Пользователь создан в системе и активен
     */
    ACTIVED,

    /**
     * Пользователь заблокирован в системе
     */
    LOCKED,

    /**
     * Пользователь удален
     */
    DELETED
}
