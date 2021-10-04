package com.hedza06.testcontainers;

import com.hedza06.testcontainers.entities.Person;
import com.hedza06.testcontainers.repositories.PersonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Date;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
@SuppressWarnings("java:S3655")
public class PersonIntTest {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final String API = "/person";

    @Container
    // TODO: pass docker image name if you need greater version of MySQL
    public static final MySQLContainer CONTAINER = new MySQLContainer<>()
        .withUsername("root")
        .withPassword("root")
        .withDatabaseName("test-containers")
        .withInitScript("database/init.sql");

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry)
    {
        registry.add("spring.datasource.url", CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", CONTAINER::getUsername);
        registry.add("spring.datasource.password", CONTAINER::getPassword);
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PersonRepository personRepository;


    @Test
    @Transactional
    public void storePersonTest() throws Exception
    {
        int personSizeBeforeStoring = personRepository.findAll().size();
        Person person = createPerson();

        MvcResult mvcResult = mockMvc.perform(
            post(API)
                .content(OBJECT_MAPPER.writeValueAsBytes(person))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        ).andReturn();

        assertThat(mvcResult.getResponse()).isNotNull();
        assertThat(mvcResult.getResponse().getContentAsString()).isNotNull();
        assertThat(mvcResult.getResponse().getContentAsString()).isNotEmpty();

        Person storedPerson = OBJECT_MAPPER.readValue(mvcResult.getResponse().getContentAsString(), Person.class);
        assertThat(storedPerson.getId()).isNotNull();

        Optional<Person> personFromDbOptional = personRepository.findById(storedPerson.getId());
        assertThat(personFromDbOptional.isPresent()).isTrue();

        Person personFromDb = personFromDbOptional.get();
        assertThat(personFromDb.getId()).isEqualTo(storedPerson.getId());
        assertThat(personFromDb.getFirstName()).isEqualTo(storedPerson.getFirstName());
        assertThat(personFromDb.getLastName()).isEqualTo(storedPerson.getLastName());
        assertThat(personFromDb.getIdentifier()).isEqualTo(storedPerson.getIdentifier());

        int personSizeAfterStoring = personRepository.findAll().size();
        assertThat(personSizeAfterStoring).isGreaterThan(personSizeBeforeStoring);
    }

    /**
     * Creating person object for testing
     *
     * @return Person Object with properties
     */
    private Person createPerson()
    {
        int prefix = ThreadLocalRandom.current().nextInt();

        Person person = new Person();
        person.setIdentifier("Person-" + prefix);
        person.setFirstName("Person");
        person.setLastName("Person");
        person.setIsActive(Boolean.TRUE);
        person.setCreatedAt(new Date());

        return person;
    }
}
