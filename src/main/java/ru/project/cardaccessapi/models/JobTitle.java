package ru.project.cardaccessapi.models;

import jakarta.persistence.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "job_title")
public class JobTitle {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "authority_job_title",
            joinColumns = @JoinColumn(name = "job_title_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id")
    )
    private List<Authority> owningAuthorities;

    @OneToMany(mappedBy = "jobTitle")
    private List<Person> people;

    public JobTitle(int id) {
        this.id = id;
    }

    public JobTitle() {
    }

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

    public List<Authority> getOwningAuthorities() {
        if (owningAuthorities == null) {
            owningAuthorities = new ArrayList<>();
        }
        return owningAuthorities;
    }

    public void setOwningAuthorities(List<Authority> owningAuthorities) {
        this.owningAuthorities = owningAuthorities;
    }

    public List<Person> getPeople() {
        return people;
    }

    public void addAuthority(Authority authority) {
        if (owningAuthorities == null) {
            owningAuthorities = new ArrayList<>();
        }
        owningAuthorities.add(authority);

        if (authority.getJobsOwningAuthority() == null) {
            authority.setJobsOwningAuthority(new ArrayList<>());
        }
        if (!authority.getJobsOwningAuthority().contains(this)) {
            authority.getJobsOwningAuthority().add(this);
        }
    }

    public void setPeople(List<Person> people) {
        this.people = people;
    }
}
