package ru.tsedrik.service;

import org.springframework.beans.factory.annotation.Value;
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

    /**
     * Максимальное количество Учащихся в одной группе
     */
    @Value("${group.maxPersonPerGroup}")
    private String maxPersonPerGroup;

    public GroupServiceImpl(GroupDAO groupDAO){
        this.groupDAO = groupDAO;
    }

    @Override
    public void addGroup(Group group) {
        int personCount = group.getStudents().size();
        if (personCount > Integer.valueOf(maxPersonPerGroup)){
            throw new IllegalArgumentException("Превышено максимальное количество Учащихся в группе: " + personCount
                    + " вместо " + maxPersonPerGroup);
        }
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
}
