package ru.project.cardaccessapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.project.cardaccessapi.models.EntryHistory;

import java.util.List;

@Repository
public interface EntryHistoryRepository extends JpaRepository<EntryHistory, Integer> {

    @Query("""
            select e from EntryHistory e where e.person.id=:id
            """)
    List<EntryHistory> findAllEntriesById(@Param("id") int id);
}
