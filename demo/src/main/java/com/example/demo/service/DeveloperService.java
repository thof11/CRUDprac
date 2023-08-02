package com.example.demo.service;

import com.example.demo.exception.NotFoundException;
import com.example.demo.models.Developer;
import com.example.demo.repo.DeveloperRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Service

@AllArgsConstructor
public class DeveloperService {

    private final DeveloperRepo developerRepo;

    public List<Developer> getAllDevelopers() {
        return developerRepo.findAll();
    }

    public Developer getDeveloperById(long developerId) {
        return developerRepo.findById(developerId)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + developerId));
    }

    public Developer createDeveloper(Developer developer) {
        return developerRepo.save(developer);
    }

    public Developer updateDeveloper(long developerId, Developer developer) {
        Developer existingDeveloper = getDeveloperById(developerId);
        existingDeveloper.setFirstName(developer.getFirstName());
        existingDeveloper.setLastName(developer.getLastName());
        existingDeveloper.setAge(developer.getAge());
        return developerRepo.save(existingDeveloper);
    }

    public void deleteDeveloper(long developerId) {
        Developer developer = getDeveloperById(developerId);
        developerRepo.delete(developer);
    }

    public void deleteAllDevelopers() {
        developerRepo.deleteAll();
    }
}
