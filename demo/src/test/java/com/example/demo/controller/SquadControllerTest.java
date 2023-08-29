package com.example.demo.controller;



import com.example.demo.models.Developer;
import com.example.demo.models.Squad;
import com.example.demo.service.DeveloperService;
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
import org.junit.jupiter.api.Assertions;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
// import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import java.util.logging.Logger;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.not;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SquadControllerTest {


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

    private Squad generateRandomSquad() {
        Squad squad = new Squad();
        squad.setName(Faker.instance().name().firstName());
        squad.setDescription(Faker.instance().name().lastName());

        int minId = 1000;
        int maxId = 9999;

        long randomId = Faker.instance().number().numberBetween(minId, maxId);
        squad.setSquadId(randomId);

        return squad;

    }

        @Test
        public void testCreateSquad() {
            //create squad
            Squad squad = squadRepo.save(generateRandomSquad());
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Squad> createSquadRequest = new HttpEntity<>(squad, headers);
            ResponseEntity<Squad> createSquadResponse = restTemplate.exchange(
                    "/squad",
                    HttpMethod.POST,
                    createSquadRequest,
                    Squad.class
            );

            Squad createdSquad= createSquadResponse.getBody();
            assertThat(createdSquad).isNotNull();
            assertThat(createdSquad.getSquadId()).isNotNull();
            assertThat(createdSquad.getName()).isEqualTo(squad.getName());
            assertThat(createdSquad.getDescription()).isEqualTo(squad.getDescription());



        }

        @Test
        public void testDeveloperAndSquadInteraction() {
            //Create Developer
            Developer developer = developerRepo.save(generateRandomDeveloper());
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Developer> createDeveloperRequest = new HttpEntity<>(developer, headers);
            ResponseEntity<Developer> createDeveloperResponse = restTemplate.exchange(
                    "/developers",
                    HttpMethod.POST,
                    createDeveloperRequest,
                    Developer.class
            );

            Developer createdDeveloper = createDeveloperResponse.getBody();
            assertThat(createdDeveloper).isNotNull();
            assertThat(createdDeveloper.getDeveloperId()).isNotNull();
            assertThat(createdDeveloper.getFirstName()).isEqualTo(developer.getFirstName());
            assertThat(createdDeveloper.getLastName()).isEqualTo(developer.getLastName());
            assertThat(createdDeveloper.getAge()).isEqualTo(developer.getAge());

            //create squad
            Squad squad = squadRepo.save(generateRandomSquad());
            HttpEntity<Squad> createSquadRequest = new HttpEntity<>(squad, headers);
            ResponseEntity<Squad> createSquadResponse = restTemplate.exchange(
                    "/squad",
                    HttpMethod.POST,
                    createSquadRequest,
                    Squad.class
            );

            Squad createdSquad = createSquadResponse.getBody();
            assertThat(createdSquad).isNotNull();
            assertThat(createdSquad.getSquadId()).isNotNull();


            //add developer to the squad
            createdSquad.addDeveloper(createdDeveloper);
            HttpEntity<Void> addDeveloperToSquadRequest = new HttpEntity<>(headers);
            ResponseEntity<Squad> addDeveloperToSquadResponse = restTemplate.exchange(
                    "/squad/" + createdSquad.getSquadId() + "/developers/" + createdDeveloper.getDeveloperId(),
                    HttpMethod.POST,
                    addDeveloperToSquadRequest,
                    Squad.class
            );

            Squad squadWithDeveloper = addDeveloperToSquadResponse.getBody();
            Developer squadDeveloper = squadWithDeveloper.getDevelopers().get(0);
            assertThat(squadWithDeveloper).isNotNull();

            assertThat(squadDeveloper.getDeveloperId()).isEqualTo(createdDeveloper.getDeveloperId());
            assertThat(squadDeveloper.getFirstName()).isEqualTo(createdDeveloper.getFirstName());
            assertThat(squadDeveloper.getLastName()).isEqualTo(createdDeveloper.getLastName());
            assertThat(squadDeveloper.getAge()).isEqualTo(createdDeveloper.getAge());



        }




    }



