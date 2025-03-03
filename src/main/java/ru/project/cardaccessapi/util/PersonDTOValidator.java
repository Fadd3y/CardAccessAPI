package ru.project.cardaccessapi.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.project.cardaccessapi.dto.PersonDTO;
import ru.project.cardaccessapi.services.PeopleService;

@Component
public class PersonDTOValidator implements Validator {
    private final PeopleService peopleService;

    public PersonDTOValidator(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return PersonDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PersonDTO personDTO = (PersonDTO) target;

        if (personDTO.getUsername() != null && peopleService.findByUsername(personDTO.getUsername()).isPresent()) {
            errors.rejectValue("username", "", "Username already taken");
        }
    }
}
