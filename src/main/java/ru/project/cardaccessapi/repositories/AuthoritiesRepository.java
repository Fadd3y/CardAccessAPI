package ru.project.cardaccessapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.project.cardaccessapi.models.Authority;

import java.util.Optional;

@Repository
public interface AuthoritiesRepository extends JpaRepository<Authority, Integer> {
    Optional<Authority> findByName(String name);
}
