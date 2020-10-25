package ru.tsedrik.dao;

import ru.tsedrik.model.Person;
import ru.tsedrik.model.Role;

import java.util.Collection;

/**
 * Интерфейс управления персистентным состоянием объектов типа Person
 */
public interface PersonDAO extends GenericDAO<Person, Long> {

    /**
     * Возвращает список записей об участниках с указанной ролью.
     *
     * @param role  роль участника
     * @return  список найденных участников
     */
    Collection<Person> getAllByRole(Role role);
}
