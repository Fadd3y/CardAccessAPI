package ru.project.cardaccessapi.util;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

public class AccessRequest {

    @Min(value = 1, message = "'personId' field is empty")
    private int personId;

    @NotEmpty(message = "'authority' field is empty")
    private String authority;

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
