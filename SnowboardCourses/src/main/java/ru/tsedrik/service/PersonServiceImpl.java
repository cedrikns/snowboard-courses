package ru.tsedrik.service;

import ru.tsedrik.dao.PersonDAO;
import ru.tsedrik.model.Person;

public class PersonServiceImpl implements PersonService{
    private PersonDAO personDAO;

    public PersonServiceImpl(PersonDAO personDAO){
        this.personDAO = personDAO;
    }

    @Override
    public void addPerson(Person person) {
        personDAO.create(person);
    }

    @Override
    public Person getPersonById(int id) {
        return personDAO.getById(id);
    }

    @Override
    public Person deletePersonById(int id) {
        return personDAO.deleteById(id);
    }

    @Override
    public Person deletePerson(Person person) {
        return personDAO.delete(person);
    }
}
