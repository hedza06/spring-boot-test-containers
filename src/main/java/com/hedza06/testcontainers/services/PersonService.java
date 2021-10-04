package com.hedza06.testcontainers.services;

import com.hedza06.testcontainers.entities.Person;
import com.hedza06.testcontainers.repositories.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    /**
     * Storing new person
     *
     * @param person given person object
     * @return persisted Person object
     */
    public Person save(Person person) {
        return personRepository.save(person);
    }
}
