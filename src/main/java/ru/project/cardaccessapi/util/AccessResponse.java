package ru.project.cardaccessapi.util;

public class AccessResponse {

    private AccessType accessType;
    private String message;

    public AccessResponse() {
    }

    public AccessResponse(AccessType accessType, String message) {
        this.accessType = accessType;
        this.message = message;
    }

    public AccessType getAccessType() {
        return accessType;
    }

    public void setAccessType(AccessType accessType) {
        this.accessType = accessType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
