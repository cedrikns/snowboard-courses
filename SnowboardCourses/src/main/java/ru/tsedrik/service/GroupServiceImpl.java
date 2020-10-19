package ru.tsedrik.service;

import ru.tsedrik.dao.GroupDAO;
import ru.tsedrik.model.Group;

public class GroupServiceImpl implements GroupService{
    private GroupDAO groupDAO;

    public GroupServiceImpl(GroupDAO groupDAO){
        this.groupDAO = groupDAO;
    }

    @Override
    public void addGroup(Group group) {
        groupDAO.create(group);
    }

    @Override
    public Group getGroupById(int id) {
        return groupDAO.getById(id);
    }

    @Override
    public Group deleteGroupById(int id) {
        return groupDAO.deleteById(id);
    }

    @Override
    public Group deleteGroup(Group group) {
        return groupDAO.delete(group);
    }
}
