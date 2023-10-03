package com.example.demo.service;

import com.example.demo.models.Developer;
import com.example.demo.repo.DeveloperRepo;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DeveloperServiceTest {

    @MockBean
    private DeveloperRepo developerRepo;

    // SquadRepo is not required here because it is not dependency of DeveloperService
    @Autowired
    private DeveloperService developerService;

    @BeforeEach
    public void setup() {
        // Clean up the mock before each test
        Mockito.reset(developerRepo);
    }

    @Test
    public void testGetAllDevelopers() {
        //GIVEN
        Developer developer1 = generateRandomDeveloper();
        Developer developer2 = generateRandomDeveloper();
        List<Developer> developers = Arrays.asList(developer1, developer2);

        when(developerRepo.findAll()).thenReturn(developers);


        //WHEN
        List<Developer> actualDevelopers = developerService.getAllDevelopers();

        //THEN
        assertEquals(developers, actualDevelopers);

    }

    @Test
    public void testGetDeveloperById() {
        //GIVEN
        Developer developer = generateRandomDeveloper();
        when(developerRepo.findById(developer.getDeveloperId()))
                .thenReturn(Optional.of(developer));

        //WHEN
        Developer actualDeveloper = developerService.getDeveloperById(developer.getDeveloperId());

        //THEN
        assertEquals(developer, actualDeveloper);
    }

    @Test
    public void testCreateDeveloper() {
        //GIVEN

        //This test has nothing to do with findById logic, hence you don't need to mock it

        Developer developer = generateRandomDeveloper();

        when(developerRepo.save(developer))
                .thenReturn(developer);

        //WHEN
        //Since this test validates createDeveloper method you need to check return result of this method
        Developer actualDeveloper = developerService.createDeveloper(developer);


        //THEN
        verify(developerRepo).save(developer);
        assertEquals(developer, actualDeveloper);
    }

    @Test
    public void testUpdateDeveloper() {
        //GIVEN
        Developer developer = generateRandomDeveloper();
        developer.setFirstName("Jack");

        when(developerRepo.save(developer)).thenReturn(developer);
        when(developerRepo.findById(developer.getDeveloperId())).thenReturn(Optional.of(developer));


        //WHEN
        //Since this test validates updateDeveloper method you need to check return result of this method
        Developer actualDeveloper = developerService.updateDeveloper(developer.getDeveloperId(), developer);

        //THEN
        verify(developerRepo).save(developer);
        assertEquals(developer, actualDeveloper);
    }

    @Test
    public void testDeleteDeveloper() {
        //GIVEN
        Developer developer = generateRandomDeveloper();
        long developerId = developer.getDeveloperId();

        doNothing()
                .when(developerRepo)
                .deleteById(developerId);

        //WHEN
        developerService.deleteDeveloper(developerId);

        //THEN
        verify(developerRepo).deleteById(developerId);
    }

    @Test
    public void testDeleteAllDevelopers() {
        // You don't need to have GIVEN block here because you verify that all developers are deleted and
        // there is no return value

        // WHEN
        developerService.deleteAllDevelopers();

        // THEN
        verify(developerRepo).deleteAll();
    }

    private Developer generateRandomDeveloper() {
        Developer developer = new Developer();
        developer.setFirstName(Faker.instance().name().firstName());
        developer.setLastName(Faker.instance().name().lastName());
        developer.setAge(Faker.instance().number().randomDigit());
        developer.setDeveloperId(Faker.instance().number().randomDigit());
        return developer;
    }

}



