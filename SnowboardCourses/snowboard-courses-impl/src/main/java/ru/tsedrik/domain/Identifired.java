package ru.tsedrik.domain;

import java.io.Serializable;

/**
 * Identifired определяет интерфейс для объектов с идентификаторами
 * @param <T>   тип идентификатора
 */
public interface Identifired <T extends Serializable> extends Serializable{

    /**
     * Возвращает идентификатор объекта.
     *
     * @return  идентификатор объекта
     */
    T getId();
}
