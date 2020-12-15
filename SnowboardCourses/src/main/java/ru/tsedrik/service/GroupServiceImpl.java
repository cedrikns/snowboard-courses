package ru.tsedrik.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tsedrik.dao.GroupDAO;
import ru.tsedrik.dao.PersonDAO;
import ru.tsedrik.model.Group;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Реализация интерфейса GroupService
 */
@Service
@Transactional
public class GroupServiceImpl implements GroupService{

    /**
     * Объект для управления персистентным состоянием объектов типа Group
     */
    private GroupDAO groupDAO;
    private PersonDAO personDAO;

    private static final Logger logger = LoggerFactory.getLogger(GroupServiceImpl.class.getName());

    public GroupServiceImpl(GroupDAO groupDAO, PersonDAO personDAO){
        this.groupDAO = groupDAO;
        this.personDAO = personDAO;
    }

    @Override
    public void addGroup(Group group) {
        groupDAO.create(group);
    }

    @Override
    public Group getGroupById(Long id) {
        Group group = groupDAO.getById(id);
        group.setStudents(personDAO.getAllByGroupId(group.getId()).stream().collect(Collectors.toList()));
        return group;
    }

    @Override
    public boolean deleteGroupById(Long id) {
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

    @Override
    public Collection<Group> getAllByCourseId(Long id) {
        Collection<Group> groups = groupDAO.getAllByCourseId(id);
        groups.forEach(g -> g.setStudents(personDAO.getAllByGroupId(g.getId()).stream().collect(Collectors.toList())));
        return groups;
    }

    @Override
    public boolean addPersonToGroup(Long groupId, Long personId) {
        return groupDAO.addPersonToGroup(groupId, personId);
    }

}
