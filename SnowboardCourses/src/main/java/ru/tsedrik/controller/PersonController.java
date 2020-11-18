package ru.tsedrik.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.tsedrik.controller.dto.PersonDto;
import ru.tsedrik.model.Role;
import ru.tsedrik.service.PersonService;
import ru.tsedrik.validator.PersonDtoValidator;

import java.net.URI;
import java.util.List;


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

    @GetMapping(value = "/{id}")
    public PersonDto getPerson(@PathVariable Long id){
        PersonDto personDto = personService.getPersonById(id);
        return personDto;
    }

    @GetMapping(params = "email")
    public PersonDto getAllPersonByEmail(@RequestParam String email){
        PersonDto personDto = personService.getPersonByEmail(email);
        return personDto;
    }

    @GetMapping(params = "role")
    public List<PersonDto> getAllPersonByRole(@RequestParam String role){
        List<PersonDto> personsWithRole = personService.getAllPersonByRole(Role.valueOf(role.toUpperCase()));
        return personsWithRole;
    }

    @DeleteMapping(value = "/{id}")
    public boolean deletePerson(@PathVariable Long id){
        boolean isDeleted = personService.deletePersonById(id);
        return isDeleted;
    }

    @PutMapping(value = "/{id}")
    public PersonDto updatePerson(@PathVariable Long id, @RequestBody PersonDto personDto){
        personService.updatePerson(id, personDto);
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
