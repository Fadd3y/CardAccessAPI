package ru.project.cardaccessapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.project.cardaccessapi.models.Person;
import ru.project.cardaccessapi.security.PersonDetails;
import ru.project.cardaccessapi.services.PeopleService;

@Controller
public class PeopleController {

    private final PeopleService peopleService;

    @Autowired
    public PeopleController(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @GetMapping("/homepage")
    public String startPage(Model model) {
        Authentication contextHolder = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) contextHolder.getPrincipal();
        Person person = peopleService.findById(personDetails.getPerson().getId()).get();
        model.addAttribute("person",person);
        model.addAttribute("role", person.getRole());
        return "/homepage";
    }

    @GetMapping("people/info")
    public String infoPage(Model model) {
        Authentication contextHolder = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) contextHolder.getPrincipal();
        Person person = peopleService.findByIdWithAuthorities(personDetails.getPerson().getId()).get();

        model.addAttribute("person", person);

        return "people/info";
    }

}
