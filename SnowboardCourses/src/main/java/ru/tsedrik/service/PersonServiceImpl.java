package ru.tsedrik.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tsedrik.controller.dto.PageDto;
import ru.tsedrik.controller.dto.PersonDto;
import ru.tsedrik.controller.dto.PersonSearchDto;
import ru.tsedrik.exception.PersonNotFoundException;
import ru.tsedrik.domain.Person;
import ru.tsedrik.domain.Role;
import ru.tsedrik.repository.PersonRepository;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

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
    public PageDto<PersonDto> getPersons(PersonSearchDto personSearchDto, Pageable pageable) {

        Page<Person> page = personRepository.findAll(getSpecification(personSearchDto), pageable);

        List<PersonDto> persons = page
                .map(person ->
                        new PersonDto(person.getId(), person.getFirstName(), person.getLastName(),
                                person.getEmail(), person.getRole().toString())
                )
                .toList();

        return new PageDto<>(persons, page.getTotalElements());
    }

    private Specification<Person> getSpecification(PersonSearchDto personSearchDto) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (personSearchDto.getRole() != null) {
                predicates.add(root.get("role").in(Role.valueOf(personSearchDto.getRole().toUpperCase())));
            }

            if (personSearchDto.getEmail() != null) {
                predicates.add(root.get("email").in(personSearchDto.getEmail()));
            }

            if (personSearchDto.getFirstName() != null){
                predicates.add(root.get("firstName").in(personSearchDto.getFirstName()));
            }

            if (personSearchDto.getLastName() != null){
                predicates.add(root.get("lastName").in(personSearchDto.getLastName()));
            }

            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}
