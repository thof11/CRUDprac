package com.example.demo.controller;


import com.example.demo.models.Developer;
import com.example.demo.models.Squad;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

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

        Squad createdSquad = createSquadResponse.getBody();
        assertThat(createdSquad).isNotNull();
        assertThat(createdSquad.getSquadId()).isNotNull();
        assertThat(createdSquad.getName()).isEqualTo(squad.getName());
        assertThat(createdSquad.getDescription()).isEqualTo(squad.getDescription());
    }

    @Test
    public void testDeveloperAndSquadInteraction() {
        Squad squad = generateRandomSquad();
        Developer developer = generateRandomDeveloper();
        developerRepo.save(developer);
        squadRepo.save(squad);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        //add developer to the squad
        HttpEntity<Void> addDeveloperToSquadRequest = new HttpEntity<>(headers);
        ResponseEntity<Squad> addDeveloperToSquadResponse = restTemplate.exchange(
                "/squad/" + squad.getSquadId() + "/developers/" + developer.getDeveloperId(),
                HttpMethod.POST,
                addDeveloperToSquadRequest,
                Squad.class
        );

        Squad squadWithDeveloper = addDeveloperToSquadResponse.getBody();
        Developer squadDeveloper = squadWithDeveloper.getDevelopers().get(0);
        assertThat(squadWithDeveloper).isNotNull();

        assertThat(squadDeveloper.getDeveloperId()).isEqualTo(developer.getDeveloperId());
        assertThat(squadDeveloper.getFirstName()).isEqualTo(developer.getFirstName());
        assertThat(squadDeveloper.getLastName()).isEqualTo(developer.getLastName());
        assertThat(squadDeveloper.getAge()).isEqualTo(developer.getAge());
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

        long randomId = Faker.instance().number().numberBetween(1000, 9999);
        squad.setSquadId(randomId);

        return squad;
    }
}
