package com.hedza06.testcontainers.controllers;

import com.hedza06.testcontainers.entities.Person;
import com.hedza06.testcontainers.services.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/person", produces = MediaType.APPLICATION_JSON_VALUE)
public class PersonController {

    private final PersonService personService;

    @PostMapping
    public ResponseEntity<Person> store(@RequestBody Person person)
    {
        Person storedPerson = personService.save(person);
        return new ResponseEntity<>(storedPerson, HttpStatus.CREATED);
    }
}
