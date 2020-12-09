package ru.tsedrik.service;

import ru.tsedrik.domain.Group;

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
     * @return  успешно ли прошло удаление
     */
    boolean deleteGroup(Group group);

    /**
     * Удаляет существующую группу по ее идентификатору.
     *
     * @param id    идентификатор удаляемой группы
     * @return  успешно ли прошло удаление
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

}
