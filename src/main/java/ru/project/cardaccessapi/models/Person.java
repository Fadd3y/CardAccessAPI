package ru.project.cardaccessapi.models;

import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;

import java.util.List;

@Entity
@Table(name = "person")
public class Person {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "system_role")
    private String role;

    @OneToMany(mappedBy = "person")
    private List<EntryHistory> entryHistories;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @ManyToOne()
    @JoinColumn(name = "job_title_id", referencedColumnName = "id")
    private JobTitle jobTitle;


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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<EntryHistory> getEntryHistories() {
        return entryHistories;
    }

    public void setEntryHistories(List<EntryHistory> entryHistories) {
        this.entryHistories = entryHistories;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public JobTitle getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(JobTitle jobTitle) {
        this.jobTitle = jobTitle;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", role='" + role + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", jobTitle=" + jobTitle +
                '}';
    }
}
