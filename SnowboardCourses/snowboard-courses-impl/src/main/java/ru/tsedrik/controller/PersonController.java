package ru.tsedrik.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.tsedrik.aspect.annotation.Audit;
import ru.tsedrik.aspect.annotation.AuditCode;
import ru.tsedrik.resource.PersonResource;
import ru.tsedrik.resource.dto.PersonDto;
import ru.tsedrik.resource.dto.PersonSearchDto;
import ru.tsedrik.service.PersonService;
import ru.tsedrik.validator.PersonDtoValidator;

import java.net.URI;

/**
 * Controller for Person
 */
@RestController
public class PersonController implements PersonResource {

    private static final Logger logger = LoggerFactory.getLogger(PersonController.class.getName());

    private PersonService personService;
    private PersonDtoValidator personDtoValidator;

    public PersonController(PersonService personService, PersonDtoValidator personDtoValidator){
        this.personService = personService;
        this.personDtoValidator = personDtoValidator;
    }

    @Audit(AuditCode.PERSON_CREATE)
    @Override
    public Mono<ResponseEntity<PersonDto>> createPerson(@Validated @RequestBody PersonDto personDto, UriComponentsBuilder uriComponentsBuilder){
        logger.debug("createPerson with {} - start ", personDto);
        Mono<PersonDto> resultPersonDto = personService.addPerson(personDto);
        return resultPersonDto.flatMap(createdPerson -> {
            URI uri = uriComponentsBuilder.path("/api/v1/location/" + createdPerson.getId()).buildAndExpand(createdPerson).toUri();
            return Mono.just(ResponseEntity.created(uri).body(createdPerson));
        }).doOnSuccess(result -> logger.debug("createPerson end with result {}", result));
    }

    @Override
    public Mono<PersonDto> getPerson(@PathVariable Long id){
        logger.debug("getPerson with {} - start ", id);
        Mono<PersonDto> personDto = personService.getPersonById(id);
        return personDto.doOnSuccess(result -> logger.debug("getPerson end with result {}", result));
    }

    @Override
    public Flux<PersonDto> getPersons(@RequestBody PersonSearchDto personSearchDto,
                                         @PageableDefault(value = 5) @SortDefault(value = "id") Pageable pageable){
        logger.debug("getPersons with {}, {} - start ", personSearchDto, pageable);
        Flux<PersonDto> result = personService.getPersons(personSearchDto, pageable);
        return result;
    }

    @Audit(AuditCode.PERSON_DELETE)
    @Override
    public Mono<Boolean> deletePerson(@PathVariable Long id){
        logger.debug("deletePerson with {} - start ", id);
        Mono<Boolean> isDeleted = personService.deletePersonById(id);
        return isDeleted.doOnSuccess(result -> logger.debug("deletePerson end with result {}", result));
    }

    @Audit(AuditCode.PERSON_UPDATE)
    @Override
    public Mono<PersonDto> updatePerson(@PathVariable Long id, @RequestBody PersonDto personDto){
        logger.debug("updatePerson with {}, {} - start ", id, personDto);
        if (!id.equals(personDto.getId())){
            throw new IllegalArgumentException("Идентификатор в пути запроса " + id + " не совпадает с идентификатором в теле запроса " + personDto.getId());
        }
        Mono<PersonDto> updatedPersonDto = personService.updatePerson(personDto);
        return updatedPersonDto.doOnSuccess(result -> logger.debug("updatePerson end with result {}", result));
    }

    @ModelAttribute
    public PersonDto personDto(){
        return new PersonDto();
    }

    @InitBinder(value = "personDto")
    private void initBinder(WebDataBinder binder){
        binder.setValidator(personDtoValidator);
    }
}
