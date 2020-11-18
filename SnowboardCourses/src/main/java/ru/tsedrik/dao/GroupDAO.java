package ru.tsedrik.dao;

import ru.tsedrik.model.Group;
import ru.tsedrik.model.Person;

import java.util.Collection;

/**
 * Интерфейс управления персистентным состоянием объектов типа Group
 */
public interface GroupDAO extends GenericDAO<Group, Long> {

    /**
     * Возвращает список записей о группах с конкретным инструктором.
     *
     * @param person    инструктор
     * @return  список найденных групп
     */
    Collection<Group> getAllByInstructor(Person person);

    /**
     * Возвращает список записей о группах, составляющих конкретный курс.
     *
     * @param id    идентификатор курса, группы которого нужно найти
     * @return  список найденных групп
     */
    Collection<Group> getAllByCourseId(Long id);

    /**
     * Добавляет участника в группу.
     *
     * @param groupId    идентификатор группы, в которую будет добавлен участник
     * @param personId   идентификатор участника, который будет добавлен в группу
     */
    boolean addPersonToGroup(Long groupId, Long personId);
}
