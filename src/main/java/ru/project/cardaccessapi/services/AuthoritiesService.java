package ru.project.cardaccessapi.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.project.cardaccessapi.models.Authority;
import ru.project.cardaccessapi.repositories.AuthoritiesRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class AuthoritiesService {

    private final AuthoritiesRepository authoritiesRepository;

    public AuthoritiesService(AuthoritiesRepository authoritiesRepository) {
        this.authoritiesRepository = authoritiesRepository;
    }

    public Optional<Authority> findByName(String name) {
        return authoritiesRepository.findByName(name);
    }

    @Transactional
    public void save(Authority authority) {
        authoritiesRepository.save(authority);
    }

    public List<Authority> findAll() {
        return authoritiesRepository.findAll();
    }

    public List<Authority> findAllById(List<Integer> authoritiesId) {
        return authoritiesRepository.findAllById(authoritiesId);
    }

    @Transactional
    public void deleteById(int id) {
        authoritiesRepository.deleteById(id);
    }
}
