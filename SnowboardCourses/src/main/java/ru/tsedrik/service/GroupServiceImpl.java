package ru.tsedrik.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tsedrik.domain.Group;
import ru.tsedrik.repository.GroupRepository;

/**
 * Реализация интерфейса GroupService
 */
@Service
@Transactional
public class GroupServiceImpl implements GroupService{

    /**
     * Объект для управления персистентным состоянием объектов типа Group
     */
    private GroupRepository groupRepository;

    private static final Logger logger = LoggerFactory.getLogger(GroupServiceImpl.class.getName());

    public GroupServiceImpl(GroupRepository groupRepository){
        this.groupRepository = groupRepository;
    }

    @Override
    public void addGroup(Group group) {
        groupRepository.save(group);
    }

    @Override
    public Group getGroupById(Long id) {
        Group group = groupRepository.findById(id).get();
        return group;
    }

    @Override
    public boolean deleteGroupById(Long id) {
        groupRepository.deleteById(id);
        return true;
    }

    @Override
    public boolean deleteGroup(Group group) {
        groupRepository.delete(group);
        return true;
    }

    @Override
    public Group updateGroup(Group group) {
         return groupRepository.save(group);
    }

}
