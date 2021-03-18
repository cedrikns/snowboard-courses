package ru.tsedrik.service;

import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.tsedrik.exception.PersonNotFoundException;
import ru.tsedrik.domain.Person;
import ru.tsedrik.domain.Role;
import ru.tsedrik.repository.PersonRepository;
import ru.tsedrik.resource.dto.PersonDto;
import ru.tsedrik.resource.dto.PersonSearchDto;

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
     * Объект для маппинга между классами Person и PersonDto
     */
    private MapperFacade mapperFacade;

    /**
     * Шаблон сообщения об ошибке для исключения PersonNotFoundException
     */
    @Value("${exception.personNotFound}")
    private String personNotFoundExMsg;

    public PersonServiceImpl(PersonRepository personRepository, MapperFacade mapperFacade){
        this.personRepository = personRepository;
        this.mapperFacade = mapperFacade;
    }

    @Override
    public Mono<PersonDto> addPerson(PersonDto personDto) {
        Person person = mapperFacade.map(personDto, Person.class);
        person.setId(System.currentTimeMillis());

        Mono<Void> dbRequest = Mono.fromRunnable(() -> personRepository.save(person));

        return Mono.when(dbRequest).then(Mono.fromSupplier(() -> mapperFacade.map(person, PersonDto.class)));
    }

    @Override
    public Mono<PersonDto> getPersonById(Long id) {
        return Mono.fromSupplier(() -> personRepository.findById(id)
                .map(person -> mapperFacade.map(person, PersonDto.class))
                .orElseThrow(() -> new PersonNotFoundException(personNotFoundExMsg + "id = " + id)));
    }

    @Override
    public Mono<Boolean> deletePersonById(Long id) {
        return Mono.fromCallable(() -> {
            personRepository.deleteById(id);
            return true;
        });
    }

    @Override
    public Mono<Boolean> deletePerson(Person person) {
        return Mono.fromCallable(() -> {
            personRepository.delete(person);
            return true;
        });
    }

    @Override
    public Mono<PersonDto> updatePerson(PersonDto personDto) {
        return Mono.fromSupplier(() -> {
            Long id = personDto.getId();
            Person person = personRepository.findById(id)
                    .orElseThrow(() -> new PersonNotFoundException(personNotFoundExMsg + "id = " + id));
            mapperFacade.map(personDto, person);
            personRepository.save(person);
            return person;
        }).flatMap(person -> Mono.just(mapperFacade.map(person, PersonDto.class)));
   }

    @Override
    public Flux<PersonDto> getPersons(PersonSearchDto personSearchDto, Pageable pageable) {
        return Flux.fromIterable(personRepository.findAll(getSpecification(personSearchDto), pageable)
                .map(person -> mapperFacade.map(person, PersonDto.class))
                .toList());
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
