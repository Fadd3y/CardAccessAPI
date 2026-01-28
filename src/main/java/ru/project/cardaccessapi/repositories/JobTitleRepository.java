package ru.project.cardaccessapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.project.cardaccessapi.models.JobTitle;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobTitleRepository extends JpaRepository<JobTitle, Integer> {

    @Query("""
            select j from JobTitle j left join fetch j.owningAuthorities
            """)
    List<JobTitle> findAllWithAuthorities();

    @Query("""
            select j from JobTitle j left join fetch j.owningAuthorities where j.id = :id
            """)
    Optional<JobTitle> findByIdWithAuthorities(@Param("id") int id);

    Optional<JobTitle> findByName(String name);

    @Query("""
        select j from JobTitle j where j.name = :name and j.id != :id
        """)
    Optional<JobTitle> findByNameAndNotSameId(String name, int id);
}
