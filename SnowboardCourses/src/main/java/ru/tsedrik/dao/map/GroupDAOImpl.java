package ru.tsedrik.dao.map;

import org.springframework.stereotype.Repository;
import ru.tsedrik.dao.GroupDAO;
import ru.tsedrik.model.Group;
import ru.tsedrik.model.Person;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Реализация интерфейса GroupDAO
 */
@Repository
public class GroupDAOImpl extends AbstactDAO<Group, Long> implements GroupDAO {

    public GroupDAOImpl() {
        super(new HashMap<>());
    }

    @Override
    public Collection<Group> getAllByPerson(Person person) {
        List<Group> groups = elements.values().stream()
                .filter(g -> g.getStudents().contains(person))
                .collect(Collectors.toList());
        return groups;
    }
}
