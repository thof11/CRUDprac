package com.example.demo.service;

import com.example.demo.exception.NotFoundException;
import com.example.demo.models.Developer;
import com.example.demo.models.Squad;
import com.example.demo.repo.DeveloperRepo;
import com.example.demo.repo.SquadRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor

public class SquadService {

    private final SquadRepo squadRepo;
    private final DeveloperRepo developerRepo;

    public List<Squad> getAllSquads() {
        return squadRepo.findAll();
    }

    public Squad getSquadById(long id) {
        return squadRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + id));
    }

    public Squad createSquad(Squad squad) {
        return squadRepo.save(squad);
    }

    public Squad updateSquad(long id, Squad squad) {
        Squad existingSquad = getSquadById(id);
        existingSquad.setName(squad.getName());
        existingSquad.setDescription(squad.getDescription());
        return squadRepo.save(existingSquad);
    }

    public void deleteSquad(long id) {
        squadRepo.deleteById(id);
    }

    public void deleteAllSquads() {
        squadRepo.deleteAll();
    }

    @Transactional
    public Squad addDeveloperToSquad(Long squadId, Long developerId) {
        Squad squad = squadRepo.findById(squadId)
                .orElseThrow(() -> new NotFoundException("Squad not found with id: " + squadId));

        Developer developer = developerRepo.findById(developerId)
                .orElseThrow(() -> new NotFoundException("Developer not found with id" + developerId));

        squad.addDeveloper(developer);
        return squadRepo.save(squad);
    }

    @Transactional
    public Squad removeDeveloperFromSquad(Long squadId, Long developerId) {
        Squad squad = squadRepo.findById(squadId)
                .orElseThrow(() -> new NotFoundException("Squad not found with id:" + squadId));

        Developer developer = developerRepo.findById(developerId)
                .orElseThrow(() -> new NotFoundException("Developer not found with id" + developerId));

        squad.removeDeveloper(developer);
        return squadRepo.save(squad);
    }

}
