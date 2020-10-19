package ru.tsedrik.service;

import ru.tsedrik.model.Group;

public interface GroupService {
    void addGroup(Group group);
    Group deleteGroup(Group group);
    Group deleteGroupById(Long id);
    Group getGroupById(Long id);
}
