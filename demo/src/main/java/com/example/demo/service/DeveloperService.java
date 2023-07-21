package com.example.demo.service;

import com.example.demo.exception.NotFoundException;
import com.example.demo.models.Developer;
import com.example.demo.repo.DeveloperRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DeveloperService {

    private final DeveloperRepo developerRepo;

    public List<Developer> getAllDevelopers() {
        return developerRepo.findAll();
    }

    public Developer getDeveloperById(long id) {
        return developerRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + id));
    }

    public Developer createDeveloper(Developer developer) {
        return developerRepo.save(developer);
    }

    public Developer updateDeveloper(long id, Developer developer) {
        Developer existingDeveloper = getDeveloperById(id);
        existingDeveloper.setFirstName(developer.getFirstName());
        existingDeveloper.setLastName(developer.getLastName());
        existingDeveloper.setAge(developer.getAge());
        return developerRepo.save(existingDeveloper);
    }

    public void deleteDeveloper(long id) {
        Developer developer = getDeveloperById(id);
        developerRepo.delete(developer);
    }

    public List<Developer> deleteAllDevelopers() {
        developerRepo.deleteAll();

        return null;

    }
}
