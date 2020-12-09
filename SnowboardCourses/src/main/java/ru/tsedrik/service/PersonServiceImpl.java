package ru.tsedrik.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tsedrik.controller.dto.PersonDto;
import ru.tsedrik.controller.dto.PersonSearchDto;
import ru.tsedrik.dao.PersonDAO;
import ru.tsedrik.exception.PersonNotFoundException;
import ru.tsedrik.domain.Person;
import ru.tsedrik.domain.Role;
import ru.tsedrik.repository.PersonRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Реализация интерфейса PersonService
 */
@Service
@Transactional
public class PersonServiceImpl implements PersonService{

    /**
     * Объект для управления персистентным состоянием объектов типа Person
     */
    private PersonRepository personRepository;

    /**
     * Шаблон сообщения об ошибке для исключения PersonNotFoundException
     */
    @Value("${exception.personNotFound}")
    private String personNotFoundExMsg;

    public PersonServiceImpl(PersonRepository personRepository){
        this.personRepository = personRepository;
    }

    @Override
    public PersonDto addPerson(PersonDto personDto) {
        Person person = new Person(
                System.currentTimeMillis(), personDto.getFirstName(), personDto.getLastName(),
                personDto.getEmail(), personDto.getRole()
        );
        personRepository.save(person);
        personDto.setId(person.getId());

        return personDto;
    }

    @Override
    public PersonDto getPersonById(Long id) {
        PersonDto personDto = personRepository.findById(id)
                .map(person -> new PersonDto(
                    person.getId(), person.getFirstName(), person.getLastName(),
                    person.getEmail(), person.getRole().toString()))
                .orElseThrow(() -> new PersonNotFoundException(personNotFoundExMsg + "id = " + id));

        return personDto;
    }

    @Override
    public PersonDto getPersonByEmail(String email) {
        Person person = personRepository.getPersonByEmail(email);
        if (person == null){
            throw new PersonNotFoundException(personNotFoundExMsg + "email = " + email);
        }
        PersonDto personDto = new PersonDto(
                person.getId(), person.getFirstName(), person.getLastName(),
                person.getEmail(), person.getRole().toString()
        );

        return personDto;
    }

    @Override
    public boolean deletePersonById(Long id) {
        personRepository.deleteById(id);
        return true;
    }

    @Override
    public boolean deletePerson(Person person) {
        personRepository.delete(person);
        return true;
    }

    @Override
    public PersonDto updatePerson(PersonDto personDto) {
        Person person = personRepository.findById(personDto.getId())
                .orElseThrow(() -> new PersonNotFoundException(personNotFoundExMsg + "id = " + personDto.getId()));

        person.setFirstName(personDto.getFirstName());
        person.setLastName(personDto.getLastName());
        person.setEmail(personDto.getEmail());
        person.setRole(personDto.getRole());

        personRepository.save(person);

        return personDto;
    }

    @Override
    public List<PersonDto> getAllPersonByRole(Role role) {
        Collection<Person> persons = personRepository.getPersonByRole(role);

        List<PersonDto> result = persons.stream().map(person -> {
            PersonDto personDto = new PersonDto(
                    person.getId(), person.getFirstName(), person.getLastName(),
                    person.getEmail(), person.getRole().toString()
            );
            return personDto;
        }).collect(Collectors.toList());

        return result;
    }

    @Override
    public List<PersonDto> getAllPerson(PersonSearchDto personSearchDto) {
        Collection<Person> persons = new ArrayList<>();
        if ((personSearchDto.getRole() != null) && (personSearchDto.getEmail() != null)){
            persons = personDAO.getAllByEmailAndRole(personSearchDto.getEmail(),
                    Role.valueOf(personSearchDto.getRole().toUpperCase()));
        } else if (personSearchDto.getRole() != null){
            persons = personDAO.getAllByRole(Role.valueOf(personSearchDto.getRole().toUpperCase()));
        } else if (personSearchDto.getEmail() != null){
            Person person = personDAO.getPersonByEmail(personSearchDto.getEmail());
            if (person != null){
                persons.add(person);
            }
        }

        List<PersonDto> result = persons.stream().map(person -> {
            PersonDto personDto = new PersonDto(
                    person.getId(), person.getFirstName(), person.getLastName(),
                    person.getEmail(), person.getRole().toString()
            );
            return personDto;
        }).collect(Collectors.toList());

        return result;
    }
}
