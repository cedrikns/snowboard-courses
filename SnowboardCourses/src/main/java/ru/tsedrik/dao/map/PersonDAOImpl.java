package ru.tsedrik.dao.map;

import org.springframework.stereotype.Repository;
import ru.tsedrik.dao.PersonDAO;
import ru.tsedrik.model.Person;
import ru.tsedrik.model.Role;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Реализация интерфейса PersonDAO
 */
@Repository
public class PersonDAOImpl extends AbstactDAO<Person, Long> implements PersonDAO {

    public PersonDAOImpl() {
        super(new HashMap<>());
    }

    @Override
    public List<Person> getAllByRole(Role role) {
        List<Person> personsWithRole = elements.values().stream().filter(p -> p.getRole() == role).collect(Collectors.toList());
        return personsWithRole;
    }
}
