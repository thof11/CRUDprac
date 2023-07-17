package com.example.demo.controller;


import com.example.demo.models.Developer;
import com.example.demo.service.DeveloperService;
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
public class DeveloperControllerTest {


    @Autowired
    private DeveloperService developerService;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    public void setup() {
        // Clean up the database before each test
        developerService.deleteAllUsers();
    }

    private Developer generateRandomDeveloper() {
        Developer user = new Developer();
        user.setFirstName(Faker.instance().name().firstName());
        user.setLastName(Faker.instance().name().lastName());
        user.setAge(Faker.instance().number().randomDigit());
        return user;
    }

    @Test
    public void testCreateUser() {
        // User user = new User();

        Developer user = generateRandomDeveloper();
       /* user.setFirstName("John");
        user.setLastName("Doe");
        user.setAge(30);
        user.setOccupation("Engineer"); */

        // invoke create user endpoint

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Developer> requestEntity = new HttpEntity<>(user, headers);
        ResponseEntity<Developer> responseEntity = restTemplate.exchange(
                "/users",
                HttpMethod.POST,
                requestEntity,
                Developer.class
        );

        //assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        Developer createdUser = responseEntity.getBody();

        // Validate the response and check if the user was created

        assertThat(createdUser).isNotNull();
        assertThat(createdUser.getFirstName()).isEqualTo(user.getFirstName());
        assertThat(createdUser.getLastName()).isEqualTo(user.getLastName());
        assertThat(createdUser.getAge()).isEqualTo(user.getAge());

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



