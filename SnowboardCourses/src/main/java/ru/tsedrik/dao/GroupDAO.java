package ru.tsedrik.dao;

import ru.tsedrik.model.Group;
import ru.tsedrik.model.Person;

import java.util.Collection;

public interface GroupDAO extends GenericDAO<Group, Long> {

    Collection<Group> getAllByPerson(Person person);

}
