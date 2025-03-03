package ru.project.cardaccessapi.models;

import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "authority")
public class Authority {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "owningAuthorities")
    private List<JobTitle> jobsOwningAuthority;

    @OneToMany(mappedBy = "authority")
    private List<EntryHistory> entryHistories;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<JobTitle> getJobsOwningAuthority() {
        if (jobsOwningAuthority == null) {
            jobsOwningAuthority = new ArrayList<>();
        }
        return jobsOwningAuthority;
    }

    public void setJobsOwningAuthority(List<JobTitle> jobsOwningAuthority) {
        this.jobsOwningAuthority = jobsOwningAuthority;
    }

    public List<EntryHistory> getEntryHistories() {
        return entryHistories;
    }

    public void setEntryHistories(List<EntryHistory> entryHistories) {
        this.entryHistories = entryHistories;
    }
}
