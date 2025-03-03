package ru.project.cardaccessapi.dto;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import ru.project.cardaccessapi.models.EntryHistory;
import ru.project.cardaccessapi.models.JobTitle;

import java.util.List;

public class PersonDTO {

    private int id;

    @Pattern(regexp = "[A-ZА-Я][a-zа-я]+ [A-ZА-Я][a-zа-я]+ [A-ZА-Я][a-zа-я]+", message = "Wrong name format.")
    @NotEmpty(message = "Name should not be empty.")
    @Size(max = 100, min = 5, message = "Name must be from 5 to 100 symbols long.")
    private String name;

    @NotEmpty
    private String role;

    @Size(min = 3, max = 100, message = "Username must be from 8 to 100 symbols long.")
    private String username;

    @Size(min = 8, max = 100, message = "Password must be from 8 to 100 symbols long.")
    private String password;

    private int jobTitleId;

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

    public int getJobTitleId() {
        return jobTitleId;
    }

    public void setJobTitleId(int jobTitleId) {
        this.jobTitleId = jobTitleId;
    }

    @Override
    public String toString() {
        return "PersonDTO{" +
                "name='" + name + '\'' +
                ", role='" + role + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", jobTitleId=" + jobTitleId +
                '}';
    }
}
