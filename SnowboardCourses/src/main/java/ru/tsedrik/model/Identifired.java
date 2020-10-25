package ru.tsedrik.model;

/**
 * Identifired определяет интерфейс для объектов с идентификаторами
 * @param <T>   тип идентификатора
 */
public interface Identifired <T>{

    /**
     * Возвращает идентификатор объекта.
     *
     * @return  идентификатор объекта
     */
    T getId();
}
