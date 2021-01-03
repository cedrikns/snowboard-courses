package ru.tsedrik.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
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

    private PersonService personService;
    private PersonDtoValidator personDtoValidator;

    public PersonController(PersonService personService, PersonDtoValidator personDtoValidator){
        this.personService = personService;
        this.personDtoValidator = personDtoValidator;
    }

    @Override
    public ResponseEntity<PersonDto> createPerson(@Validated @RequestBody PersonDto personDto, UriComponentsBuilder uriComponentsBuilder){
        PersonDto resultPersonDto = personService.addPerson(personDto);
        URI uri = uriComponentsBuilder.path("/api/v1/person/" + resultPersonDto.getId()).buildAndExpand(resultPersonDto).toUri();
        return ResponseEntity.created(uri).body(resultPersonDto);
    }

    @Override
    public PersonDto getPerson(@PathVariable Long id){
        PersonDto personDto = personService.getPersonById(id);
        return personDto;
    }

    @Override
    public PageDto<PersonDto> getPersons(@RequestBody PersonSearchDto personSearchDto,
                                         @PageableDefault(value = 5) @SortDefault(value = "id") Pageable pageable){
        return personService.getPersons(personSearchDto, pageable);
    }

    @Override
    public boolean deletePerson(@PathVariable Long id){
        boolean isDeleted = personService.deletePersonById(id);
        return isDeleted;
    }

    @Override
    public PersonDto updatePerson(@PathVariable Long id, @RequestBody PersonDto personDto){
        if (!id.equals(personDto.getId())){
            throw new IllegalArgumentException("Идентификатор в пути запроса " + id + " не совпадает с идентификатором в теле запроса " + personDto.getId());
        }
        personService.updatePerson(personDto);
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
