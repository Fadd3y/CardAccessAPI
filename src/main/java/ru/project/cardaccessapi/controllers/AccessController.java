package ru.project.cardaccessapi.controllers;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.project.cardaccessapi.services.PeopleService;
import ru.project.cardaccessapi.util.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AccessController {

    private final PeopleService peopleService;
    private final AccessRequestValidator validator;

    public AccessController(PeopleService peopleService, AccessRequestValidator validator) {
        this.peopleService = peopleService;
        this.validator = validator;
    }

    @PostMapping("/access")
    public ResponseEntity<AccessResponse> getAccess(@RequestBody @Valid AccessRequest request,
                                                    BindingResult bindingResult) {
        validator.validate(request, bindingResult);

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(
                    new AccessResponse(AccessType.DENIED, createErrorMessage(bindingResult)),
                    HttpStatus.BAD_REQUEST
            );
        }

        if (peopleService.checkAccess(request.getPersonId(), request.getAuthority())) {
            return new ResponseEntity<>(
                    new AccessResponse(AccessType.ACCEPTED, "Access granted"),
                    HttpStatus.OK);
        } else {
            return new ResponseEntity<>(
                    new AccessResponse(AccessType.DENIED, "Access denied"),
                    HttpStatus.OK);
        }
    }

//    @ExceptionHandler({PersonNotFoundException.class, AuthorityNotFoundException.class})
//    public ResponseEntity<AccessResponse> errorHandling(RuntimeException e) {
//        return new ResponseEntity<>(new AccessResponse(AccessType.DENIED, "error: " + e.getMessage()), HttpStatus.BAD_REQUEST);
//    }

    public String createErrorMessage(BindingResult bindingResult) {
        StringBuilder builder = new StringBuilder();
        List<FieldError> errors = bindingResult.getFieldErrors();
        for (FieldError error : errors) {
            builder.append("field: ").append(error.getField()).append(", ")
                    .append("error_message: ").append(error.getDefaultMessage())
                    .append("; ");
        }

        return builder.toString();
    }
}
