package ru.project.cardaccessapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.project.cardaccessapi.models.Authority;
import ru.project.cardaccessapi.models.EntryHistory;
import ru.project.cardaccessapi.models.Person;
import ru.project.cardaccessapi.repositories.EntryHistoryRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class EntryHistoryService {

    private final EntryHistoryRepository entryHistoryRepository;

    @Autowired
    public EntryHistoryService(EntryHistoryRepository entryHistoryRepository) {
        this.entryHistoryRepository = entryHistoryRepository;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void addEntryRecord(Person person, Authority authority) {
        EntryHistory entryHistory = new EntryHistory();
        entryHistory.setPerson(person);
        entryHistory.setAuthority(authority);
        entryHistory.setDateTime(LocalDateTime.now());

        entryHistoryRepository.save(entryHistory);
    }

    public List<EntryHistory> findAllEntriesById(int id) {
        return entryHistoryRepository.findAllEntriesById(id);
    }
}
