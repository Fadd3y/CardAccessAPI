package ru.project.cardaccessapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.project.cardaccessapi.models.Person;
import ru.project.cardaccessapi.repositories.PeopleRepository;
import ru.project.cardaccessapi.util.AuthorityNotFoundException;
import ru.project.cardaccessapi.util.PersonNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {

    private final PeopleRepository peopleRepository;
    private final EntryHistoryService entryHistoryService;
    private final AuthoritiesService authoritiesService;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository, EntryHistoryService entryHistoryService, AuthoritiesService authoritiesService) {
        this.peopleRepository = peopleRepository;
        this.entryHistoryService = entryHistoryService;
        this.authoritiesService = authoritiesService;
    }

    public boolean checkAccess(int personId, String authorityName) {
        boolean hasAccess = peopleRepository.hasAccess(
                personId,
                authorityName
        );

        if (hasAccess) {
            entryHistoryService.addEntryRecord(
                    findById(personId)
                            .orElseThrow(() -> new PersonNotFoundException("Person not found")),
                    authoritiesService.findByName(authorityName)
                            .orElseThrow(() -> new AuthorityNotFoundException("Authority not found"))
            );
        }

        return hasAccess;
    }

    public Optional<Person> findById(int personId) {
        return peopleRepository.findById(personId);
    }

    public Optional<Person> findByIdWithAuthorities(int personId) {
         return peopleRepository.findByIdWithAuthorities(personId);
    }

    @Transactional
    public void save(Person person) {
        peopleRepository.save(person);
    }

    public List<Person> findAll() {
        return  peopleRepository.findAll(Sort.by("name"));
    }

    @Transactional
    public void update(Person person) {
        Person personToBeUpdated = peopleRepository.findById(person.getId())
                .orElseThrow(() -> new PersonNotFoundException("Person not found"));
        personToBeUpdated.setName(person.getName());
        personToBeUpdated.setRole(person.getRole());
//        if (person.getJobTitle() != null) {
//            JobTitle jobTitle = jobTitleService.findById(person.getJobTitle().getId()).get();
//            personToBeUpdated.setJobTitle(jobTitle);
//        } else {
//            personToBeUpdated.setJobTitle(null);
//        }
        personToBeUpdated.setJobTitle(person.getJobTitle());
    }

    @Transactional
    public void deleteById(int id) {
        peopleRepository.deleteById(id);
    }

    public Optional<Person> findByUsername(String username) {
         return peopleRepository.findByUsername(username);
    }
}
