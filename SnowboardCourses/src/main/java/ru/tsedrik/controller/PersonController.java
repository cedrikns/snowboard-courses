package ru.tsedrik.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.tsedrik.controller.dto.PersonDto;
import ru.tsedrik.service.PersonService;
import ru.tsedrik.validator.PersonDtoValidator;

import java.net.URI;


/**
 * Controller for Person
 */
@RestController
@RequestMapping("/person")
public class PersonController {

    private PersonService personService;
    private PersonDtoValidator personDtoValidator;

    public PersonController(PersonService personService, PersonDtoValidator personDtoValidator){
        this.personService = personService;
        this.personDtoValidator = personDtoValidator;
    }

    @PostMapping
    public ResponseEntity<PersonDto> createPerson(@Validated @RequestBody PersonDto personDto, UriComponentsBuilder uriComponentsBuilder){
        PersonDto resultPersonDto = personService.addPerson(personDto);
        URI uri = uriComponentsBuilder.path("/person/" + resultPersonDto.getId()).buildAndExpand(resultPersonDto).toUri();
        return ResponseEntity.created(uri).body(resultPersonDto);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public PersonDto getPerson(@PathVariable Long id){
        PersonDto personDto = personService.getPersonById(id);
        return personDto;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public boolean deletePerson(@PathVariable Long id){
        boolean isDeleted = personService.deletePersonById(id);
        return isDeleted;
    }

    @PutMapping
    public PersonDto updatePerson(@RequestBody PersonDto personDto){
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
