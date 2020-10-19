package ru.tsedrik.dao;

import ru.tsedrik.model.Person;
import ru.tsedrik.model.Role;

import java.util.Collection;

public interface PersonDAO extends GenericDAO<Person, Integer> {

    Collection<Person> getAllByRole(Role role);
}
