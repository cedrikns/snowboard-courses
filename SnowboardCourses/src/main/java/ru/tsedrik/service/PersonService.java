package ru.tsedrik.service;

import ru.tsedrik.model.Person;

public interface PersonService {
    void addPerson(Person person);
    Person deletePerson(Person person);
    Person deletePersonById(int id);
    Person getPersonById(int id);
}
