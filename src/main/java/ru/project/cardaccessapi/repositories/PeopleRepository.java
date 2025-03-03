package ru.project.cardaccessapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.project.cardaccessapi.models.Person;

import java.util.List;
import java.util.Optional;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {

    @Query("""
            select case when count(a) > 0 then true else false end
            from Person p
            join p.jobTitle j
            join j.owningAuthorities a
            where p.id = :personId and a.name = :authorityName
            """)
    boolean hasAccess(@Param("personId") int personId, @Param("authorityName") String authorityName);

    Optional<Person> findByUsername(String username);

    @Query("""
            select p from Person p left join fetch p.jobTitle j left join fetch j.owningAuthorities where p.id = :personId
            """)
    Optional<Person> findByIdWithAuthorities(@Param("personId") int personId);
}
