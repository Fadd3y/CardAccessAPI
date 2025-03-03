package ru.project.cardaccessapi.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.project.cardaccessapi.models.Person;
import ru.project.cardaccessapi.repositories.PeopleRepository;

@Service
public class RegisterService {

    private final PeopleRepository peopleRepository;
    private final PasswordEncoder passwordEncoder;

    public RegisterService(PeopleRepository peopleRepository, PasswordEncoder passwordEncoder) {
        this.peopleRepository = peopleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(Person person) {
        String encodedPassword = encodePassword(person.getPassword());
        person.setPassword(encodedPassword);
        peopleRepository.save(person);
    }

    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}
