package ru.tsedrik.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.tsedrik.controller.dto.PersonDto;
import ru.tsedrik.service.PersonService;


/**
 * Controller for Person
 */
@RestController
@RequestMapping("/person")
public class PersonController {

    private PersonService personService;

    @Autowired
    public PersonController(PersonService personService){
        this.personService = personService;
    }

    @PostMapping
    public PersonDto createPerson(@RequestBody PersonDto personDto){
        personService.addPerson(personDto);
        return personDto;
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
}
