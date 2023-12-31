package com.example.demo.controller;

import com.example.demo.models.Developer;
import com.example.demo.service.DeveloperService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<Developer>> getAllDevelopers() {
        return new ResponseEntity<>(developerService.getAllDevelopers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Developer> getDeveloperById(@PathVariable long id) {
        return new ResponseEntity<>(developerService.getDeveloperById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Developer> createDeveloper(@PathVariable Developer developer) {
        return new ResponseEntity<>(developerService.createDeveloper(developer), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Developer> updateDeveloper(@PathVariable long id, @RequestBody Developer developer) {
        return new ResponseEntity<>(developerService.updateDeveloper(id, developer), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Developer> deleteDeveloper(@PathVariable long id) {
        developerService.deleteDeveloper(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @DeleteMapping
    public ResponseEntity<Developer> deleteAllDeveloper(@PathVariable long id) {
        developerService.deleteAllDevelopers();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
