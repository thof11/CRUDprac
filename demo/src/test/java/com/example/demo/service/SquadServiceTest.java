package com.example.demo.service;


import com.example.demo.models.Developer;
import com.example.demo.models.Squad;
import com.example.demo.repo.DeveloperRepo;
import com.example.demo.repo.SquadRepo;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SquadServiceTest {

    @MockBean
    private DeveloperRepo devRepository;

    @MockBean
    private SquadRepo squadRepository;

    @Autowired
    private SquadService squadService;

    @BeforeEach
    public void setup() {
        // Clean up the mocks before each test
        Mockito.clearAllCaches();
    }

    @Test
    public void testGetAllSquads() {
        //GIVEN
        Squad squad1 = generateRandomSquad();
        Squad squad2 = generateRandomSquad();

        List<Squad> squads = Arrays.asList(squad1, squad2);
        when(squadRepository.findAll()).thenReturn(squads);

        //WHEN
        List<Squad> actualSquads = squadService.getAllSquads();

        //THEN
        assertEquals(squads, actualSquads);
    }

    @Test
    public void testGetSquadById() {
        //GIVEN
        Squad squad = generateRandomSquad();
        long squadId = squad.getSquadId();

        when(squadRepository.findById(squadId)).thenReturn(Optional.of(squad));

        //WHEN
        Squad actualSquad = squadService.getSquadById(squadId);

        //THEN
        verify(squadRepository).findById(squadId);
        assertEquals(squad, actualSquad);
    }

    @Test
    public void testCreateSquad() {
        //GIVEN
        Squad squad = generateRandomSquad();
        when(squadRepository.save(squad)).thenReturn(squad);

        //WHEN
        squadService.createSquad(squad);

        //THEN
        verify(squadRepository).save(squad);
    }

    @Test
    public void testUpdateSquad() {
        //GIVEN
        Long squadId = 1L;
        Squad squad = generateRandomSquad();
        squad.setSquadId(squadId);

        when(squadRepository.findById(squadId)).thenReturn(Optional.of(squad));
        when(squadRepository.save(squad)).thenReturn(squad);

        //WHEN
        squadService.updateSquad(squadId, squad);

        //THEN
        verify(squadRepository).save(squad);
        verify(squadRepository).findById(squadId);
    }

    @Test
    public void testDeleteSquad() {
        //GIVEN
        Squad squad = generateRandomSquad();
        Long squadId = squad.getSquadId();

        doNothing().when(squadRepository).deleteById((squadId));

        //WHEN
        squadService.deleteSquad(squadId);

        //THEN
        verify(squadRepository).deleteById(squadId);
    }

    @Test
    public void testDeleteAllSquads() {
        //WHEN
        squadService.deleteAllSquads();

        //THEN
        verify(squadRepository).deleteAll();
    }

    @Test
    public void testAddDeveloperToSquad() {
        //GIVEN
        Squad squad = generateRandomSquad();
        Developer developer = generateRandomDeveloper();

        Long squadId = squad.getSquadId();
        Long developerId = developer.getDeveloperId();

        when(squadRepository.save(squad)).thenReturn(squad);
        when(squadRepository.findById(squadId)).thenReturn(Optional.of(squad));
        when(devRepository.findById(developerId)).thenReturn(Optional.of(developer));

        //WHEN
        Squad squadWithDeveloper = squadService.addDeveloperToSquad(squadId, developerId);

        //THEN
        assertThat(squadWithDeveloper).isNotNull();
        assertThat(squadWithDeveloper.getDevelopers())
                .isNotNull()
                .asList()
                .hasSize(1)
                .contains(developer);
        assertThat(developer).isNotNull();
        assertThat(developer.getSquad()).isNotNull();

        verify(squadRepository).findById(squadId);
        verify(devRepository).findById(developerId);
        verify(squadRepository).save(squad);
    }

    @Test
    public void testRemoveDeveloperToSquad() {
        //GIVEN
        Squad squad = generateRandomSquad();
        Developer developer = generateRandomDeveloper();

        Long squadId = squad.getSquadId();
        Long developerId = developer.getDeveloperId();
        squad.addDeveloper(developer);

        when(squadRepository.save(squad)).thenReturn(squad);
        when(squadRepository.findById(squadId)).thenReturn(Optional.of(squad));
        when(devRepository.findById(developerId)).thenReturn(Optional.of(developer));

        //WHEN
        Squad squadWithoutDeveloper = squadService.removeDeveloperFromSquad(squadId, developerId);

        //THEN
        assertThat(squadWithoutDeveloper).isNotNull();
        assertThat(squadWithoutDeveloper.getDevelopers())
                .asList()
                .hasSize(0);

        verify(squadRepository).findById(squadId);
        verify(devRepository).findById(developerId);
        verify(squadRepository).save(squad);
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

}
