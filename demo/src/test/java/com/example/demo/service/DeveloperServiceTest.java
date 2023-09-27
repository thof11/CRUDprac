package com.example.demo.service;

import com.example.demo.models.Developer;
import com.example.demo.models.Squad;
import com.example.demo.service.DeveloperService;
import com.example.demo.repo.DeveloperRepo;
import com.example.demo.repo.SquadRepo;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
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
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
// import static org.assertj.core.api.Assertions.assertThat;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.not;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DeveloperServiceTest {


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
    public void testGetAllDevelopers(){

        //GIVEN
        Developer developer1 = generateRandomDeveloper();
        Developer developer2 = generateRandomDeveloper();

        List<Developer> developers= Arrays.asList(
                developer1,developer2
        );

        DeveloperRepo repository = Mockito.mock(DeveloperRepo.class);
        Mockito.when(repository.findAll()).thenReturn(developers);

        DeveloperService service= new DeveloperService(repository);


        //WHEN
        List<Developer> actualDevelopers = service.getAllDevelopers();

        //THEN
        assertEquals(developers, actualDevelopers);

    }

    @Test
    public void testGetDeveloperById() {

        //GIVEN
        Developer developer1 = generateRandomDeveloper();

        DeveloperRepo repository=Mockito.mock(DeveloperRepo.class);
        Mockito.when(repository.findById(developer1.getDeveloperId())).thenReturn(Optional.of(developer1));

        DeveloperService service= new DeveloperService(repository);

        //WHEN
        Developer actualDeveloper = service.getDeveloperById(developer1.getDeveloperId());

        //THEN
        assertEquals(developer1, actualDeveloper);






    }

    @Test
    public void testCreateDeveloper() {

        //GIVEN
        Developer developer1 = generateRandomDeveloper();

        DeveloperRepo repository=Mockito.mock(DeveloperRepo.class);
        DeveloperService service= new DeveloperService(repository);

        Mockito.when(repository.save(developer1)).thenReturn(developer1);
        Mockito.when(repository.findById(developer1.getDeveloperId())).thenReturn(Optional.of(developer1));



        //WHEN
        service.createDeveloper(developer1);
        Developer actualDeveloper = service.getDeveloperById(developer1.getDeveloperId());


        //THEN
        Mockito.verify(repository).save(developer1);
        assertEquals(developer1, actualDeveloper);


    }

    @Test
    public void testUpdateDeveloper() {

        //GIVEN
        Developer developer1 = generateRandomDeveloper();
        developer1.setFirstName("Jack");

        DeveloperRepo repository=Mockito.mock(DeveloperRepo.class);
        DeveloperService service= new DeveloperService(repository);

        Mockito.when(repository.save(developer1)).thenReturn(developer1);
        Mockito.when(repository.findById(developer1.getDeveloperId())).thenReturn(Optional.of(developer1));



        //WHEN
        service.updateDeveloper(developer1.getDeveloperId(), developer1);
        Developer actualDeveloper = service.getDeveloperById(developer1.getDeveloperId());


        //THEN
        Mockito.verify(repository).save(developer1);
        assertEquals(developer1, actualDeveloper);


    }

    @Test
    public void testDeleteDeveloper() {

        //GIVEN
        Developer developer1 = generateRandomDeveloper();
        long developer1Id= developer1.getDeveloperId();



        DeveloperRepo repository=Mockito.mock(DeveloperRepo.class);
        DeveloperService service= new DeveloperService(repository);


        Mockito.when(repository.findById(developer1Id)).thenReturn(Optional.of(developer1));
        Mockito.doNothing().when(repository).deleteById(developer1Id);



        //WHEN
        service.deleteDeveloper(developer1Id);



        //THEN
        Mockito.verify(repository).deleteById(developer1Id);



    }

    @Test
    public void testDeleteAllDevelopers() {

        //GIVEN
        Developer developer1 = generateRandomDeveloper();
        Developer developer2 = generateRandomDeveloper();

        List<Developer> developers= Arrays.asList(
                developer1,developer2
        );



        DeveloperRepo repository=Mockito.mock(DeveloperRepo.class);
        DeveloperService service= new DeveloperService(repository);


        Mockito.when(repository.findAll()).thenReturn(developers);
        //Mockito.doNothing().when(repository).deleteById(developer1Id);




        // WHEN
        service.deleteAllDevelopers();

        // THEN
        Mockito.verify(repository).deleteAll();



    }




}



