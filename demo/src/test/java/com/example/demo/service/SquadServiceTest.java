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
public class SquadServiceTest {

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
    public void testGetAllSquads(){
        //GIVEN
        Squad squad1 = generateRandomSquad();
        Squad squad2 = generateRandomSquad();

        List<Squad> squads= Arrays.asList(
                squad1,squad2
        );

        SquadRepo squadRepository = Mockito.mock(SquadRepo.class);
        DeveloperRepo devRepository=Mockito.mock(DeveloperRepo.class);
        Mockito.when(squadRepository.findAll()).thenReturn(squads);


        DeveloperService devService= new DeveloperService(devRepository);
        SquadService service= new SquadService(squadRepository,devRepository);



        //WHEN
        List<Squad> actualSquads= service.getAllSquads();

        //THEN
        assertEquals(squads, actualSquads);

    }

    @Test
    public void testGetSquadById(){
        //GIVEN
        Squad squad1 = generateRandomSquad();
        long squadId = squad1.getSquadId();



        SquadRepo squadRepository = Mockito.mock(SquadRepo.class);
        DeveloperRepo devRepository=Mockito.mock(DeveloperRepo.class);
        Mockito.when(squadRepository.findById(squadId)).thenReturn(Optional.of(squad1));


        DeveloperService devService= new DeveloperService(devRepository);
        SquadService service= new SquadService(squadRepository,devRepository);



        //WHEN
        Squad actualSquad= service.getSquadById(squadId);

        //THEN
        Mockito.verify(squadRepository).findById(squadId);
        assertEquals(squad1, actualSquad);

    }

    @Test
    public void testCreateSquad(){
        //GIVEN
        Squad squad1 = generateRandomSquad();




        SquadRepo squadRepository = Mockito.mock(SquadRepo.class);
        DeveloperRepo devRepository=Mockito.mock(DeveloperRepo.class);
        Mockito.when(squadRepository.save(squad1)).thenReturn(squad1);


        DeveloperService devService= new DeveloperService(devRepository);
        SquadService service= new SquadService(squadRepository,devRepository);



        //WHEN
        service.createSquad(squad1);

        //THEN
        Mockito.verify(squadRepository).save(squad1);


    }

    @Test
    public void testUpdateSquad(){
        //GIVEN
        Long squadId = 1L;
        Squad squad1 = generateRandomSquad();
        squad1.setSquadId(squadId);




        SquadRepo squadRepository = Mockito.mock(SquadRepo.class);
        DeveloperRepo devRepository=Mockito.mock(DeveloperRepo.class);
        Mockito.when(squadRepository.findById(squadId)).thenReturn(Optional.of(squad1));
        Mockito.when(squadRepository.save(squad1)).thenReturn(squad1);


        DeveloperService devService= new DeveloperService(devRepository);
        SquadService service= new SquadService(squadRepository,devRepository);



        //WHEN
        service.updateSquad(squadId, squad1);

        //THEN
        Mockito.verify(squadRepository).save(squad1);
        Mockito.verify(squadRepository).findById(squadId);


    }

    @Test
    public void testDeleteSquad(){
        //GIVEN

        Squad squad1 = generateRandomSquad();
        Long squadId = squad1.getSquadId();




        SquadRepo squadRepository = Mockito.mock(SquadRepo.class);
        DeveloperRepo devRepository=Mockito.mock(DeveloperRepo.class);
        Mockito.when(squadRepository.findById(squadId)).thenReturn(Optional.of(squad1));
        Mockito.doNothing().when(squadRepository).deleteById((squadId));


        DeveloperService devService= new DeveloperService(devRepository);
        SquadService service= new SquadService(squadRepository,devRepository);



        //WHEN
        service.deleteSquad(squadId);

        //THEN
        Mockito.verify(squadRepository).findById(squadId);
        Mockito.verify(squadRepository).deleteById(squadId);


    }

    @Test
    public void testDeleteAllSquads(){
        //GIVEN

        Squad squad1 = generateRandomSquad();
        Squad squad2 = generateRandomSquad();

        List<Squad> squads = Arrays.asList( squad1, squad2);




        SquadRepo squadRepository = Mockito.mock(SquadRepo.class);
        DeveloperRepo devRepository=Mockito.mock(DeveloperRepo.class);
        Mockito.when(squadRepository.findAll()).thenReturn((squads));



        DeveloperService devService= new DeveloperService(devRepository);
        SquadService service= new SquadService(squadRepository,devRepository);



        //WHEN
        service.deleteAllSquads();

        //THEN
        Mockito.verify(squadRepository).deleteAll();


    }

    @Test
    public void testAddDeveloperToSquad(){
        //GIVEN

        Squad squad1 = generateRandomSquad();
        Developer developer1= generateRandomDeveloper();

        Long squadId= squad1.getSquadId();
        Long developerId= developer1.getDeveloperId();



        SquadRepo squadRepository = Mockito.mock(SquadRepo.class);
        DeveloperRepo devRepository=Mockito.mock(DeveloperRepo.class);
        Mockito.when(squadRepository.findById(squadId)).thenReturn(Optional.of(squad1));
        Mockito.when(devRepository.findById(developerId)).thenReturn(Optional.of(developer1));


        DeveloperService devService= new DeveloperService(devRepository);
        SquadService service= new SquadService(squadRepository,devRepository);



        //WHEN
        service.addDeveloperToSquad(squadId, developerId);

        //THEN
        Mockito.verify(squadRepository).findById(squadId);
        Mockito.verify(devRepository).findById(developerId);
        Mockito.verify(squadRepository).save(squad1);
        assertThat(squad1).isNotNull();
        // discuss assertThat(squad1.getDeveloperId()).isEqualTo(developer1.getDeveloperId());


    }

    @Test
    public void testRemoveDeveloperToSquad(){
        //GIVEN

        Squad squad1 = generateRandomSquad();
        Developer developer1= generateRandomDeveloper();

        Long squadId= squad1.getSquadId();
        Long developerId= developer1.getDeveloperId();

        squad1.addDeveloper(developer1);

        SquadRepo squadRepository = Mockito.mock(SquadRepo.class);
        DeveloperRepo devRepository=Mockito.mock(DeveloperRepo.class);
        Mockito.when(squadRepository.findById(squadId)).thenReturn(Optional.of(squad1));
        Mockito.when(devRepository.findById(developerId)).thenReturn(Optional.of(developer1));


        DeveloperService devService= new DeveloperService(devRepository);
        SquadService service= new SquadService(squadRepository,devRepository);



        //WHEN
        service.removeDeveloperFromSquad(squadId, developerId);

        //THEN
        Mockito.verify(squadRepository).findById(squadId);
        Mockito.verify(devRepository).findById(developerId);
        Mockito.verify(squadRepository).save(squad1);



    }










}
