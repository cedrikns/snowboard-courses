package ru.tsedrik.service;

import org.springframework.stereotype.Service;
import ru.tsedrik.dao.GroupDAO;
import ru.tsedrik.model.Group;

/**
 * Реализация интерфейса GroupService
 */
@Service
public class GroupServiceImpl implements GroupService{

    /**
     * Объект для управления персистентным состоянием объектов типа Group
     */
    private GroupDAO groupDAO;

    public GroupServiceImpl(GroupDAO groupDAO){
        this.groupDAO = groupDAO;
    }

    @Override
    public void addGroup(Group group) {
        groupDAO.create(group);
    }

    @Override
    public Group getGroupById(Long id) {
        return groupDAO.getById(id);
    }

    @Override
    public Group deleteGroupById(Long id) {
        return groupDAO.deleteById(id);
    }

    @Override
    public Group deleteGroup(Group group) {
        return groupDAO.delete(group);
    }

    @Override
    public Group updateGroup(Group group) {
        return groupDAO.update(group);
    }
}
