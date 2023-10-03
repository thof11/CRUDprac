package com.example.demo.controller;


import com.example.demo.models.Developer;
import com.example.demo.repo.DeveloperRepo;
import com.example.demo.repo.SquadRepo;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DeveloperControllerTest {

    @Autowired
    private DeveloperRepo developerRepo;

    @Autowired
    private SquadRepo squadRepo;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    public void setup() {
        // Clean up the database before each test
        developerRepo.deleteAll();
        squadRepo.deleteAll();
    }

    private Developer generateRandomDeveloper() {
        Developer developer = new Developer();
        developer.setFirstName(Faker.instance().name().firstName());
        developer.setLastName(Faker.instance().name().lastName());
        developer.setAge(Faker.instance().number().randomDigit());
        developer.setDeveloperId(Faker.instance().number().randomDigit());
        return developer;
    }


    @Test
    public void testCreateDeveloper() {
        Developer developer = generateRandomDeveloper();

        // invoke create user endpoint

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Developer> requestEntity = new HttpEntity<>(developer, headers);
        ResponseEntity<Developer> responseEntity = restTemplate.exchange(
                "/developers",
                HttpMethod.POST,
                requestEntity,
                Developer.class
        );


        Developer createdDeveloper = responseEntity.getBody();

        // Validate the response and check if the user was created

        assertThat(createdDeveloper).isNotNull();
        assertThat(createdDeveloper.getFirstName()).isEqualTo(developer.getFirstName());
        assertThat(createdDeveloper.getLastName()).isEqualTo(developer.getLastName());
        assertThat(createdDeveloper.getAge()).isEqualTo(developer.getAge());

    }

    @Test
    public void testUpdateDeveloper() {
        // Generate a random developer
        Developer createdDeveloper = developerRepo.save(generateRandomDeveloper());


        // Generate updated details for the developer
        Developer updatedDeveloper = generateRandomDeveloper();
        updatedDeveloper.setDeveloperId(createdDeveloper.getDeveloperId());

        // Update the developer using REST API
        HttpHeaders updateHeaders = new HttpHeaders();
        updateHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Developer> updateRequestEntity = new HttpEntity<>(updatedDeveloper, updateHeaders);
        ResponseEntity<Developer> updateResponseEntity = restTemplate.exchange(
                "/developers/" + createdDeveloper.getDeveloperId(),
                HttpMethod.PUT,
                updateRequestEntity,
                Developer.class
        );
        Developer updatedDeveloperResponse = updateResponseEntity.getBody();

        // Assert the response
        assertThat(updatedDeveloperResponse).isNotNull();
        assertThat(updatedDeveloperResponse.getDeveloperId()).isEqualTo(createdDeveloper.getDeveloperId());
        assertThat(updatedDeveloperResponse.getFirstName()).isEqualTo(updatedDeveloper.getFirstName());
        assertThat(updatedDeveloperResponse.getLastName()).isEqualTo(updatedDeveloper.getLastName());
        assertThat(updatedDeveloperResponse.getAge()).isEqualTo(updatedDeveloper.getAge());
    }


    @Test
    public void testDeleteDeveloper() {
        // Generate a random developer
        Developer createdDeveloper = developerRepo.save(generateRandomDeveloper());

        // Delete the developer using REST API
        HttpHeaders deleteHeaders = new HttpHeaders();
        deleteHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Developer> deleteRequestEntity = new HttpEntity<>(deleteHeaders);
        ResponseEntity<Void> deleteResponseEntity = restTemplate.exchange(
                "/developers/" + createdDeveloper.getDeveloperId(),
                HttpMethod.DELETE,
                deleteRequestEntity,
                Void.class
        );

        assertThat(deleteResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(developerRepo.findById(createdDeveloper.getDeveloperId())).isEmpty();
    }
}








