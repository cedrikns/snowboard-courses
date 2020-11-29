package ru.tsedrik.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.tsedrik.controller.dto.PersonDto;
import ru.tsedrik.dao.PersonDAO;
import ru.tsedrik.exception.PersonNotFoundException;
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

    /**
     * Шаблон сообщения об ошибке для исключения PersonNotFoundException
     */
    @Value("${exception.personNotFound}")
    private String personNotFoundExMsg;

    public PersonServiceImpl(PersonDAO personDAO){
        this.personDAO = personDAO;
    }

    @Override
    public PersonDto addPerson(PersonDto personDto) {
        Person person = new Person(
                System.currentTimeMillis(), personDto.getFirstName(), personDto.getLastName(),
                personDto.getEmail(), personDto.getRole()
        );
        personDAO.create(person);
        personDto.setId(person.getId());
        return personDto;
    }

    @Override
    public PersonDto getPersonById(Long id) {
        Person person = personDAO.getById(id);
        if (person == null){
            throw new PersonNotFoundException(personNotFoundExMsg + id);
        }
        PersonDto personDto = new PersonDto(
                person.getId(), person.getFirstName(), person.getLastName(),
                person.getEmail(), person.getRole().toString()
        );
        return personDto;
    }

    @Override
    public boolean deletePersonById(Long id) {
        Person person = personDAO.deleteById(id);
        boolean deletingResult;
        if (person != null) {
            deletingResult = true;
        } else {
            deletingResult = false;
        }
        return deletingResult;
    }

    @Override
    public Person deletePerson(Person person) {
        return personDAO.delete(person);
    }

    @Override
    public PersonDto updatePerson(Long id, PersonDto personDto) {
        Person person = personDAO.getById(id);
        if (person == null){
            throw new PersonNotFoundException(personNotFoundExMsg + personDto.getId());
        }
        person.setFirstName(personDto.getFirstName());
        person.setLastName(personDto.getLastName());
        person.setEmail(personDto.getEmail());
        person.setRole(personDto.getRole());

        personDAO.update(person);
        personDto.setId(id);

        return personDto;
    }
}
