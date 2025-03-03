package ru.project.cardaccessapi.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class AuthorityDTO {

    private int id;

    @Pattern(regexp = "[A-ZА-Я][a-zа-я]+", message = "Authority should start with capital letter")
    @NotEmpty(message = "Authority name cant be empty")
    @Size(min = 3, max = 100, message = "Size should be between 3 and 100 characters")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
