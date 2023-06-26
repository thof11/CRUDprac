package com.example.demo.Controller;

import com.example.demo.utils.JsonUtils;
import com.example.demo.Controller.Models.User;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;




import org.springframework.web.bind.annotation.GetMapping;


@SpringBootTest
@AutoConfigureMockMvc
public class TestController{

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @BeforeEach
    public void setup(){

    }

    @Test
    public void testCreateUser() throws Exception{
        User user=new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setAge(30);
        user.setOccupation("Engineer");

        // invoke create user endpoint

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/test/users")
                .contentType("application/json")
                .content(JsonUtils.toJson(user))
                .andExpect(status()).isOk())
                .andReturn();

        //Validate the response and check if the user was created
        User createdUser= userService.getuserById(1L);
        assertThat(createdUser).isNotNull();
        assertThat(createdUser.getFirstName()).isEqualTo("John");
        assertThat(createdUser.getLastName()).isEqualTo("Doe");
        assertThat(createdUser.getLastName()).isEqualTo("30");
        assertThat(createdUser.getOccupation()).isEqualTo("Engineer");
    }

    @Test
    public void testUpdateUser() throws Exception {
        User userToUpdate = userService.getUserById(1L);
        userToUpdate.setAge(35);
        userToUpdate.setOccupation("Senior Engineer");

        //invoke the updateUser endpoint
        mockMvc.perform(MockMvcRequestBuilders.put("/test/users/1")
                        .contentType("application/json")
                        .content(JsonUtils.toJson(userToUpdate)))
                .andExpect(status().isOk());

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
    }
}


