package ru.project.cardaccessapi.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.project.cardaccessapi.dto.AuthorityDTO;
import ru.project.cardaccessapi.dto.JobTitleDTO;
import ru.project.cardaccessapi.dto.PersonDTO;
import ru.project.cardaccessapi.models.Authority;
import ru.project.cardaccessapi.models.JobTitle;
import ru.project.cardaccessapi.models.Person;
import ru.project.cardaccessapi.services.*;
import ru.project.cardaccessapi.util.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final PeopleService peopleService;
    private final ModelMapper modelMapper;
    private final JobTitleService jobTitleService;
    private final RegisterService registerService;
    private final AuthoritiesService authoritiesService;
    private final EntryHistoryService entryHistoryService;
    private final PersonDTOValidator personDTOValidator;
    private final AuthorityDTOValidator authorityDTOValidator;
    private final JobTitleDTOValidator jobTitleDTOValidator;

    public AdminController(PeopleService peopleService, ModelMapper modelMapper, JobTitleService jobTitleService, RegisterService registerService, AuthoritiesService authoritiesService, EntryHistoryService entryHistoryService, EntryHistoryService entryHistoryService1, PersonDTOValidator personDTOValidator, AuthorityDTOValidator authorityDTOValidator, JobTitleDTOValidator jobTitleDTOValidator) {
        this.peopleService = peopleService;
        this.modelMapper = modelMapper;
        this.jobTitleService = jobTitleService;
        this.registerService = registerService;
        this.authoritiesService = authoritiesService;
        this.entryHistoryService = entryHistoryService1;
        this.personDTOValidator = personDTOValidator;
        this.authorityDTOValidator = authorityDTOValidator;
        this.jobTitleDTOValidator = jobTitleDTOValidator;
    }

    //СОТРУДНИКИ
    //ВСЕ СОТРУДНИКИ
    @GetMapping("/people")
    public String showPeople(Model model) {
        List<Person> people = peopleService.findAll();
        model.addAttribute("people", people);
        return "admin/people/show";
    }

    //СТРАНИЦА СОЗДАНИЯ
    @GetMapping("/people/new")
    public String userCreationPage(@ModelAttribute("person") PersonDTO personDTO,
                                   Model model) {
        model.addAttribute("jobTitles", jobTitleService.findAll());
        return "admin/people/create";
    }

    //ДОБАВЛЕНИЕ СОТРУДНИКА
    @PostMapping("/people")
    public String registerPerson(@ModelAttribute("person") @Valid PersonDTO personDTO,
                                 BindingResult bindingResult) {
        personDTOValidator.validate(personDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            return "admin/people/create";
        }

        Person person = convertToPerson(personDTO);
        registerService.register(person);
        return "redirect:/homepage";
    }

    //ИНФОРМАЦИЯ СОТРУДНИКА
    @GetMapping("/people/{id}")
    public String showPerson(Model model, @PathVariable("id") int id) {
        model.addAttribute("admin_tools", true);
        model.addAttribute("person", peopleService.findByIdWithAuthorities(id)
                .orElseThrow(() -> new PersonNotFoundException("Person not found")));
        return "people/info";
    }

    //СТРАНИЦА РЕДАКТИРОВАНИЯ
    @GetMapping("/people/{id}/edit")
    public String editPersonPage(@PathVariable("id") int id,
                                 Model model) {
        Person person = peopleService.findById(id)
                .orElseThrow(() -> new PersonNotFoundException("Person not found"));
        model.addAttribute("personToUpdate", modelMapper.map(person, PersonDTO.class));
        model.addAttribute("jobTitles", jobTitleService.findAll());
        return "admin/people/edit";
    }

    //ВНЕСЕНИЕ ИЗМЕНЕНИЙ
    @PatchMapping("/people/{id}")
    public String editPerson(@PathVariable("id") int id,
                             @ModelAttribute("personToUpdate") @Valid PersonDTO personDTO,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/people/edit";
        }

        Person person = convertToPerson(personDTO);
        person.setId(id);
        peopleService.update(person);
        return "redirect:/admin/people/{id}";
    }

    //СТРАНИЦИА ИЗМЕНЕНИЯ ПАРОЛЯ
    @GetMapping("/people/{id}/password/new")
    public String changePasswordPage(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", modelMapper.map(peopleService.findById(id), PersonDTO.class));
        return "auth/change";
    }

    //ВНЕСЕНИЕ ИЗМЕНЕНИЙ ПАРОЛЯ
    @PatchMapping("/people/{id}/password")
    public String changePassword(@PathVariable("id") int id, @RequestParam("password") String password) {
        String encodedPassword = registerService.encodePassword(password);
        Person person = peopleService.findById(id)
                .orElseThrow(() -> new PersonNotFoundException("Person not found"));;
        person.setPassword(encodedPassword);
        peopleService.update(person);
        return "redirect:/people/{id}";
    }

    //УДАЛИТЬ ЧЕЛОВЕКА
    @DeleteMapping("/people/{id}")
    public String deletePerson(@PathVariable("id") int id) {
        peopleService.deleteById(id);
        return "redirect:/admin/people";
    }

    //ПРАВА ДОСТУПА
    //ВСЕ ПРАВА ДОСТУПА
    @GetMapping("/authorities")
    public String authoritiesPage(Model model) {
        model.addAttribute("authorities", authoritiesService.findAll());
        return "admin/authorities/show";
    }

    //СТРАНИЦА ДОБАВЛЕНИЯ ПРАВА
    @GetMapping("/authorities/new")
    public String createAuthorityPage(@ModelAttribute("authority") AuthorityDTO authorityDTO) {
        return "admin/authorities/create";
    }

    //ДОБАВЛЕНИЕ ПРАВА
    @PostMapping("/authorities")
    public String saveAuthority(@ModelAttribute("authority") @Valid AuthorityDTO authorityDTO,
                                BindingResult bindingResult) {
        authorityDTOValidator.validate(authorityDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            return "admin/authorities/create";
        }

        Authority authority = modelMapper.map(authorityDTO, Authority.class);
        authoritiesService.save(authority);
        return "redirect:/homepage";
    }

    //УДАЛИТЬ ПРАВО
    @DeleteMapping("/authorities/{id}")
    public String deleteAuthority(@PathVariable("id") int id) {
        authoritiesService.deleteById(id);
        return "redirect:/admin/authorities";
    }

    //ДОЛЖНОСТИ
    //СПИСОК ДОЛЖНОСТЕЙ
    @GetMapping("/job_titles")
    public String jobTitlesPage(Model model) {
        model.addAttribute("jobTitles", jobTitleService.findAllWithAuthorities());
        return "admin/jobTitles/show";
    }

    //СТРАНИЦА СОЗДАНИЯ ДОЛЖНОСТИ
    @GetMapping("/job_titles/new")
    public String createJobTitlePage(@ModelAttribute("job_title") JobTitleDTO jobTitleDTO, Model model) {
        model.addAttribute("authorities", authoritiesService.findAll());
        return "admin/jobTitles/create";
    }

    //СОЗДАНИЕ ДОЛЖНОСТИ
    @PostMapping("/job_titles")
    public String saveJobTitle(@ModelAttribute("job_title") @Valid JobTitleDTO jobTitleDTO,
                               BindingResult bindingResult,
                               Model model) {
        jobTitleDTOValidator.validate(jobTitleDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("authorities", authoritiesService.findAll());
            return "admin/jobTitles/create";
        }
        jobTitleService.save(convertToJobTitle(jobTitleDTO));
        return "redirect:/homepage";
    }

    //СТРАНИЦА РЕДАКТИРОВАНИЯ ДОЛЖНОСТИ
    @GetMapping("/job_titles/{id}/edit")
    public String editJobTitlePage(@PathVariable("id") int id,
                                   Model model) {
        JobTitle jobTitle = jobTitleService.findByIdWithAuthorities(id)
                .orElseThrow(() -> new JobTitleNotFoundException("Job title not found"));;
        model.addAttribute("jobTitle", convertToJobTitleDTO(jobTitle));
        model.addAttribute("authorities", authoritiesService.findAll());
        return "admin/jobTitles/edit";
    }

    //ВНЕСЕНИЕ ИЗМЕНЕНИЙ ДОЛЖНОСТИ
    @PatchMapping("/job_titles/{id}")
    public String updateJobTitle(@PathVariable("id") int id,
                                 @ModelAttribute("jobTitle") @Valid JobTitleDTO jobTitleDTO,
                                 BindingResult bindingResult,
                                 Model model) {
        jobTitleDTOValidator.validate(jobTitleDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("authorities", authoritiesService.findAll());
            return "admin/jobTitles/edit";
        }
        jobTitleService.save(convertToJobTitle(jobTitleDTO));
        return "redirect:/admin/job_titles";
    }

    //УДАЛЕНИЕ ДОЛЖНОСТИ
    @DeleteMapping("/job_titles/{id}")
    public String deleteJobTitle(@PathVariable("id") int id) {
        jobTitleService.deleteById(id);
        return "redirect:/admin/job_titles";
    }

    //ИСТОРИЯ
    //СТРАНИЦА ИСТОРИИ СОТРУДНИКА
    @GetMapping("/people/{id}/entries")
    public String entriesPage(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", peopleService.findById(id)
                .orElseThrow(() -> new PersonNotFoundException("Person not found")));
        model.addAttribute("entries", entryHistoryService.findAllEntriesById(id));
        return "admin/entries/show";
    }

    @ExceptionHandler({PersonNotFoundException.class, JobTitleNotFoundException.class, AuthorityNotFoundException.class})
    @GetMapping("/error")
    public String exceptionHandling(RuntimeException e, Model model) {
        model.addAttribute("message", e.getMessage());
        return "error";
    }

    private Person convertToPerson(PersonDTO personDTO) {
        Person person = new Person();
        person.setName(personDTO.getName());
        person.setUsername(personDTO.getUsername());
        person.setPassword(personDTO.getPassword());
        person.setRole(personDTO.getRole());
        person.setJobTitle(personDTO.getJobTitleId() == 0 ? null : new JobTitle(personDTO.getJobTitleId()));
        //person.setJobTitle(jobTitleService.findById(personDTO.getJobTitleId()).orElse(null));
        return person;
    }

    private JobTitle convertToJobTitle(JobTitleDTO jobTitleDTO) {
        JobTitle jobTitle = new JobTitle();
        jobTitle.setId(jobTitleDTO.getId());
        jobTitle.setName(jobTitleDTO.getName());

        List<Authority> authorities = authoritiesService.findAllById(jobTitleDTO.getOwningAuthoritiesId());
        jobTitle.setOwningAuthorities(authorities);

        authorities.forEach(authority -> authority.getJobsOwningAuthority().add(jobTitle));
        return jobTitle;
    }

    public JobTitleDTO convertToJobTitleDTO(JobTitle jobTitle) {
        JobTitleDTO jobTitleDTO = new JobTitleDTO();
        jobTitleDTO.setId(jobTitle.getId());
        jobTitleDTO.setName(jobTitle.getName());
        jobTitleDTO.setOwningAuthoritiesId(jobTitle.getOwningAuthorities().stream().map(Authority::getId).toList());
        return jobTitleDTO;
    }
}
