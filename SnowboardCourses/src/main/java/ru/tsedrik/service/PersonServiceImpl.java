package ru.tsedrik.service;

import org.springframework.stereotype.Service;
import ru.tsedrik.dao.PersonDAO;
import ru.tsedrik.model.Person;

/**
 * Реализация интерфейса PersonService
 */
@Service
public class PersonServiceImpl implements PersonService{

    /**
     * Объект для управления персистентным состоянием объектов типа Person
     */
    private PersonDAO personDAO;

    public PersonServiceImpl(PersonDAO personDAO){
        this.personDAO = personDAO;
    }

    @Override
    public void addPerson(Person person) {
        personDAO.create(person);
    }

    @Override
    public Person getPersonById(Long id) {
        return personDAO.getById(id);
    }

    @Override
    public Person deletePersonById(Long id) {
        return personDAO.deleteById(id);
    }

    @Override
    public Person deletePerson(Person person) {
        return personDAO.delete(person);
    }
}
