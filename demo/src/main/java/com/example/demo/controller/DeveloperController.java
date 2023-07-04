package com.example.demo.controller;

import com.example.demo.models.Developer;
import com.example.demo.service.DeveloperService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class DeveloperController {

    private final DeveloperService developerService;

    @GetMapping
    public List<Developer> getAllUsers() {
        return developerService.getAllUsers();
    }

    @GetMapping("/{id}")
    public Developer getUserById(@PathVariable long id) {
        return developerService.getUserById(id);
    }

    @PostMapping
    public Developer createUser(@RequestBody Developer developer) {
        return developerService.createUser(developer);
    }

    @PutMapping("/{id}")
    public Developer updateUser(@PathVariable long id, @RequestBody Developer developer) {
        return developerService.updateUser(id, developer);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable long id) {
        developerService.deleteUser(id);
    }

}
