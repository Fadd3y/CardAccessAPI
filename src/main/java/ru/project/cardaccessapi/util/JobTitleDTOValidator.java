package ru.project.cardaccessapi.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.project.cardaccessapi.dto.JobTitleDTO;
import ru.project.cardaccessapi.services.JobTitleService;

@Component
public class JobTitleDTOValidator implements Validator {

    private final JobTitleService jobTitleService;

    public JobTitleDTOValidator(JobTitleService jobTitleService) {
        this.jobTitleService = jobTitleService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return JobTitleDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        JobTitleDTO jobTitleDTO = (JobTitleDTO) target;

        if (jobTitleService.findByName(jobTitleDTO.getName()).isPresent()) {
            errors.rejectValue("name", "", "This job title already exist");
        }
    }
}
