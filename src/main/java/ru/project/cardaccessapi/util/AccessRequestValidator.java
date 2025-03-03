package ru.project.cardaccessapi.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.project.cardaccessapi.services.AuthoritiesService;
import ru.project.cardaccessapi.services.PeopleService;

@Component
public class AccessRequestValidator implements Validator {

    private final PeopleService peopleService;
    private final AuthoritiesService authoritiesService;

    public AccessRequestValidator(PeopleService peopleService, AuthoritiesService authoritiesService) {
        this.peopleService = peopleService;
        this.authoritiesService = authoritiesService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return AccessRequest.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AccessRequest request = (AccessRequest) target;

        if (peopleService.findById(request.getPersonId()).isEmpty()) {
            errors.rejectValue("personId", "", "Person not found");
        }

        if (authoritiesService.findByName(request.getAuthority()).isEmpty()) {
            errors.rejectValue("authority", "", "Authority not found");
        }
    }
}
