package com.example.demo.controller;


import com.example.demo.models.Developer;
import com.example.demo.service.DeveloperService;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.Random;
import java.util.List;

import static com.example.demo.service.DeveloperService.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DeveloperControllerTest {


    @Autowired
    private DeveloperService developerService;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    public void setup() {
        // Clean up the database before each test
        developerService.deleteAllDevelopers();
    }

    private Developer generateRandomDeveloper() {
        Developer developer = new Developer();
        developer.setFirstName(Faker.instance().name().firstName());
        developer.setLastName(Faker.instance().name().lastName());
        developer.setAge(Faker.instance().number().randomDigit());
        return developer;
    }

    @Test
    public void testCreateDeveloper() {
        // User user = new User();

        Developer developer = generateRandomDeveloper();
       /* user.setFirstName("John");
        user.setLastName("Doe");
        user.setAge(30);
        user.setOccupation("Engineer"); */

        // invoke create user endpoint

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Developer> requestEntity = new HttpEntity<>(developer, headers);
        ResponseEntity<Developer> responseEntity = restTemplate.exchange(
                "/users",
                HttpMethod.POST,
                requestEntity,
                Developer.class
        );


        Developer createdDeveloper = responseEntity.getBody();

        // Validate the response and check if the user was created

        assertThat(createdDeveloper).isNotNull();
        assertThat(createdDeveloper.getFirstName()).isEqualTo(createdDeveloper.getFirstName());
        assertThat(createdDeveloper.getLastName()).isEqualTo(createdDeveloper.getLastName());
        assertThat(createdDeveloper.getAge()).isEqualTo(createdDeveloper.getAge());

    }

    @Test
    public void testUpdateDeveloper() {
        // Generate a random developer
        Developer randomDeveloper = generateRandomDeveloper();

        // Create the developer using REST API
        HttpHeaders createHeaders = new HttpHeaders();
        createHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Developer> createRequestEntity = new HttpEntity<>(randomDeveloper, createHeaders);
        ResponseEntity<Developer> createResponseEntity = restTemplate.exchange(
                "/users",
                HttpMethod.POST,
                createRequestEntity,
                Developer.class
        );
        Developer createdDeveloper = createResponseEntity.getBody();

        // Generate updated details for the developer
        Faker faker = new Faker();
        Developer updatedDeveloper = new Developer();
        updatedDeveloper.setId(createdDeveloper.getId());
        updatedDeveloper.setFirstName(faker.name().firstName());
        updatedDeveloper.setLastName(faker.name().lastName());
        updatedDeveloper.setAge(faker.number().randomDigit());

        // Update the developer using REST API
        HttpHeaders updateHeaders = new HttpHeaders();
        updateHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Developer> updateRequestEntity = new HttpEntity<>(updatedDeveloper, updateHeaders);
        ResponseEntity<Developer> updateResponseEntity = restTemplate.exchange(
                "/users/" + createdDeveloper.getId(),
                HttpMethod.PUT,
                updateRequestEntity,
                Developer.class
        );
        Developer updatedDeveloperResponse = updateResponseEntity.getBody();

        // Assert the response
        assertThat(updatedDeveloperResponse).isNotNull();
        assertThat(updatedDeveloperResponse.getId()).isEqualTo(createdDeveloper.getId());
        assertThat(updatedDeveloperResponse.getFirstName()).isEqualTo(updatedDeveloper.getFirstName());
        assertThat(updatedDeveloperResponse.getLastName()).isEqualTo(updatedDeveloper.getLastName());
        assertThat(updatedDeveloperResponse.getAge()).isEqualTo(updatedDeveloper.getAge());
    }


    @Test
    public void testDeleteDeveloper(){
        // Generate a random developer
        Developer randomDeveloper = generateRandomDeveloper();

        // Create the developer using REST API
        HttpHeaders createHeaders = new HttpHeaders();
        createHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Developer> createRequestEntity = new HttpEntity<>(randomDeveloper, createHeaders);
        ResponseEntity<Developer> createResponseEntity = restTemplate.exchange(
                "/users",
                HttpMethod.POST,
                createRequestEntity,
                Developer.class
        );
        Developer createdDeveloper = createResponseEntity.getBody();

        // Delete the developer using REST API
        HttpHeaders deleteHeaders = new HttpHeaders();
        deleteHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Developer> deleteRequestEntity = new HttpEntity<>(deleteHeaders);
        ResponseEntity<Void> deleteResponseEntity = restTemplate.exchange(
                "/users/" + createdDeveloper.getId(),
                HttpMethod.DELETE,
                deleteRequestEntity,
                Void.class
        );

        assertThat(deleteResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        ResponseEntity<Void> getResponseEntity = restTemplate.exchange(
                "/users/"+ createdDeveloper.getId(),
                HttpMethod.GET,
                null,
                Void.class
        );


        assertThat(getResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);







    }


}



        /*
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/test/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(user));

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("John"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(30))
                .andExpect(MockMvcResultMatchers.jsonPath("$.occupation").value("Engineer"))
                .andReturn();

        //Validate the response and check if the user was created
        User createdUser = userService.getUserById(1L);
        assertThat(createdUser).isNotNull();
        assertThat(createdUser.getFirstName()).isEqualTo("John");
        assertThat(createdUser.getLastName()).isEqualTo("Doe");
        assertThat(createdUser.getAge()).isEqualTo(30);
        assertThat(createdUser.getOccupation()).isEqualTo("Engineer");
    }



   @Test
    public void testUpdateUser() throws Exception {
        User userToUpdate = userService.getUserById(1L);
        userToUpdate.setAge(35);
        userToUpdate.setOccupation("Senior Engineer");

        //invoke the updateUser endpoint
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/test/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(userToUpdate));

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("John"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(35))
                .andExpect(MockMvcResultMatchers.jsonPath("$.occupation").value("Senior Engineer"));

        // Validate the database to check if the user was updated
        User updatedUser = userService.getUserById(1L);
        assertThat(updatedUser.getAge()).isEqualTo(35);
        assertThat(updatedUser.getOccupation()).isEqualTo("Senior Engineer");
    }

    @Test
    public void testDeleteUser() throws Exception {
        // Invoke the deleteUser endpoint
        mockMvc.perform(MockMvcRequestBuilders.delete("/test/users/1"))
                .andExpect(status().isOk());

        // Validate the database to check if the user was deleted
        // You can use userService.getUserById() and assert that it throws NotFoundException
    }*/



