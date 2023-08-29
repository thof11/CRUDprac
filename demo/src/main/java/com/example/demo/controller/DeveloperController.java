package com.example.demo.controller;

import com.example.demo.models.Developer;
import com.example.demo.service.DeveloperService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/developers")
@AllArgsConstructor
public class DeveloperController {

    private final DeveloperService developerService;

    @GetMapping
    public ResponseEntity<List<Developer>> getAllDevelopers() {
        return new ResponseEntity<>(developerService.getAllDevelopers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Developer> getDeveloperById(@PathVariable("id") long developerId) {
        return new ResponseEntity<>(developerService.getDeveloperById(developerId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Developer> createDeveloper(@RequestBody Developer developer) {
        return new ResponseEntity<>(developerService.createDeveloper(developer), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Developer> updateDeveloper(@PathVariable("id") long developerId, @RequestBody Developer developer) {
        return new ResponseEntity<>(developerService.updateDeveloper(developerId, developer), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDeveloper(@PathVariable("id") long developerId) {
        developerService.deleteDeveloper(developerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllDeveloper() {
        developerService.deleteAllDevelopers();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
