package com.example.demo.controller;


import com.example.demo.models.Squad;
import com.example.demo.service.SquadService;
import com.example.demo.repo.SquadRepo;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/squad")
@AllArgsConstructor

public class SquadController {
    @Autowired
    private final SquadService squadService;

    @GetMapping
    public ResponseEntity<List<Squad>> getAllSquads(){
        return new ResponseEntity<>(squadService.getAllSquads(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Squad> getSquadById(@PathVariable long squadId){
        return new ResponseEntity<>(squadService.getSquadById(squadId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Squad> createSquad(@RequestBody Squad squad){
        return new ResponseEntity<>(squadService.createSquad(squad), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Squad> updateSquad(@PathVariable long squadId, @RequestBody Squad squad){
        return new ResponseEntity<>(squadService.updateSquad(squadId,squad), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSquad(@PathVariable long squadId){
        squadService.deleteSquad(squadId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllSquads(@PathVariable long squadId){
        squadService.deleteAllSquads();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/developers/{developerId}")
    public ResponseEntity<Squad> createSquad(@PathVariable Long squadId, @PathVariable Long developerId) {
        return new ResponseEntity<>(squadService.addDeveloperToSquad(squadId, developerId), HttpStatus.OK);
    }

    @DeleteMapping("/developers/{developerId}")
    public ResponseEntity<Void> deleteSquad(@PathVariable Long squadId, @PathVariable Long developerId){
        squadService.removeDeveloperFromSquad(squadId,developerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }




}
