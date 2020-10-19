package ru.tsedrik.service;

import ru.tsedrik.model.Group;

public interface GroupService {
    void addGroup(Group group);
    Group deleteGroup(Group group);
    Group deleteGroupById(int id);
    Group getGroupById(int id);
}
