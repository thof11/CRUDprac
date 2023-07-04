package com.example.demo.controller;

import com.example.demo.models.Developer;
import com.example.demo.repo.DeveloperRepo;
import com.example.demo.service.DeveloperService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class DeveloperControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DeveloperRepo developerRepo;

    @Autowired
    private DeveloperService developerService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        developerRepo.deleteAll();
    }

    @Test
    public void testCreateUser() throws Exception {
        Developer developer = new Developer();
        developer.setFirstName("John");
        developer.setLastName("Doe");
        developer.setAge(30);

        // invoke create user endpoint
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(developer));

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("John"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(30))
                .andExpect(MockMvcResultMatchers.jsonPath("$.occupation").value("Engineer"))
                .andReturn();

        //Validate the response and check if the user was created
        Developer createdDeveloper = developerService.getUserById(1L);
        assertThat(createdDeveloper).isNotNull();
        assertThat(createdDeveloper.getFirstName()).isEqualTo("John");
        assertThat(createdDeveloper.getLastName()).isEqualTo("Doe");
        assertThat(createdDeveloper.getAge()).isEqualTo(30);
    }

    @Test
    public void testUpdateUser() throws Exception {
        // GIVEN
        Developer developerToUpdate = developerService.getUserById(1L);
        developerToUpdate.setAge(35);

        //invoke the updateUser endpoint
        // WHEN
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(developerToUpdate));

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("John"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(35))
                .andExpect(MockMvcResultMatchers.jsonPath("$.occupation").value("Senior Engineer"));

        // Validate the database to check if the user was updated
        // THEN
        Developer updatedDeveloper = developerService.getUserById(1L);
        assertThat(updatedDeveloper.getAge()).isEqualTo(35);
    }

    @Test
    public void testDeleteUser() throws Exception {
        // Invoke the deleteUser endpoint
        mockMvc.perform(MockMvcRequestBuilders.delete("/test/users/1"))
                .andExpect(status().isOk());

        // Validate the database to check if the user was deleted
        // You can use userService.getUserById() and assert that it throws NotFoundException
    }
}


