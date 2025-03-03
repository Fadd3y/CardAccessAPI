package ru.project.cardaccessapi.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.project.cardaccessapi.models.JobTitle;
import ru.project.cardaccessapi.repositories.JobTitleRepository;

import java.util.List;
import java.util.Optional;

@Service
public class JobTitleService {

    private final JobTitleRepository jobTitleRepository;

    public JobTitleService(JobTitleRepository jobTitleRepository) {
        this.jobTitleRepository = jobTitleRepository;
    }

    public Optional<JobTitle> findById(int titleId) {
        return jobTitleRepository.findById(titleId);
    }

    public List<JobTitle> findAll() {
        return jobTitleRepository.findAll();
    }

    @Transactional
    public void save(JobTitle jobTitle) {
        jobTitleRepository.save(jobTitle);
    }

    public List<JobTitle> findAllWithAuthorities() {
        return jobTitleRepository.findAllWithAuthorities();
    }

    public void deleteById(int id) {
        jobTitleRepository.deleteById(id);
    }

    public Optional<JobTitle> findByIdWithAuthorities(int id) {
        return jobTitleRepository.findByIdWithAuthorities(id);
    }

    public Optional<JobTitle> findByName(String name) {
        return jobTitleRepository.findByName(name);
    }

}
