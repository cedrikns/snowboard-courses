package ru.tsedrik.service;

import ru.tsedrik.model.Group;

import java.util.Collection;

/**
 * GroupService представляет интерфейс взаимодействия с классом Group
 */
public interface GroupService {

    /**
     * Добавляет новую группу.
     *
     * @param group    новая группа, которая будет добавлена
     */
    void addGroup(Group group);

    /**
     * Удаляет существующую группу.
     *
     * @param group    существующая группа, которая будет удалена
     * @return  удаленная группа
     */
    Group deleteGroup(Group group);

    /**
     * Удаляет существующую группу по ее идентификатору.
     *
     * @param id    идентификатор удаляемой группы
     * @return  удаленная группа
     */
    boolean deleteGroupById(Long id);

    /**
     * Запрашивает группу по ее идентификатору.
     *
     * @param id    идентификатор запрашиваемой группы
     * @return  найденная группа
     */
    Group getGroupById(Long id);

    /**
     * Обновляет группу.
     *
     * @param group    группа, которая будет обновлена
     */

    Group updateGroup(Group group);

    /**
     * Добавляет участника в группу.
     *
     * @param groupId    идентификатор группы, в которую будет добавлен участник
     * @param personId   идентификатор участника, который будет добавлен в группу
     */

    boolean addPersonToGroup(Long groupId, Long personId);

    /**
     * Возвращает список записей о группах, составляющих конкретный курс.
      * @param id идентификатор курса, группы которого нужно найти
     * @return список найденных групп
     */
    Collection<Group> getAllByCourseId(Long id);

}
