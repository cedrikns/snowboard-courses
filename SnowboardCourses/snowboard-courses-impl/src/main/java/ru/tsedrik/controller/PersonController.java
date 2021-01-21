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
import ru.tsedrik.aspect.annotation.Audit;
import ru.tsedrik.aspect.annotation.AuditCode;
import ru.tsedrik.resource.PersonResource;
import ru.tsedrik.resource.dto.PageDto;
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
    public ResponseEntity<PersonDto> createPerson(@Validated @RequestBody PersonDto personDto, UriComponentsBuilder uriComponentsBuilder){
        logger.debug("createPerson with {} - start ", personDto);
        PersonDto resultPersonDto = personService.addPerson(personDto);
        URI uri = uriComponentsBuilder.path("/api/v1/person/" + resultPersonDto.getId()).buildAndExpand(resultPersonDto).toUri();
        logger.debug("createPerson end with result {}", resultPersonDto);
        return ResponseEntity.created(uri).body(resultPersonDto);
    }

    @Override
    public PersonDto getPerson(@PathVariable Long id){
        logger.debug("getPerson with {} - start ", id);
        PersonDto personDto = personService.getPersonById(id);
        logger.debug("getPerson end with result {}", personDto);
        return personDto;
    }

    @Override
    public PageDto<PersonDto> getPersons(@RequestBody PersonSearchDto personSearchDto,
                                         @PageableDefault(value = 5) @SortDefault(value = "id") Pageable pageable){
        logger.debug("getPersons with {}, {} - start ", personSearchDto, pageable);
        PageDto<PersonDto> result = personService.getPersons(personSearchDto, pageable);
        logger.debug("getPersons end with result {}", result);
        return result;
    }

    @Audit(AuditCode.PERSON_DELETE)
    @Override
    public boolean deletePerson(@PathVariable Long id){
        logger.debug("deletePerson with {} - start ", id);
        boolean isDeleted = personService.deletePersonById(id);
        logger.debug("deletePerson end with result {}", isDeleted);
        return isDeleted;
    }

    @Audit(AuditCode.PERSON_UPDATE)
    @Override
    public PersonDto updatePerson(@PathVariable Long id, @RequestBody PersonDto personDto){
        logger.debug("updatePerson with {}, {} - start ", id, personDto);
        if (!id.equals(personDto.getId())){
            throw new IllegalArgumentException("Идентификатор в пути запроса " + id + " не совпадает с идентификатором в теле запроса " + personDto.getId());
        }
        personService.updatePerson(personDto);
        logger.debug("updatePerson end with result {}", personDto);
        return personDto;
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
