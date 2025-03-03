package ru.project.cardaccessapi.dto;

import jakarta.persistence.Column;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import ru.project.cardaccessapi.models.Authority;

import java.util.List;

public class JobTitleDTO {

    private int id;

    @Pattern(regexp = "[A-ZА-Я][a-zа-я]+", message = "Job title should start with capital letter")
    @NotEmpty(message = "Job title cant be empty")
    @Size(min = 3, max = 50, message = "Name must be from 5 to 100 symbols long.")
    private String name;

    private List<Integer> owningAuthoritiesId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getOwningAuthoritiesId() {
        return owningAuthoritiesId;
    }

    public void setOwningAuthoritiesId(List<Integer> owningAuthoritiesId) {
        this.owningAuthoritiesId = owningAuthoritiesId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
