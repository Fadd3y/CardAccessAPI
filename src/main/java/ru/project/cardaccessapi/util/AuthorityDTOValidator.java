package ru.project.cardaccessapi.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.project.cardaccessapi.dto.AuthorityDTO;
import ru.project.cardaccessapi.services.AuthoritiesService;

@Component
public class AuthorityDTOValidator implements Validator {
    private final AuthoritiesService authoritiesService;

    public AuthorityDTOValidator(AuthoritiesService authoritiesService) {
        this.authoritiesService = authoritiesService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return AuthorityDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AuthorityDTO authorityDTO = (AuthorityDTO) target;

        if (authoritiesService.findByName(authorityDTO.getName()).isPresent()) {
            errors.rejectValue("name", "", "This authority already exist");
        }
    }
}
