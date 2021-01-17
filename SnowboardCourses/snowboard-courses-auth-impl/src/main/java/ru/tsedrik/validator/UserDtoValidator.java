package ru.tsedrik.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.tsedrik.exception.UserFormValidationException;
import ru.tsedrik.resource.dto.UserDto;

/**
 * Валидатор для полей формы по созданию сущности User
 */
@Component
public class UserDtoValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return UserDto.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserDto userDto = (UserDto) o;

        if (userDto.getUserName().isEmpty()){
            errors.rejectValue("userName", "userName.empty", "userName can't be empty");
        }

        if (errors.hasErrors()){
            throw new UserFormValidationException("Incorrect data for creating user.", errors);
        }

    }
}
