package ru.tsedrik.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.tsedrik.controller.dto.PersonDto;
import ru.tsedrik.exception.PersonFormValidationException;

@Component
public class PersonDtoValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return PersonDto.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        PersonDto personDto = (PersonDto) o;
        if (personDto.getEmail().isEmpty()){
            errors.rejectValue("email", "email.empty", "person email can't be empty");
        }

        if (personDto.getRole().isEmpty()){
            errors.rejectValue("role", "role.empty", "person role can't be empty");
        }

        if (errors.hasErrors()){
            throw new PersonFormValidationException("Incorrect data for creating person.", errors);
        }

    }
}
