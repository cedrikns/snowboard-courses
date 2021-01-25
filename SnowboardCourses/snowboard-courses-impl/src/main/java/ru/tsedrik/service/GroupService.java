package ru.tsedrik.service;

import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    void addGroup(Group group);

    /**
     * Удаляет существующую группу.
     *
     * @param group    существующая группа, которая будет удалена
     * @return  успешно ли прошло удаление
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    boolean deleteGroup(Group group);

    /**
     * Удаляет существующую группу по ее идентификатору.
     *
     * @param id    идентификатор удаляемой группы
     * @return  успешно ли прошло удаление
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    boolean deleteGroupById(Long id);

    /**
     * Запрашивает группу по ее идентификатору.
     *
     * @param id    идентификатор запрашиваемой группы
     * @return  найденная группа
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    Group getGroupById(Long id);

    /**
     * Обновляет группу.
     *
     * @param group    группа, которая будет обновлена
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    Group updateGroup(Group group);

}
