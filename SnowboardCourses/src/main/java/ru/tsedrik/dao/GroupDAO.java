package ru.tsedrik.dao;

import ru.tsedrik.model.Group;
import ru.tsedrik.model.Person;

import java.util.Collection;

/**
 * Интерфейс управления персистентным состоянием объектов типа Group
 */
public interface GroupDAO extends GenericDAO<Group, Long> {

    /**
     * Возвращает список записей о группах, содержащих указанного участника.
     *
     * @param person    участник
     * @return  список найденных групп
     */
    Collection<Group> getAllByPerson(Person person);

}
