package ru.tsedrik.service;

import ru.tsedrik.model.Person;

public interface PersonService {
    void addPerson(Person person);
    Person deletePerson(Person person);
    Person deletePersonById(Long id);
    Person getPersonById(Long id);
}
